package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 车辆工作量
 */
@Data
public class VehicleWorkloadDetailVO {

    private String taskId;
    private String vehicleId;
    private String builderCode;
    private String builderName;
    private String eppCode;
    private String eppName;
    /**
     * 累计方量
     */
    private Double num;
    /**
     * 车数
     */
    private Integer carNum;
    /**
     * 浇筑部位
     */
    private String placing;
    /**
     * 浇筑方式
     */
    private String placeStyleName;
    /**
     * 出厂时间
     */
    private Timestamp leaveTime;
    /**
     * 回厂时间
     */
    private Timestamp arriveTime;
    /**
     * 砼标记
     */
    private String concreteMark;
    /**
     * 销售方量
     */
    private BigDecimal saleNum;
    private String personalName;

    private String stgId;

    /**
     * 生产线号
     */
    private Integer stirId;

    /**
     * 线号名称
     */
    private String stirName;

}
