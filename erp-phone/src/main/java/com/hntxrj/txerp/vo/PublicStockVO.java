package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicStockVO implements Serializable {
    private Integer id;
    private String compid;
    private Integer stirId;
    private Integer erpStockCode;
    private Integer produceStockCode;
    private Integer produceConsume;
    private Byte publicUse;
    private Integer publicStirId;
    private Byte upDown;
    private Integer upDownMark;
    private Byte recStatus;


}
