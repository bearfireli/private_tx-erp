package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.PriceMarkup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceMarkupRepository extends JpaRepository<PriceMarkup, Integer> {
}
