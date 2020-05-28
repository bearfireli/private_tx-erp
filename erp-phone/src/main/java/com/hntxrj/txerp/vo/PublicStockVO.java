package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicStockVO implements Serializable {
    private Integer id;
    private String compid;
    private Integer stirId;
    private Integer erpStockCode;     //ERP仓位序号
    private Integer produceStockCode; //生产仓位序号
    private Integer produceConsume;   //生产消耗值
    private Byte publicUse;           //是否公共 0:不公共； 1:公共
    private Integer publicStirId;     //公共仓序号
    private Byte upDown;
    private Integer upDownMark;
    private Byte recStatus;


}
