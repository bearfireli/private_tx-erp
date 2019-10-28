package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.ContractPumpPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPumpPriceRepository
        extends JpaRepository<ContractPumpPrice, Integer> {
}
