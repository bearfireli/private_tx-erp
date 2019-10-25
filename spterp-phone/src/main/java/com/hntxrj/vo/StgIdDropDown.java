package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 标号下拉
 */
@Data
public class StgIdDropDown {

    private String compid;
    private String stgId;
    private BigDecimal notPumpPrice;
    private BigDecimal pumpPrice;
    private boolean isDefault;
    /**
     * 塔吊价
     */
    private BigDecimal towerCranePrice;


}
