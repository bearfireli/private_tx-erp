package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
/*入库库位*/
public class WeightStorageVO {
    /*库位名称*/
    private String stoName;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;
}
