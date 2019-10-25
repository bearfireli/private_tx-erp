package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TaskPlanVO {

    private String taskId;
    private String contractId;
    private String contractUID;
    private String cContractCode;
    private String eppCode;
    private String eppName;
    private String builderCode;
    private String builderName;
    private String grade;
    private String stgId;
    // 完成方量
    private BigDecimal overNum;
    private String attribute;
    private String technicalRequirements;
    private String stoneyAsk;
    private String distance;
    private String address;
    private String placing;
    private String preNum;
    private String concreteMark;
    private String preCarNum;
    private Timestamp preTime;
    private String linkTel;
    private String linkMan;
    private String stoneAsk;
    private String stoneAskName;
    // 石子粒径
    private String stoneDia;
    // 塌落度
    private String slump;
    // 水泥品种
    private String cementVariety;
    private String cementVarietyName;
    private boolean verifyStatus;
    private Integer taskStatus;
    private String taskStatusName;
    private String placeStyle;
    private String placeStyleName;
    private String preRemark;
    private boolean receiptStatus;
}
