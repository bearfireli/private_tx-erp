package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 调度派车中每辆车的调度
 */
@Data
public class SendCarDetailVO implements Serializable {

    private String taskId;
    private String stgId;
    private String eppName;
    private String placing;
    //
    private String stirId;
    //销量
    private Double saleNumTotal;

    //产量
    private Double totalProduceNum;
    //派车
    private String sendTime;
    //回厂
    private String arriveTime;

    private String vehicleID;

    private String stirName;
    /*生产方量*/
    private  String threeProduceNum;
}
