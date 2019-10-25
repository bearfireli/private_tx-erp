package com.hntxrj.mapper;

import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.ProductionPlanStaticesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductionPlanMapper {
    /**
     * 生产计划统计查询
     *
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param taskStatus 任务状态
     * @return 生产计划统计
     */
    List<ProductionPlanStaticesVO> getProductionStatistics(String compid, String beginTime, String endTime,
                                                           String taskStatus,String stgId,
                                                           String eppCode,String builderCode,
                                                           String placeStyle);

    List<ProductionPlanStaticesVO> getProductionStatisticstotalPreNum(String compid, String beginTime,
                                                                      String endTime, String taskStatus
                                                                        ,String stgId,
                                                                      String eppCode,String builderCode,
                                                                      String placeStyle);

}
