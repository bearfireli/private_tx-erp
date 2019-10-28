package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
/*原材料过磅统计，车辆代号*/
@Data
public class WeightVechicIdVO {
    /*车辆代号*/
    private String vehicleId;
    /*入库方量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;

}
