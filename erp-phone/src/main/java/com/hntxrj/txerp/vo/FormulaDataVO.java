package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FormulaDataVO implements Serializable {
    private String mat;             //材料名称
    private BigDecimal matValue;    //材料用量
    private BigDecimal wr;          //含水率
    private String stockName;       //库位名称
    private String matParent;       //材料父id
}
