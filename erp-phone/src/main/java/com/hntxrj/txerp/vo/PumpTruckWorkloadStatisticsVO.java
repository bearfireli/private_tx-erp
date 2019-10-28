package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName PumpTruckWorkloadStatistics
 * @Description 工作方量统计
 * @Date 19-6-5 上午9:54
 * @Version 1.0
 **/
@Data
public class PumpTruckWorkloadStatisticsVO {
    /**
     * 工程名称
     */
    private String eppName;
    /**
     * 泵车
     */
    private String vehicleJump;
    /**
     * 方量
     */
    private double num;
    /**
     * 方量合计
     */
    private  double numList;
}
