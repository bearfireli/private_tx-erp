package com.hntxrj.txerp.entity.sell;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class ContractGradePrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 合同标号价格id */
    private Integer cgpId;
    /* null */
    private Integer enterprise;
    /* 子合同uid */
    private String cdUid;
    /* 主合同uid */
    private String cmUid;
    /* 标号名称 */
    private String stgId;
    /* 非泵价格 */
    private BigDecimal noPumpPrice;
    /* null */
    private BigDecimal pumpPrice;
    /* 塔吊价 */
    private BigDecimal towerCranePrice;
    /* 差价 */
    private BigDecimal priceDifference;
    /* 价格开始执行时间 */
    private Date priceBeginTime;
    /* 价格结束时间 */
    private Date priceStopTime;
    /* 特征 */
    private String attribute;
    /* 创建时间 */
    @Column(updatable = false, insertable = false)
    private Date createTime;
    /* 操作用户id */
    private Integer updateUser;
    /* 审核状态 */
    private Integer verifyStatus;
    /* 审核人id */
    private Integer verifyUser;
    /* 审核时间 */
    private Date verifyTime;
    /* 删除否 */
    private Integer del;

}
