package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.PowerTankControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerTankControlRepository extends JpaRepository<PowerTankControl,String> {
}
