package com.hntxrj.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName PumpTruckDetailsVO
 * @Description 泵车明细
 * @Date 19-6-5 上午8:48
 * @Version 1.0
 **/
@Data
public class PumpTruckDetailsVO {
    /**
     *施工单位
     */
    private String builderShortName;
    /**
     *工程名称
     */
    private String eppName;
    /**
     *浇筑部位
     */
    private String placing;
    /**
     *方量
     */
    private double saleNum;
    /**
     *发车时间
     */
    private String sendTime;
    /**
     *任务单号
     */
    private String taskid;
    /**
     *单价
     */
    private double unitPrice;
    /**
     * 金额
     */
    private double totalMoney;
    /**
     *车号
     */
    private String vehicleId;

}
