package com.hntxrj.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 派车列表
 */
@Data
public class SendCarListVO {

    private String taskId;
    private String stgId;
    private String eppName;
    private String builderName;
    /*预计方量*/
    private double preNum;
    private double saleNumTotal;
    private String placing;
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
    List<DriverShiftLEDVO> cars;
    private String totalProduceNum;
    private double num;
    private String attribute;
    private String vehiceNumber;


}
