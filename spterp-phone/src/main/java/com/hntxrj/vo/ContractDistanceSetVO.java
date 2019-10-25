package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同运距
 */
@Data
public class ContractDistanceSetVO {
    private String contractDetailCode;
    /**
     * 运距
     */
    private BigDecimal distance;
    private String compid;
    private String compName;
}
