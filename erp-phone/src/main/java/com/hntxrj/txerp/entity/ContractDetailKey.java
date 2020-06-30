package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * SM_ContractDetail
 * @author lhr
 */
@Data
public class ContractDetailKey implements Serializable {
    /**
     * 主合同UID号
     */
    private String contractUID;

    /**
     * 子合同号
     */
    private String ccontractCode;

    private static final long serialVersionUID = 1L;
}