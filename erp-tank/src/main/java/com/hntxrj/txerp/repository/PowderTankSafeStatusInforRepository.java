package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.PowderTankSafeStatusInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowderTankSafeStatusInforRepository extends JpaRepository<PowderTankSafeStatusInfor, String> {
}
