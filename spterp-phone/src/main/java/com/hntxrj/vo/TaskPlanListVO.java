package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskPlanListVO {
    //预计时间
    private String preTime;
    //时间
    private String createTime;

    private String taskId;
    private String eppName;
    private String builderName;
    // 浇筑部位
    private String placing;
    private BigDecimal preNum;
    // 完成方量
    private BigDecimal overNum;
    // 砼标记
    private String concreteMark;
    private Integer taskStatus;
    private Integer verifyStatus;
    private String preRemark;

    private String placeStyle;
    private String placeStyleName;
    //石料要求
    private String stoneAskName;
    private String taskStatusName;
    private double produceNum;
}
