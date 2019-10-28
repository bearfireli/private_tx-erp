package com.hntxrj.txerp.entity.sell;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class GradePrice implements Serializable {

    /* 标号价格id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gpId;
    /* 企业id */
    private Integer enterpriseId;
    /* 标号(强度) */
    private String stgId;
    /* 等级 */
    private String grade;
    /* 特性 */
    private String attribute;
    /* 泵送价 */
    private BigDecimal pumpPrice;
    /* 非泵价 */
    private BigDecimal noPumpPrice;
    /* 塔吊价 */
    private BigDecimal towerCranePrice;
    /* 差价 */
    private BigDecimal priceDifference;
    /* 重量折方系数 */
    private BigDecimal weightConvertCube;
    /* 是否默认，0不默认，1默认 */
    @Column(name = "`default`")
    private Integer default_;
    /* 删除否 */
    private Integer del;
    /* 创建用户 */
    private Integer createUser;
    /* 创建时间 */
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /* 更新用户 */
    private Integer updateUser;
    /* 更新时间 */
    @Column(insertable = false, updatable = false)
    private Date updateTime;

}
