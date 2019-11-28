package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author qyb
 * @ClassName PumpTruckDetailsVO
 * @Description 泵车明细
 * @Date 19-6-5 上午8:48
 * @Version 1.0
 **/
@Data
public class PumpTruckDetailsVO implements Serializable {
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
    private String taskId;
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
