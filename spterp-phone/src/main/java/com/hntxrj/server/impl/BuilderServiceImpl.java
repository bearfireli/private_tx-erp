package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.dao.BuilderDao;
import com.hntxrj.entity.BuilderInfo;
import com.hntxrj.entity.PageBean;
import com.hntxrj.mapper.BuilderMapper;
import com.hntxrj.server.BuilderService;
import com.hntxrj.vo.BuilderDropDownVO;
import com.hntxrj.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 功能:   施工单位服务接口实现层
 * <p>
 * 李帅
 * 2017-08-11.上午 11:13
 */
@Service
@Scope("prototype")

public class BuilderServiceImpl implements BuilderService {

    private final BuilderDao builderDao;
    private final BuilderMapper builderMapper;

    @Autowired
    public BuilderServiceImpl(BuilderDao builderDao, BuilderMapper builderMapper) {
        this.builderDao = builderDao;
        this.builderMapper = builderMapper;
    }


    /**
     * 加载详情列表
     *
     * @param builderName 工程名
     * @param pageBean    页码相关实体
     */
    @Override
    public JSONArray getEppList(String builderName, PageBean pageBean, String compid) {
        return builderDao.getBuilderList(builderName, pageBean, compid);
    }


    /**
     * 添加修改删除  施工单位
     *
     * @param Mark               操作标识 0 插入 1 更新 2 删除
     * @param compid             企业ID
     * @param OpId               操作员ID
     * @param BuilderCode        施工单位代号
     * @param BuilderName_1      施工单位名
     * @param BuilderShortName_2 施工单位名简称
     * @param Address_3          地址
     * @param CreateTime_4       创建时间
     * @param Corporation_5      法人
     * @param Fax_6              传真
     * @param LinkTel_7          联系电话
     * @param RecStatus_8        记录状态(有效)   0未启用 1启用(0无效1有效)
     */
    @Override
    public JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String OpId, String BuilderCode,
                                                String BuilderName_1, String BuilderShortName_2, String Address_3,
                                                Timestamp CreateTime_4, String Corporation_5, String Fax_6,
                                                String LinkTel_7, byte RecStatus_8) {
        return builderDao.insertUpDel_SM_BUILDERINFO(Mark, compid, OpId, BuilderCode, BuilderName_1, BuilderShortName_2,
                Address_3, CreateTime_4, Corporation_5, Fax_6, LinkTel_7, RecStatus_8);
    }

    @Override
    public PageVO<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "createTime desc");
        List<BuilderDropDownVO> builderDropDownVOS = builderMapper.getBuilderDropDown(compid, builderName);
        PageInfo<BuilderDropDownVO> pageInfo = new PageInfo<>(builderDropDownVOS);
        PageVO<BuilderDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public BuilderInfo getBuilderInfo(String builderCode, String compid) {
        return builderMapper.getBuilderInfo(builderCode, compid);
    }
}
