package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.PowderTankControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowderTankControlRepository extends JpaRepository<PowderTankControl,String> {
}
