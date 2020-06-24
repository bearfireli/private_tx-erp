package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VehicleReturnVO implements Serializable {
    private String compid;              //企业id
    private String taskIdOld;           //剩转工地任务单号
    private String taskIdNew;           //剩转接收工地端任务单号
    private String vehicleId;           //车号
    private String sendTime;            //派车时间
    private Integer invoiceIdOld;       //原小票id
    private Integer invoiceIdNew;       //现车小票id
    private Double remnantNum;          //剩转方量
    private Double returnNum;           //退转方量
    private Double surplusNum;          //剩料报废方量
    private Double scrapNum;            //退料报废方量
    private Double separateNum;         //分料方量
    private String remarks;             //备注
    private Integer opId;               //操纵员id
    private String opName;              //操作员姓名
    private String createTime;          //操作时间
    private Integer vehicleNumberOld;   //原车次
    private Integer vehicleNumberNew;   //现车次
    private Byte isUse;                 //是否使用      0:未使用；1:已使用
    private String eppNameOld;          //原工程名称
    private String eppNameNew;          //现工程名称
    private String placingOld;          //原浇筑部位
    private String placingNew;          //现浇筑部位
    private String stgIdOld;            //原标号
    private String stgIdNew;            //现标号
    private String TechnicalRequirementsOld;//原特殊要求
    private String TechnicalRequirementsNew;//现特殊要求
}
