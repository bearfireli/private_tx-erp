package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 模板列表对象
 */

@Data
public class TheoryFormulaVO implements Serializable {
    private String compid;              //企业id
    private String formulaCheckCode;    //配比检验号
    private String theoryFormulaCode;   //理论配比编号
    private String stgId;               //标号强度
    private Integer identifyNumber;     //配比取值识别号
    private String slump;               //塌落度
    private Double waterCementRatio;    //水灰比
    private Double stoneRate;           //砂率
    private Double mPowderPara;         //矿粉参量
    private Double flyAshPara;          //煤灰参量
    private Double aePara;              //外加剂参量
    private String seepGrate;           //抗渗等级
    private String strengthGrate;       //抗折等级
    private String formulaClass;        //配比种类
    private String formulaType;         //配比类型
    private Integer whiskTime;          //搅拌时间
    private Integer allWater;           //全部清水
    private String remarks;             //备注
    private String createTime;          //创建时间
    private String concreteMark;        //砼标记
    private String opId;                //修改人代号
    private Double theoryTotalNum;      //理论总重量
    private Byte recStatus;                 //启用标识1:启用；0:未启用
    private Integer mixersDoorHalf;         //搅拌机门半开时间
    private Integer mixersDoorFull;         //搅拌机门全开时间

}
