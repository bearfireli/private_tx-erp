package com.hntxrj.txerp.vo;

import lombok.Data;

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
    /*材料名称*/
    private String materialName;
}
