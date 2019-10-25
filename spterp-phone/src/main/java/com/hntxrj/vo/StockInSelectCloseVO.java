package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
/*原材料过磅查询*/
@Data
public class StockInSelectCloseVO {
    /*结算入库量*/
    private BigDecimal clWeight;
}
