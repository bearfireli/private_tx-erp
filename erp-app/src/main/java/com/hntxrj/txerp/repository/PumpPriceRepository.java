package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.PumpPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PumpPriceRepository extends JpaRepository<PumpPrice, Integer> {
}
