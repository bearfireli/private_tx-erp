package com.hntxrj.txerp.entity.sell;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class ContractPumpPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 主键 */
    private Integer cppId;
    /* 泵车类别 */
    private Integer pumpType;
    /* 泵送单价 */
    private BigDecimal pumpPrice;
    /* 台班费 */
    private BigDecimal tableExpense;
    /* 子合同id */
    private String cdUid;
    /* 创建时间 */
    private Date createTime;
    /* 车号 */
    private Integer vehicleId;
    /* 企业id */
    private Integer enterprise;
    /* 删除否 */
    private Integer del;

}
