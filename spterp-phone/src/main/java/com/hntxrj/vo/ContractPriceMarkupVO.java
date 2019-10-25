package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同特殊材料对象
 */
@Data
public class ContractPriceMarkupVO {

    private String contractDetailCode;
    /**
     * 特殊材料名称
     */
    private String pName;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 泵送价格
     */
    private BigDecimal jumpPrice;

    /**
     * 自卸价
     */
    private BigDecimal selfDiscPrice;
    /**
     * 塔吊价
     */
    private BigDecimal towerCranePrice;

    private BigDecimal otherPrice;

    private boolean isDefault;

}
