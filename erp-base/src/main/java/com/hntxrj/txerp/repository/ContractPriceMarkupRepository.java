package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.ContractPriceMarkup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPriceMarkupRepository extends JpaRepository<ContractPriceMarkup, Integer> {
}
