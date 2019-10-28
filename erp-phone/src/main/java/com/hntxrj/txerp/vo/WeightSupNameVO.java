package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeightSupNameVO {
    /*材料*/
    private String matName;
    /*供应商*/
    private String supName;
    /*联系人*/
    private String supLinkMan;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;

}
