package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskSaleInvoiceSumVO implements Serializable {
    private int totalCarNum;   //总车数
    private double produceNum;  //总方量
    private double distance;   //总运距



}
