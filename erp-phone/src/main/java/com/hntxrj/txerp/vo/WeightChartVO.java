package com.hntxrj.txerp.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class WeightChartVO implements Serializable {


    //入库量
    private double tlWeight;
    //供应商
    private String supName;
    //材料名称
    private String matName;
    //入库库位
    private String stoName;






}
