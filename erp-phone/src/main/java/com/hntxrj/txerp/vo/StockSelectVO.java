package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockSelectVO implements Serializable {
    private String stoName;   // 库位名称
    private String stkCode;   // 库位代号
    private Integer stirId;   // 线号

}
