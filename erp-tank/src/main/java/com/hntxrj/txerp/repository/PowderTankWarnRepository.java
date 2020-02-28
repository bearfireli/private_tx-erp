package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.PowderTankWarn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowderTankWarnRepository extends JpaRepository<PowderTankWarn,String> {
}
