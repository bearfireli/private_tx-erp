package com.hntxrj.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 司机砼运输明细
 * @author qyb
 * @ClassName DriverworkloadStatistics
 * @Description TODO
 * @Date 19-6-3 下午5:28
 * @Version 1.0
 **/
@Data
public class DriverworkloadStatisticsVO {


    /**
     *驾驶员
     */
    private  String personalName;
    /**
     *施工单位
     */
    private String BuilderName;
    /**
     *砼标记
     */
    private String concreteMark;
    /**
     *工程名称
     */
    private String eppName;
    /**
     *销售方量
     */
    private Integer saleNum;
    /**
     *浇筑方式
     */
    private String placeStyleName;
    /**
     *浇筑部位
     */
    private String  placing;
    /**
     *发车时间
     */
    private Timestamp leaveTime;
    /**
     * 回厂时间
     */
    private Timestamp arriveTime;
    /**
     *任务单号
     */
    private String taskId;
    /**
     *车次
     */
    private Integer carNum;
    /**
     *车号
     */
    private String vehicleId;
}
