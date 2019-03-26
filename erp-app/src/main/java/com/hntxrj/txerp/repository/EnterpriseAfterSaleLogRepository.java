package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.EnterpriseAfterSaleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseAfterSaleLogRepository extends JpaRepository<EnterpriseAfterSaleLog, Integer> {
}
