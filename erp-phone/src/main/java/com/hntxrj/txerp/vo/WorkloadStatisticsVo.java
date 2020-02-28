package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 搅拌车过磅查询
 * @author qyb
 * @ClassName VMVehicleWeightVo
 * @Date 19-6-2 下午5:05
 * @Version 1.0
 **/
@Data
public class WorkloadStatisticsVo {

    /**
     * 工程名称
     */
    private String eppName;
    /*
    * 施工单位
    * */
    private String builderName;
    /**
     * 初次过磅时间
     */
    private Timestamp firstTime;
    /**
     * 车辆毛重
     */
    private double grWeight;
    /**
     * 车辆净重
     */
    private double netWeight;
    /**
     *二次过磅时间
     */
    private Timestamp secondTime;
    /**
     * 标号
     */
    private  String stgId;
    /**
     * 车辆皮重
     */
    private  double taWeight;
    /**
     * 任务单号
     */
    private String taskId;
    /**
     * 车号
     */
    private  String vehicleId;
    /**
     *方量
     */
    private  double weightFang;
    /**
     *过磅类型
     */
    private  String weightTypeName;
    /**
     * 过磅员
     */
    private  String empName;

    /**
     * 进场时间
     * */

}
