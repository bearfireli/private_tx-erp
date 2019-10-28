package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ContractListVO {

    private String contractId;

    private String contractUid;

    private String contractDetailCode;
    private Integer compId;

    private String eppName;

    private String builderName;
    private String builderCode;
    private String eppCode;
    private  String address;
    /**
     * 合同方量
     */
    private BigDecimal contractNum;

    private Integer contractStatus;

    /**
     * 签订时间
     */
    private Timestamp signDate;

    private String salesman;
    private String scaleName;

    private boolean verifyStatus;
}
