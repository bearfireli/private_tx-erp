package com.hntxrj.txerp.repository

import com.hntxrj.txerp.entity.SMContractDetail
import com.hntxrj.txerp.entity.SMContractDetailPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractDetailRepository: JpaRepository<SMContractDetail, SMContractDetailPK> {
}