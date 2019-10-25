package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ContractGradePriceVO {

    private String compid;

    private String stgId;

    private BigDecimal notPumpPrice;

    private BigDecimal pumpPrice;

    /**
     * 差价
     */
    private BigDecimal priceDifference;

    /**
     * 塔吊价
     */
    private BigDecimal towerCranePrice;

    /**
     * 价格开始执行时间
     */
    private Timestamp priceExecuteTime;

    /**
     * 价格执行结束时间
     */
    private Timestamp priceStopTime;

}
