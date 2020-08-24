package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author ws
 */
@Data
public class EppListVO {

    private String eppCode;
    private String eppName;
    private String shortName;
    private String address;
    private String linkMan;
    private String linkTel;
    private String remarks;
    private String accountingAccountCode;
    private Integer recStatus;

}
