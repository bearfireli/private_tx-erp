package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.ProducetionStatisticsService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/production")
public class ProductionApi {

    private final ProducetionStatisticsService producetionStatisticsService;

    public ProductionApi(ProducetionStatisticsService producetionStatisticsService) {
        this.producetionStatisticsService = producetionStatisticsService;
    }

    /**
     * 生产计划统计
     *
     * @param compid    企业id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param taskStatus 任务状态
     * @return     生产计划统计
     */
    @PostMapping("/getProductionStatistics")
    public ResultVO getProductionStatistics(String compid, Long beginTime, Long endTime,
                                            String taskStatus, String stgId, String eppCode, String builderCode,
                                            String placeStyle,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(producetionStatisticsService.getProductionStatistics(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                taskStatus,stgId, eppCode,builderCode,placeStyle,page, pageSize));
    }

    @PostMapping("/getProductionStatisticstotalPreNum")
    public ResultVO getProductionStatisticstotalPreNum(String compid, Long beginTime, Long endTime,
                                            String taskStatus,String stgId,String eppCode,String builderCode,
                                                       String placeStyle,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(producetionStatisticsService.getProductionStatisticstotalPreNum(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                taskStatus, stgId, eppCode,builderCode,placeStyle,page, pageSize));
    }

}
