package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * 小票列表
 */
@Data
public class TaskSaleInvoiceListVO {

    private Integer id;
    // 车号
    private String vehicleID;
    // 司机编号
    private String personalCode;
    //司机名称
    private String personalName;
    // 车数
    private Integer vehiceNumber;

    private String taskId;

    private String eppName;
    private String builderName;
    private String stgId;
    private String placeStyleName;

    //签收状态
    private int upStatus;

    private String vehicleStatusName;

    private String invoiceTypeName;

    //砼方量
    private double threeProduceNum;
    private double taWeight;
    private double grWeight;
    private double netWeight;
    private int WeightType;
    private String WeightTypeName;


}
