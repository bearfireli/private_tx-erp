package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.PowderTankDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowderTankDeviceRepository extends JpaRepository<PowderTankDevice,String> {
}
