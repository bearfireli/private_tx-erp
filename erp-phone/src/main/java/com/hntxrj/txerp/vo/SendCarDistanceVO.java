package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SendCarDistanceVO {
    private Timestamp sendTime;
    private Timestamp arriveStTime;
    private String vehiceNumber;
    private String vehicleId;
    private String vehicleStatus;
    private double saleNum;
    /*生产方量*/
    private double produceNum;
    /*退料方量*/
    private double returnNum;
    /*剩料方量*/
    private  double remnantNum;
    /*分料方量*/
    private  double separateNum;
    /*报废方量*/
    private  double scrapNum;
    private double totalNum;
    private  String veicleStatusNama;
    private  String personalName;
    private  String empName;
    private double printTotalNum;
    private double threeProduceNum;
    private String totalProduceNum;
    private String totalScraoNum;
    private double num;
}
