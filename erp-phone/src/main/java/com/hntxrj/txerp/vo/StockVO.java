package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class StockVO {
    private Integer id;
    private String stkCode;
    private String stoName;
    //材料归属
    private String matParent;
    // 最大库存
    private BigDecimal stoMaxQty;
    // 最小库存
    private BigDecimal stoMinQty;
    // 当前库存
    private BigDecimal stoCurQty;
    // 单位
    private String measureUnit;
    private Integer stirId;
    private String compid;
    // 材料代号
    private Integer oderBy;
    private String matCode;
    private String matName;
    //线号名称
    private String stirName;
    //称重值
    private BigDecimal stoWeight;

    //库位显示序号
    private Integer sortBy;


}
