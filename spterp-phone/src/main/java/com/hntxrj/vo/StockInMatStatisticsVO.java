package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

/*原材料汇总统计*/
@Data
public class StockInMatStatisticsVO {
    /*材料名称*/
    private  String matName;
    /*入库量*/
    private double clWeight;
    /*车数*/
    private Integer carcount;
    private String matSpecs;

}
