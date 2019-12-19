package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.EppDao;
import com.hntxrj.txerp.entity.EppInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.mapper.ConstructionMapper;
import com.hntxrj.txerp.mapper.EppMapper;
import com.hntxrj.txerp.server.EppService;
import com.hntxrj.txerp.vo.EppDropDownVO;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public EppServiceImpl(EppDao eppDao, EppMapper eppMapper, ConstructionMapper constructionMapper) {
        this.eppDao = eppDao;
        this.eppMapper = eppMapper;
        this.constructionMapper = constructionMapper;
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
    public EppInfo getEppInfo(String eppCode, String compid) {
        return eppMapper.getEppInfo(eppCode, compid);
    }

    @Override
    public PageVO<EppDropDownVO> getBuildDropDown(String eppName, Integer buildId, Integer page, Integer pageSize) {
        //首先根据buildId查询出关联的合同号和子合同号。
        List<String>  ccontractCodes=constructionMapper.getContractodeList(buildId);
        List<String> contractUIDList=constructionMapper.getContractUID(buildId);

        //只能查询出绑定的合同的工程名称

        PageHelper.startPage(page, pageSize);
        List<EppDropDownVO> eppDropDownVOList = eppMapper.getBuildDropDown(ccontractCodes,contractUIDList, eppName);
        PageInfo<EppDropDownVO> eppDropDownVOPageInfo = new PageInfo<>(eppDropDownVOList);
        PageVO<EppDropDownVO> pageVO = new PageVO<>();
        pageVO.format(eppDropDownVOPageInfo);
        return null;
    }
}
