package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * 配比备份表
 */

@Data
@Entity
@IdClass(TaskProduceFormula.TaskTheoryFormulaUK.class)
@Table(name = "LM_taskproduceformula")
public class TaskProduceFormula implements Serializable {
    @Id
    @Column(name = "compid")
    private String compid;                  //企业id
    @Id
    @Column(name = "taskid")
    private String taskId;                  //任务单号
    @Id
    @Column(name = "formulacode")
    private String formulaCode;             //实际配比编号
    @Id
    @Column(name = "stirid")
    private Integer stirId;                 //楼号
    @Column(name = "theoryformulacode")
    private String theoryFormulaCode;       //理论配比编号
    @Column(name = "verifystatus")
    private Byte verifyStatus;              //审核状态
    @Column(name = "verifyid")
    private String verifyId;                //审核员id
    @Column(name = "verifytime")
    private String verifyTime;              //审核时间
    @Column(name = "opid")
    private String opId;                    //配比制作人代号
    @Column(name = "stgid")
    private String stgId;                   //标号强度
    @Column(name = "slump")
    private String slump;                   //塌落度
    @Column(name = "whisktime")
    private Integer whiskTime;              //搅拌时间
    @Column(name = "WR1")
    private BigDecimal wr1;                 //含水率砂一
    @Column(name = "WR2")
    private BigDecimal wr2;
    @Column(name = "WR3")
    private BigDecimal wr3;
    @Column(name = "WR4")
    private BigDecimal wr4;
    @Column(name = "WR5")
    private BigDecimal wr5;
    @Column(name = "WR6")
    private BigDecimal wr6;
    @Column(name = "WR7")
    private BigDecimal wr7;
    @Column(name = "WR8")
    private BigDecimal wr8;
    @Column(name = "MatV1")
    private BigDecimal matV1;               //理论用量一
    @Column(name = "MatV2")
    private BigDecimal matV2;
    @Column(name = "MatV3")
    private BigDecimal matV3;
    @Column(name = "MatV4")
    private BigDecimal matV4;
    @Column(name = "MatV5")
    private BigDecimal matV5;
    @Column(name = "MatV6")
    private BigDecimal matV6;
    @Column(name = "MatV7")
    private BigDecimal matV7;
    @Column(name = "MatV8")
    private BigDecimal matV8;
    @Column(name = "MatV9")
    private BigDecimal matV9;
    @Column(name = "MatV10")
    private BigDecimal matV10;
    @Column(name = "MatV11")
    private BigDecimal matV11;
    @Column(name = "MatV12")
    private BigDecimal matV12;
    @Column(name = "MatV13")
    private BigDecimal matV13;
    @Column(name = "MatV14")
    private BigDecimal matV14;
    @Column(name = "MatV15")
    private BigDecimal matV15;
    @Column(name = "MatV16")
    private BigDecimal matV16;
    @Column(name = "MatV17")
    private BigDecimal matV17;
    @Column(name = "MatV18")
    private BigDecimal matV18;
    @Column(name = "MatV19")
    private BigDecimal matV19;
    @Column(name = "MatV20")
    private BigDecimal matV20;
    @Column(name = "MatV21")
    private BigDecimal matV21;
    @Column(name = "MatV22")
    private BigDecimal matV22;
    @Column(name = "MatV23")
    private BigDecimal matV23;
    @Column(name = "MatV24")
    private BigDecimal matV24;
    @Column(name = "MatV25")
    private BigDecimal matV25;
    @Column(name = "MatV26")
    private BigDecimal matV26;
    @Column(name = "MatV27")
    private BigDecimal matV27;
    @Column(name = "Mat1")
    private String mat1;                        //材料名称规格一
    @Column(name = "Mat2")
    private String mat2;
    @Column(name = "Mat3")
    private String mat3;
    @Column(name = "Mat4")
    private String mat4;
    @Column(name = "Mat5")
    private String mat5;
    @Column(name = "Mat6")
    private String mat6;
    @Column(name = "Mat7")
    private String mat7;
    @Column(name = "Mat8")
    private String mat8;
    @Column(name = "Mat9")
    private String mat9;
    @Column(name = "Mat10")
    private String mat10;
    @Column(name = "Mat11")
    private String mat11;
    @Column(name = "Mat12")
    private String mat12;
    @Column(name = "Mat13")
    private String mat13;
    @Column(name = "Mat14")
    private String mat14;
    @Column(name = "Mat15")
    private String mat15;
    @Column(name = "Mat16")
    private String mat16;
    @Column(name = "Mat17")
    private String mat17;
    @Column(name = "Mat18")
    private String mat18;
    @Column(name = "Mat19")
    private String mat19;
    @Column(name = "Mat20")
    private String mat20;
    @Column(name = "Mat21")
    private String mat21;
    @Column(name = "Mat22")
    private String mat22;
    @Column(name = "Mat23")
    private String mat23;
    @Column(name = "Mat24")
    private String mat24;
    @Column(name = "Mat25")
    private String mat25;
    @Column(name = "Mat26")
    private String mat26;
    @Column(name = "Mat27")
    private String mat27;
    @Column(name = "Totalnum")
    private Double totalNum;                    //总重量
    @Column(name = "Recstatus", columnDefinition = "1")
    private Byte recStatus;                     //启用标识1:启用；0:未启用
    @Column(name = "Updown", columnDefinition = "0")
    private Byte upDown;
    @Column(name = "Updownmark", columnDefinition = "0")
    private Integer upDownMark;
    @Column(name = "Mixersdoorfull", columnDefinition = "0")
    private Integer mixersDoorHalf;             //搅拌机门半开时间
    @Column(name = "Mixersdoorhalf", columnDefinition = "0")
    private Integer mixersDoorFull;             //搅拌机门全开时间

    @Transient
    private String formulaCheckCode;            //配比检验号，此字段为业务逻辑需要，LM_taskproduceformula表中没有

    @Transient
    private Integer identifyNumber;              //配比取值标识号,此字段为业务逻辑需要，LM_taskproduceformula表中没有


    static class TaskTheoryFormulaUK implements Serializable {
        private String compid;
        private String taskId;
        private String formulaCode;
        private Integer stirId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskTheoryFormulaUK that = (TaskTheoryFormulaUK) o;
            return compid.equals(that.compid) &&
                    taskId.equals(that.taskId) &&
                    formulaCode.equals(that.formulaCode) &&
                    stirId.equals(that.stirId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(compid, taskId, formulaCode, stirId);
        }
    }

}
