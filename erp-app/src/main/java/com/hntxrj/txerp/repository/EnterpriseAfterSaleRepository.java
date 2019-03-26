package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.EnterpriseAfterSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseAfterSaleRepository
        extends JpaRepository<EnterpriseAfterSale, Integer> {
}
