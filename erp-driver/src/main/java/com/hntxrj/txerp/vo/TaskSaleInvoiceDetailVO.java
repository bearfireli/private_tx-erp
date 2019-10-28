package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 小票签收需求
 */
@Data
public class TaskSaleInvoiceDetailVO {

    private String eppName;
    private String builderName;
    private String placing;
    private String stirId;
    private String personalCode;
    private String personalName;
    // 发货时间
    private Timestamp sendTime;

    private String placeTypeName;

    // 泵车id
    private String jumpVehicleID;
    private String jumpVehicleName;
    //砼方量
    private double threeProduceNum;

    private double saleNum;
    //生产方量
    private double produceNum;

    private String stgId;

    // 石子粒径
    private String stoneDia;
    // 石料要求
    private String stoneAsk;

    // 塌落度
    private String slump;

    // 水泥品种
    private String cementVName;

    // 离场时间
    private Timestamp leaveTime;

    // 回厂时间
    private Timestamp arriveTime;

    private double taWeight;
    private double grWeight;
    private double netWeight;
    // 签收状态
    private Integer upStatus;
    // 发货人名称
    private String empname;
    //运输距离
    private double distance;
    private String vehicleID;
    private String saleFileImage;
    private int weightType;
    private String weightTypeName;
    private String invoiceTypeName;
    private String eppCode;
}
