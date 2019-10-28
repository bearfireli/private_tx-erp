package com.hntxrj.txerp.entity.sell;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@Table(name = "contract_master")
public class Contract implements Serializable {

    /**
     * 合同状态
     */
    public static final int STATUS_WORKING = 1; // 未完成
    public static final int STATUS_OVER = 2;    // 已完成
    public static final int STATUS_STOP = 3;    // 暂停
    public static final int STATUS_CANCEL = 4;  // 作废


    @Id
    /* 合同uid */
    private String cmUid;
    /* 合同代号 */
    private String cmId;
    /* 销售员uid */
    private Integer salesmanUid;
    /* 签订时间 */
    private Date signDate;
    /* 生效时间 */
    private Date effectDate;
    /* 到期时间 */
    private Date expiresDate;
    /* 合同类型 */
    private Integer cmType;
    /* 结算日 */
    private Integer accountDay;
    /* 锁定日 */
    private Integer lockDay;
    /* 锁定比率 */
    private BigDecimal lockRate;
    /* 联系人 */
    private String linkMan;
    /* 联系电话 */
    private String linkTel;
    /* 价格执行方式0合同价 1 市场价 2浮动价 3特殊价 */
    private Integer priceStyle;
    /* 浮动范围 */
    private BigDecimal priceFloatLimits;
    /* 付款方式 */
    private Integer payType;
    /* 计算方式 */
    private String computeStyle;
    /* 创建人代号 */
    private Integer createUid;
    /* 创建时间 */
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /* 更新人 */
    private Integer updateUid;
    /* 更新时间 */
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    /* 付款比率 */
    private BigDecimal payRate;
    /* 记录有效否 */
    private Integer rowStatus;
    /* 同步状态 */
    private Integer sysnStatus;
    /* 所属企业 */
    private Integer enterpriseId;
    /* null */
    private String expand1;
    /* 拓展字段 */
    private String expand2;
    /* 备注 */
    private String remark;

}
