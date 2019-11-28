package com.hntxrj.txerp.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class SupNamePieChartVO implements Serializable {

    //供应商
    private String supName;

    /*入库量*/
    private double tlWeight;




}
