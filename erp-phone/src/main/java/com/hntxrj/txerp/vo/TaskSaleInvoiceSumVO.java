package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskSaleInvoiceSumVO implements Serializable {
    private int totalCarNum;   //总车数
    private double totalSaleNum;  //总发货方量
    private double totalSignNum;  //总签收方量
    private double distance;   //总运距


}
