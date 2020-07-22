package com.hntxrj.txerp.vo;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;


/*每盘配料明细*/
@Data
public class PlatelngredientsVO {
    /*线号*/
    private Integer stirId;
    /*线号名称*/
    private String stirName;
    /*任务单号*/
    private String taskId;
    /*标号*/
    private String stgId;
    /*车号*/
    private String vehicleId;
    /*生产时间*/
    private Timestamp produceTime;

    private BigDecimal panNum;
    /*盘号*/
    private Integer panCode;
    /*浇筑部位*/
    private String placing;
    private BigDecimal totalNum;
    /*盘方量*/
    private BigDecimal qty;
    /* 配比材料名称砂一 */
    @Column(name = "mat1")
    private String mat1;
    /* 配比材料名称砂二 */
    @Column(name = "mat2")
    private String mat2;
    /* 配比材料名称砂三 */
    @Column(name = "mat3")
    private String mat3;
    /* 配比材料名称砂四 */
    @Column(name = "mat4")
    private String mat4;
    /* 配比材料名称石一 */
    @Column(name = "mat5")
    private String mat5;
    /* 配比材料名称石二 */
    @Column(name = "mat6")
    private String mat6;
    /* 配比材料名称石三 */
    @Column(name = "mat7")
    private String mat7;
    /* 配比材料名称石四 */
    @Column(name = "mat8")
    private String mat8;
    /* 配比材料名称水泥一 */
    @Column(name = "mat9")
    private String mat9;
    /* 配比材料名称水泥二 */
    @Column(name = "mat10")
    private String mat10;
    /* 配比材料名称水泥三 */
    @Column(name = "mat11")
    private String mat11;
    /* 配比材料名称水泥四 */
    @Column(name = "mat12")
    private String mat12;
    /* 配比材料名称矿粉 */
    @Column(name = "mat13")
    private String mat13;
    /* 配比材料名称粉煤灰 */
    @Column(name = "mat14")
    private String mat14;
    /* 配比材料名称粉外剂 */
    @Column(name = "mat15")
    private String mat15;
    /* 配比材料名称膨胀剂 */
    @Column(name = "mat16")
    private String mat16;
    /* 配比材料名称外加剂一 */
    @Column(name = "mat17")
    private String mat17;
    /* 配比材料名称外加剂二 */
    @Column(name = "mat18")
    private String mat18;
    /* 配比材料名称外加剂三 */
    @Column(name = "mat19")
    private String mat19;
    /* 配比材料名称外加剂四 */
    @Column(name = "mat20")
    private String mat20;
    /* 配比材料名称清水一 */
    @Column(name = "mat21")
    private String mat21;
    /* 配比材料名称清水二 */
    @Column(name = "mat22")
    private String mat22;
    /* 配比材料名称回收水 */
    @Column(name = "mat23")
    private String mat23;
    /* 预设值砂一 */
    @Column(name = "matv1")
    private BigDecimal matV1;
    /* 预设值砂二 */
    @Column(name = "matv2")
    private BigDecimal matV2;
    /* 预设值砂三 */
    @Column(name = "matv3")
    private BigDecimal matV3;
    /* 预设值砂四 */
    @Column(name = "matv4")
    private BigDecimal matV4;
    /* 预设值石一 */
    @Column(name = "matv5")
    private BigDecimal matV5;
    /* 预设值石二 */
    @Column(name = "matv6")
    private BigDecimal matV6;
    /* 预设值石三 */
    @Column(name = "matv7")
    private BigDecimal matV7;
    /* 预设值石四 */
    @Column(name = "matv8")
    private BigDecimal matV8;
    /* 预设值水泥一 */
    @Column(name = "matv9")
    private BigDecimal matV9;
    /* 预设值水泥二 */
    @Column(name = "matv10")
    private BigDecimal matV10;
    /* 预设值水泥三 */
    @Column(name = "matv11")
    private BigDecimal matV11;
    /* 预设值水泥四 */
    @Column(name = "matv12")
    private BigDecimal matV12;
    /* 预设值矿粉 */
    @Column(name = "matv13")
    private BigDecimal matV13;
    /* 预设值粉煤灰 */
    @Column(name = "matv14")
    private BigDecimal matV14;
    /* 预设值粉外剂 */
    @Column(name = "matv15")
    private BigDecimal matV15;
    /* 预设值膨胀剂 */
    @Column(name = "matv16")
    private BigDecimal matV16;
    /* 预设值外加剂一 */
    @Column(name = "matv17")
    private BigDecimal matV17;
    /* 预设值外加剂二 */
    @Column(name = "matv18")
    private BigDecimal matV18;
    /* 预设值外加剂三 */
    @Column(name = "matv19")
    private BigDecimal matV19;
    /* 预设值外加剂四 */
    @Column(name = "matv20")
    private BigDecimal matV20;
    /* 预设值清水一 */
    @Column(name = "matv21")
    private BigDecimal matV21;
    /* 预设值清水二 */
    @Column(name = "matv22")
    private BigDecimal matV22;
    /* 预设值回收水 */
    @Column(name = "matv23")
    private BigDecimal matV23;
    /* 称量值砂一 */
    @Column(name = "matl1")
    private BigDecimal matL1;
    /* 称量值砂二 */
    @Column(name = "matl2")
    private BigDecimal matL2;
    /* 称量值砂三 */
    @Column(name = "matl3")
    private BigDecimal matL3;
    /* 称量值砂四 */
    @Column(name = "matl4")
    private BigDecimal matL4;
    /* 称量值石一 */
    @Column(name = "matl5")
    private BigDecimal matL5;
    /* 称量值石二 */
    @Column(name = "matl6")
    private BigDecimal matL6;
    /* 称量值石三 */
    @Column(name = "matl7")
    private BigDecimal matL7;
    /* 称量值石四 */
    @Column(name = "matl8")
    private BigDecimal matL8;
    /* 称量值水泥一 */
    @Column(name = "matl9")
    private BigDecimal matL9;
    /* 称量值水泥二 */
    @Column(name = "matl10")
    private BigDecimal matL10;
    /* 称量值水泥三 */
    @Column(name = "matl11")
    private BigDecimal matL11;
    /* 称量值水泥四 */
    @Column(name = "matl12")
    private BigDecimal matL12;
    /* 称量值矿粉 */
    @Column(name = "matl13")
    private BigDecimal matL13;
    /* 称量值粉煤灰 */
    @Column(name = "matl14")
    private BigDecimal matL14;
    /* 称量值粉外剂 */
    @Column(name = "matl15")
    private BigDecimal matL15;
    /* 称量值膨胀剂 */
    @Column(name = "matl16")
    private BigDecimal matL16;
    /* 称量值外加剂一 */
    @Column(name = "matl17")
    private BigDecimal matL17;
    /* 称量值外加剂二 */
    @Column(name = "matl18")
    private BigDecimal matL18;
    /* 称量值外加剂三 */
    @Column(name = "matl19")
    private BigDecimal matL19;
    /* 称量值外加剂四 */
    @Column(name = "matl20")
    private BigDecimal matL20;
    /* 称量值清水一 */
    @Column(name = "matl21")
    private BigDecimal matL21;
    /* 称量值清水二 */
    @Column(name = "matl22")
    private BigDecimal matL22;
    /* 称量值回收水 */
    @Column(name = "matl23")
    private BigDecimal matL23;

}
