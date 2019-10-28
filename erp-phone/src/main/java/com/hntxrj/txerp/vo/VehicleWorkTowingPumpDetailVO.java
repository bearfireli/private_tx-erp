package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName VehicleWorkTowingPumpDetailVO
 * @Description  搅拌车拖水拖泵明细
 * @Date 19-6-3 上午11:25
 * @Version 1.0
 **/
@Data
public class VehicleWorkTowingPumpDetailVO {

    /**
     * 回厂时间
     */
    private Timestamp arriveSTTime;
    /**
     * 施工单位
     */
    private String builderShortName;
    /**
     *
     */
    private double distance;
    /**
     * 工程名称
     */
    private String  eppName;
    /**
     * 浇筑部位
     */
    private String placing;
    /**
     * 发车时间
     */
    private Timestamp sendTime;
    /**
     * 任务单号
     */
    private String taskId;
    /**
     *车次
     */
    private String vehiceNumber;
    /**
     * 车号
     */
    private String vehicleId;
    /**
     *
     */
    private String vehicleIdJump;
    /**
     *砼标记
     */
    private String concreteMark;
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String invoiceType;
    /**
     *小票类型
     */
    private String invoiceTypeName;
    /**
     *
     */
    private String placeStyle;
    /**
     *浇筑方式
     */
    private String placeStyleName;
    /**
     * 联系方式
     */
    private String linkTel;
    private  String personalName;
}
