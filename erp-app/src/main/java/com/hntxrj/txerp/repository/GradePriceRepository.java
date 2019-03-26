package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.GradePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradePriceRepository extends JpaRepository<GradePrice, Integer> {
}
