package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
/*原材料过磅砼统计。过磅员*/
@Data
public class WeightEmpNameVO {
    /*过磅员*/
    private String empName;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;
}
