package com.hntxrj.txerp.entity.sell;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PumpPrice implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* null */
    private Integer ppId;
    /* 泵车类别名称 */
    private String typeName;
    /* 泵车类别代号 */
    private String typeCode;
    /* 泵送单价 */
    private BigDecimal pricePump;
    /* 台班费 */
    private BigDecimal tableExpense;
    /* 创建时间 */
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /* 创建用户 */
    private Integer createUser;
    /* 记录状态 */
    private Integer del;
    /* 企业id */
    private Integer enterprise;

}
