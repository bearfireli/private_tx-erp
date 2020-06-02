package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskPriceMarkup {
    private String compid;              // 企业id
    private String TaskId;              // 任务单号
    private String PPCode;              // 加价项目
    private BigDecimal PMarkupNum;      // 加价数量
    private BigDecimal UnitPrice;       // 单价
    private BigDecimal SelfDiscPrice;   // 自卸价
    private BigDecimal JumpPrice;       // 泵送价
    private BigDecimal TowerCranePrice; // 塔吊价
    private BigDecimal OtherPrice;      // 其他价
    private Byte RecStatus;             // 启用表示；1:启用，0:禁用
    private Byte UpDown;
    private Integer UpDownMark;         // 同步标识
}
