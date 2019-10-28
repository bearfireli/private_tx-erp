package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
/*原材料过磅汇总*/
@Data
public class StockInCollectCloseVO {
    /*结算入库量*/
    private double totalLWeight;
    private String matSpecs;
}
