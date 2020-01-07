package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 派车列表
 */
@Data
public class SendCarListVO {

    private String taskId;                                  //任务单号
    private String stgId;                                 //标号
    private String eppName;                                 //工程名称
    private String builderName;
    /*预计方量*/
    private double preNum;
    //总销售量(从小票表中查询出来的)
    private double saleNumTotal;
    private String placing;                             //浇筑部位
    private String concreteMark;
    private Timestamp adjustmentTime;
    private String defaultJump;
    private double preCarNum;
    private Timestamp SendTime;
    private String placeStyleName;
    /*生产方量*/
    private double produceNum;
    /*报废方量*/
    private double scrapNum;
    /*剩料方量*/
    private double remnantNum;
    private String vehicleStatus;
    private Integer status;
    private String statusName;
    List<DispatchVehicle> cars;
    private String totalProduceNum;
    private double num;
    private String attribute;
    //签收车数
    private String vehiceNumber;
    //塌落度
    private String slump;
    //签收方量
    private String numberOfSignings;



}
