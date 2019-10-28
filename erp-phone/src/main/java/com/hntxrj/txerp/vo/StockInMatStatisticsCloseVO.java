package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
/*原材料过磅汇总*/
@Data
public class StockInMatStatisticsCloseVO {
    /*结算购入量*/
    private BigDecimal totalLWeight;
    private String matSpecs;
}
