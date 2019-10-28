package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProductionPlanStaticesVO;


public interface ProducetionStatisticsService {

    /**
     * @param compid   企业id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param taskStatus 状态
     * @return    生产计划统计
     */
    PageVO<ProductionPlanStaticesVO> getProductionStatistics(String compid, String beginTime,
                                                             String endTime, String taskStatus,
                                                             String stgId,String eppCode,String builderCode,
                                                             String placeStyle,
                                                             Integer page, Integer pageSize);

    PageVO<ProductionPlanStaticesVO> getProductionStatisticstotalPreNum(String compid, String beginTime,
                                                             String endTime, String taskStatus,
                                                                        String stgId,String eppCode,String builderCode,
                                                                        String placeStyle,
                                                             Integer page, Integer pageSize);

}
