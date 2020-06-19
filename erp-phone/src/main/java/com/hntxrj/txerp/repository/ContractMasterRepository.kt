package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.SMContractMaster;
import com.hntxrj.txerp.entity.SMContractMasterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ContractMasterRepository: JpaRepository<SMContractMaster, SMContractMasterPK> {
}
