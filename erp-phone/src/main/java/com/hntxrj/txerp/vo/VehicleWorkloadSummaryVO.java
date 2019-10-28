package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * 搅拌车砼运输汇总
 */
@Data
public class VehicleWorkloadSummaryVO {

    /**
     *车数
     */
    private Integer carNum;
    /**
     * 砼产量
     */
    private  double num;
    /**
     * 运距
     */
    private  double distance;
    /**
     * 车号
     */
    private  Integer vehicleId;

    /**
     * 砼产量汇总
     */
    private double numList;

    /**
     * 车数汇总
     */
    private Integer carNumList;
    private String personalName;

}
