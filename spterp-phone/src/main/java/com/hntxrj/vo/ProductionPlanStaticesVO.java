package com.hntxrj.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *  生产计划统计
 *  Taskid 任务单号
 *  Stgid  标号，强度
 *  Prenum  预计方量
 *  PreRemark 预计备注
 *  Slump  塌落度
 *  TechnicalRequirements  其他技术要求
 *  PreTime  预计时间
 *  StoneDia 石子立径
 *  TotalNum 总运输方量
 *  Placing 浇筑部位
 *  PreCarNum
 *  BuilderName 客户全称
 *  EPPName 工程名称
 *  LinkTel 现场联系电话
 *  PI_TypeName AS PlaceStyleName 信息类别别称
 *  ID
 */
@Data
public class ProductionPlanStaticesVO  {

    private Integer ID;
    private String taskId;
    private String stgId;
    private Double preNum;
    private String preRemark;
    private String slump;
    private String technicalRequirements;
    private Timestamp preTime;
    private String stoneDia;
    private Double totalNum;
    private String placing;
    private Integer preCarNum;
    private String builderName;
    private String ePPName;
    private String linkTel;
    private String placeStyleName;
    private Double totalPreNum;



}













