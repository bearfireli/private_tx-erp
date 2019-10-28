package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

/*原材料过磅统计*/
/*材料名称*/
@Data
public class WeightMatNameVO {
    /*材料名称*/
    private String matName;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;
}
