package com.hntxrj.txerp.vo;

import com.hntxrj.txerp.entity.sell.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主合同视图
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContractMasterVO extends Contract {
    /**
     * 销售员名称
     */
    private String salesmanName;
    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 更新人
     */
    private String updateUserName;
    /**
     * 企业名称
     */
    private String enterpriseName;

}
