package com.hntxrj.txerp.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 企业订单
 * @author lhr
 * @date 2018-02-26 09:31:19
 */

@Entity
@Data
@ToString
public class UserCompOrder implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 订单金额
     */
    private BigDecimal orderPrice;

    /**
     * 创建时间
     */
    private java.sql.Timestamp createTime;

    /**
     * 创建管理员
     */
    private String createAdmin;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 发票状态
     */
    private Integer orderInvoiceStatus;

    /**
     * 支付类型
     */
    private Integer orderPayType;

    /**
     * 订单支付备注
     */
    private String orderPayRemark;

    /**
     * 订单所属企业
     */
    @OneToOne(targetEntity = UserComp.class)
    @JoinColumn(name = "order_comp", referencedColumnName = "compid")
    private UserComp orderComp;


}
