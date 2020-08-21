package com.hntxrj.txerp.vo;

import com.hntxrj.txerp.entity.ContractDetail;
import com.hntxrj.txerp.entity.ContractMaster;

import java.util.List;

/**
 * 合同详情
 *
 * @author haoranliu
 */
public class ContractMasterDetailsVO {
    private ContractMaster contractMaster;
    private List<ContractDetail> contractDetails;


    public ContractMaster getContractMaster() {
        return contractMaster;
    }

    public void setContractMaster(ContractMaster contractMaster) {
        this.contractMaster = contractMaster;
    }

    public List<ContractDetail> getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(List<ContractDetail> contractDetails) {
        this.contractDetails = contractDetails;
    }

    @Override
    public String toString() {
        return "ContractMasterDetailsVO{" +
                "contractMaster=" + contractMaster +
                ", contractDetails=" + contractDetails +
                '}';
    }

    public ContractMasterDetailsVO() {
    }

    public ContractMasterDetailsVO(ContractMaster contractMaster, List<ContractDetail> contractDetails) {
        this.contractMaster = contractMaster;
        this.contractDetails = contractDetails;
    }
}
