package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.Date;

/**
 * 小票列表(司机端)
 */
@Data
public class TaskSaleInvoiceDriverListVO {

    private Integer id;
    // 车号
    private String vehicleID;
    // 司机编号
    private String personalCode;
    //司机名称
    private String personalName;
    // 车数
    private Integer vehiceNumber;
    private String placing;
    private String taskId;
    private Date sendTime;
    private String eppName;
    private String builderName;
    private String eppCode;
    private String builderCode;
    private String stgId;
    private String placeStyleName;

    //签收状态
    private int upStatus;


    private String vehicleStatusName;

    private int invoiceType;
    private String invoiceTypeName;

    //砼方量
    private double threeProduceNum;
    private double taWeight;
    private double grWeight;
    private double netWeight;
    private double totalNum;// 累计方量
    private double numberOfSignings; // 签收方量
    private double saleNum; // 销售方量

    private String receiptMan;   //签收人
    private String saleFileImage;   //签收人签名

    private String jumpVehicleID; // 泵车号
    private String slump;
    private int vehicleStatus;
}
