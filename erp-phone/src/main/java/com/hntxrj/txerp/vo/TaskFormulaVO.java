package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskFormulaVO implements Serializable {
    private String compid;              //企业id
    private String taskId;              //任务单号
    private String formulaCode;         //实际配比编号
    private Integer stirId;              //楼号
    private String theoryFormulaCode;   //理论配比编号
    private Byte verifyStatus;        //审核状态
    private String verifyId;            //审核员id
    private String verifyTime;          //审核时间
    private String opId;                //配比制作人代号
    private String stgId;               //标号强度
    private String slump;               //塌落度
    private Integer whiskTime;          //搅拌时间
    private Double wr1;                 //含水率砂一
    private Double wr2;
    private Double wr3;
    private Double wr4;
    private Double wr5;
    private Double wr6;
    private Double wr7;
    private Double wr8;
    private Double matV1;               //理论用量一
    private Double matV2;
    private Double matV3;
    private Double matV4;
    private Double matV5;
    private Double matV6;
    private Double matV7;
    private Double matV8;
    private Double matV9;
    private Double matV10;
    private Double matV11;
    private Double matV12;
    private Double matV13;
    private Double matV14;
    private Double matV15;
    private Double matV16;
    private Double matV17;
    private Double matV18;
    private Double matV19;
    private Double matV20;
    private Double matV21;
    private Double matV22;
    private Double matV23;
    private Double matV24;
    private Double matV25;
    private Double matV26;
    private Double matV27;
    private String mat1;                        //材料名称规格一
    private String mat2;
    private String mat3;
    private String mat4;
    private String mat5;
    private String mat6;
    private String mat7;
    private String mat8;
    private String mat9;
    private String mat10;
    private String mat11;
    private String mat12;
    private String mat13;
    private String mat14;
    private String mat15;
    private String mat16;
    private String mat17;
    private String mat18;
    private String mat19;
    private String mat20;
    private String mat21;
    private String mat22;
    private String mat23;
    private String mat24;
    private String mat25;
    private String mat26;
    private String mat27;
    private Double totalNum;                    //总重量
    private Byte recStatus;                     //启用标识1:启用；0:未启用
    private Integer mixersDoorHalf;             //搅拌机门半开时间
    private Integer mixersDoorFull;             //搅拌机门全开时间
}
