package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class TheoryDetailVO implements Serializable {
    private String theoryFormulaCode;
    private String formulaCode;
    private String stgId;
    private String slump;
    private Integer whiskTime;
    private Boolean verifyStatus;
    private String verifyId;
    private Timestamp verifyTime;
    private Integer mixersDoorHalf;
    private Integer mixersDoorFull;

    private List<FormulaDataVO> formulas;   //配比数据列表
}
