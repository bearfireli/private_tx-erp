package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskPlanListVO {

    private String preTime;  //预计时间
    private String createTime; //时间
    private String taskId;
    private String eppName;
    private String builderName;
    private String placing;    // 浇筑部位
    private BigDecimal preNum;  //预计方量
    private BigDecimal overNum; // 完成方量
    private String concreteMark;  // 砼标记
    private Integer taskStatus;
    private Integer verifyStatus;
    private String preRemark;
    private String placeStyle;   //浇筑方式
    private String placeStyleName;
    private String stoneAskName;  //石料要求
    private String taskStatusName;
    private double produceNum;
    private String OpenTime;  //开盘时间
    private String stgId;   //砼标号
    private String slump;   //坍塌度
    private String attribute;  //砼特性

}
