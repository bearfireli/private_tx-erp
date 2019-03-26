package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.ContractGradePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractGradePriceRepository extends JpaRepository<ContractGradePrice, Integer> {
}
