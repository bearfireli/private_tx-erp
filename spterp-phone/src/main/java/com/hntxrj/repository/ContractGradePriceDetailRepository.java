package com.hntxrj.repository;

import com.hntxrj.entity.ContractGradePriceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractGradePriceDetailRepository extends JpaRepository<ContractGradePriceDetail, Integer> {
}
