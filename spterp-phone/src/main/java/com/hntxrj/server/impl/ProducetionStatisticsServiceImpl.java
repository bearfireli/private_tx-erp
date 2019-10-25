package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.mapper.ProductionPlanMapper;
import com.hntxrj.server.ProducetionStatisticsService;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.ProductionPlanStaticesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class ProducetionStatisticsServiceImpl implements ProducetionStatisticsService {

    private final ProductionPlanMapper productionPlanMapper;


    @Autowired
    public ProducetionStatisticsServiceImpl(ProductionPlanMapper productionPlanMapper) {
        this.productionPlanMapper = productionPlanMapper;
    }


    /**
     * 生产计划 统计信息
     *
     * @param compid     企业id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param taskStatus 状态
     * @return ProductionPlanStaticesVO
     */
    @Override
    public PageVO<ProductionPlanStaticesVO> getProductionStatistics(String compid, String beginTime, String endTime,
                                                                    String taskStatus,String stgId,
                                                                    String eppCode,String builderCode,
                                                                    String placeStyle, Integer page, Integer pageSize) {
        PageVO<ProductionPlanStaticesVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<ProductionPlanStaticesVO> productionPlanStaticesVOS =
                productionPlanMapper.getProductionStatistics(compid, beginTime, endTime, taskStatus,
                        stgId, eppCode,builderCode,placeStyle);
        PageInfo<ProductionPlanStaticesVO> pageInfo = new PageInfo<>(productionPlanStaticesVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<ProductionPlanStaticesVO> getProductionStatisticstotalPreNum(String compid, String beginTime,
                                                                               String endTime, String taskStatus,
                                                                               String stgId,String eppCode,
                                                                               String builderCode,
                                                                               String placeStyle,
                                                                               Integer page, Integer pageSize) {
        PageVO<ProductionPlanStaticesVO> pageVO = new PageVO<>();
        List<ProductionPlanStaticesVO> productionPlanStaticesVOS =
                productionPlanMapper.getProductionStatisticstotalPreNum(compid, beginTime, endTime, taskStatus,
                        stgId, eppCode,builderCode,placeStyle);
        PageInfo<ProductionPlanStaticesVO> pageInfo = new PageInfo<>(productionPlanStaticesVOS);
        pageVO.format(pageInfo);
        return pageVO;
}}
