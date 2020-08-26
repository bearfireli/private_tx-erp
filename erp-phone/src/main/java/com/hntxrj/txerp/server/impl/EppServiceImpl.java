package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.SyncPlugin;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.dao.EppDao;
import com.hntxrj.txerp.entity.EppInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.mapper.ConstructionMapper;
import com.hntxrj.txerp.mapper.EppMapper;
import com.hntxrj.txerp.server.EppService;
import com.hntxrj.txerp.vo.EppDropDownVO;
import com.hntxrj.txerp.vo.EppInfoVO;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能: 工程服务接口实现层
 *
 * @author 李帅
 * @Data 2017-08-11.上午 11:00
 */
@Service
@Scope("prototype")
public class EppServiceImpl implements EppService {

    private final EppDao eppDao;
    private final EppMapper eppMapper;
    private final ConstructionMapper constructionMapper;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();
    private final SyncPlugin syncPlugin;

    @Autowired
    public EppServiceImpl(EppDao eppDao, EppMapper eppMapper, ConstructionMapper constructionMapper, SyncPlugin syncPlugin) {
        this.eppDao = eppDao;
        this.eppMapper = eppMapper;
        this.constructionMapper = constructionMapper;
        this.syncPlugin = syncPlugin;
    }


    /**
     * 加载详情列表
     *
     * @param eppName  工程名
     * @param pageBean 页码相关实体
     * @return
     */
    @Override
    public JSONArray getEppList(String eppName, PageBean pageBean, String compid) {
        return eppDao.getEppList(eppName, pageBean, compid);
    }

    /**
     * 加载浇筑部位
     *
     * @param eppCode  工程代号
     * @param pageBean 分页
     * @return
     */
    @Override
    public JSONArray getEppPlacing(String eppCode, String placing, PageBean pageBean, String compid) {
        return eppDao.getEppPlacing(eppCode, placing, pageBean, compid);
    }

    @Override
    public PageVO<EppDropDownVO> getDropDown(String eppName, String compid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "CreateTime desc");
        List<EppDropDownVO> eppDropDownVOList = eppMapper.getDropDown(compid, eppName);
        PageInfo<EppDropDownVO> eppDropDownVOPageInfo = new PageInfo<>(eppDropDownVOList);
        PageVO<EppDropDownVO> pageVO = new PageVO<>();
        pageVO.format(eppDropDownVOPageInfo);
        return pageVO;
    }

    @Override
    public PageVO<EppDropDownVO> getBuildDropDown(String eppName, Integer buildId, Integer page, Integer pageSize) {
        //首先根据buildId查询出关联的合同号和子合同号。
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);

        //只能查询出绑定的合同的工程名称

        PageHelper.startPage(page, pageSize);
        List<EppDropDownVO> eppDropDownVOList = eppMapper.getBuildDropDown(contractDetailCodes, contractUIDList, eppName);
        PageInfo<EppDropDownVO> eppDropDownVOPageInfo = new PageInfo<>(eppDropDownVOList);
        PageVO<EppDropDownVO> pageVO = new PageVO<>();
        pageVO.format(eppDropDownVOPageInfo);
        return pageVO;
    }

    @Override
    public void addEppInfo(String compid, String eppName, String shortName, String address, String linkMan,
                           String phone, String remarks) {
        //获取当前日期
        String nowDate = sdf.format(new Date());

        //获取生成的工程名称代号
        String eppCode = getEppCode(compid);
        eppMapper.addEppInfo(compid, eppName, eppCode, shortName, address, linkMan, phone, remarks, nowDate);
        EppInfo eppInfo = eppMapper.getEppInfo(eppCode, compid);
        try {
            syncPlugin.save(eppInfo, "SM_EPPInfo", "INS", compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageVO<EppInfoVO> getEppPageVO(String eppCode, String eppName, Integer recStatus, String compid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<EppInfoVO> eppList = eppMapper.getEppList(eppCode, eppName, recStatus, compid);
        PageInfo<EppInfoVO> pageInfo = new PageInfo<>(eppList);
        PageVO<EppInfoVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public EppInfoVO getEppInfoVO(String eppCode, String compid) {
        return eppMapper.getEppInfoVO(eppCode, compid);
    }

    @Override
    public String saveOrUpdateEppInfo(EppInfoVO eppInfoVO) {
        //获取生成的工程名称代号
        String compid = eppInfoVO.getCompid();
        String eppCode = this.getEppCode(compid);
        // 同步标识
        String syncOption;

        if (StringUtils.isEmpty(eppInfoVO.getEppCode())) {
            // 插入操作
            eppInfoVO.setEppCode(eppCode);
            eppInfoVO.setCreateTime(new Date());
            eppMapper.insertEppInfo(eppInfoVO);

            // 同步 插入操作
            syncOption = "INS";
        }else {
            // 更新操作
            eppMapper.updateEppInfo(eppInfoVO);
            // 同步 插入操作
            syncOption = "UP";
            // 查询使用
            eppCode = eppInfoVO.getEppCode();
        }

        // 同步数据
        EppInfo eppInfo = eppMapper.getEppInfo(eppCode, compid);
        try {
            syncPlugin.save(eppInfo, "SM_EPPInfo", syncOption, compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eppCode;
    }

    @Override
    public void changeEppRecStatus(String eppCode, String compid, Integer recStatus) {
        eppMapper.changeEppRecStatus(eppCode, compid, recStatus);
    }

    private String getEppCode(String compid) {
        String eppCode = eppMapper.getMaxEppCode(compid);

        //正则匹配找出eppCode中最大的数值
        Pattern compile = Pattern.compile("[0-9]\\d*$");
        Matcher matcher = compile.matcher(eppCode);
        String preMaxCode = "";
        while (matcher.find()) {
            preMaxCode = matcher.group(0);
        }

        //获取最大数值所在的索引，将之前的字符串分割，之后的数值加1
        int startIndex = eppCode.indexOf(preMaxCode);
        String preEppCode = eppCode.substring(0, startIndex);

        int length = preMaxCode.length();
        int maxId = Integer.parseInt(preMaxCode);

        StringBuilder maxCode = new StringBuilder(String.valueOf(maxId + 1));

        //将加1之后的数值转换成字符串
        int dValue = length - maxCode.length();
        if (dValue > 0) {
            for (int i = 0; i < dValue; i++) {
                maxCode.insert(0, "0");
            }
        }

        return preEppCode + maxCode;
    }
}
