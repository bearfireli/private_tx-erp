package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * 小票列表
 */
@Data
public class TaskSaleInvoiceListVO {

    private String compid;

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
    private String placeStyleName;  //浇筑方式

    //签收状态
    private int upStatus;

    private String vehicleStatusName;

    private Integer invoiceType;
    private String invoiceTypeName;

    //砼方量
    private double threeProduceNum;
    private double taWeight;
    private double grWeight;
    private double netWeight;
    private int WeightType;
    private String WeightTypeName;
    private String slump;
    private String placing;
    private String arriveSttime;
    private double qianNum;
    private double numberOfSignings; // 签收方量
    private double saleNum;
    private String vehicleStatus;
    private String attribute;
    private String leaveTime;       //离厂时间
}
