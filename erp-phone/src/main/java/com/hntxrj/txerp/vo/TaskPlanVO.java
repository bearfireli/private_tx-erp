package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class TaskPlanVO {

    private String compid;
    private String taskId;
    private String contractId;
    private String contractUID;
    private String contractDetailCode;  //子合同号
    private String eppCode;
    private String eppName;
    private String builderCode;
    private String builderName;
    private String grade;          //抗折等级
    private String stgId;
    private BigDecimal overNum;     // 完成方量
    private String attribute;      //特性
    private String technicalRequirements;  //技术要求
    private String stoneyAsk;        //石料要求
    private String distance;
    private String address;      //交货地址
    private String placing;
    private String preNum;       //预计方量
    private String concreteMark;
    private String preCarNum;
    private Timestamp preTime;    //预计时间
    private String linkTel;
    private String linkMan;       //现场人员
    private String stoneAsk;
    private String stoneAskName;
    private String stoneDia;       // 石子粒径
    private String slump;           // 塌落度
    private String cementVariety;     // 水泥品种
    private String cementVarietyName;
    private boolean verifyStatus;
    private Integer taskStatus;
    private String taskStatusName;
    private String placeStyle;
    private String placeStyleName;
    private String preRemark;
    private boolean receiptStatus;
    private boolean excess;
    private boolean tax;

    private List<String> ppCodes;
}
