package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.Engineering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EngineeringRepository extends JpaRepository<Engineering, Integer> {


}
