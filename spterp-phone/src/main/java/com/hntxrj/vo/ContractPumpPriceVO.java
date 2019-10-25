package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractPumpPriceVO {

    private String contractDetailCode;

    private Integer pumpType;

    /**
     * 泵车类别名称
     */
    private String pumpTypeName;

    /**
     * 泵送单价
     */
    private BigDecimal pumpPrice;

    /**
     * 台班费
     */
    private BigDecimal tableExpense;
}
