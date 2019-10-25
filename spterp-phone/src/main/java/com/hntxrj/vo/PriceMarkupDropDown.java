package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 特殊材料下拉
 */
@Data
public class PriceMarkupDropDown {

    /**
     * 特殊材料名称
     */
    private String pName;

    private String pCode;

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
