package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.PowderTankCalibration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowderTankCalibrationRepository extends JpaRepository<PowderTankCalibration,String> {
}
