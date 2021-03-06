package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

/*原材料过磅统计*/
/*材料名称*/
@Data
public class WeightMatNameVO {
    /*材料名称*/
    private String matName;
    /*材料详情向后台传递的材料名称标识*/
    private String mat;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;
    /*规格*/
    private String matSpecs;

    /*材料类别名称*/
    private String matParentName;

    /*材料类别编号*/
    private String matParentCode;

}
