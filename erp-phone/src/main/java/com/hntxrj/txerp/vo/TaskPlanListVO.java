package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskPlanListVO {

    private String preTime;  //预计时间
    private String createTime; //时间
    private String taskId;   //任务单号
    private String eppName;  // 工程名称
    private String builderName; // 施工单位
    private String placing;    // 浇筑部位
    private BigDecimal preNum;  //预计方量
    private BigDecimal overNum; // 完成方量
    private String concreteMark;  // 砼标记
    private Integer taskStatus;   //任务状态代号
    private String taskStatusName;  //任务单状态名称
    private Integer verifyStatus;  //审核状态
    private String preRemark;  //备注
    private String placeStyle;   //浇筑方式代号
    private String placeStyleName;  //浇筑方式名称
    private String stoneAskName;  //石料要求
    private double produceNum;  //生产方量
    private String OpenTime;  //开盘时间
    private String stgId;   //砼标号
    private String slump;   //坍塌度
    private String attribute;  //砼特性
    private String ccontractCode; // 子合同号
    private String compid; //公司代号

}
