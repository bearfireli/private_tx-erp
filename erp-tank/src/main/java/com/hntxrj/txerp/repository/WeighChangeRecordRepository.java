package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.WeightChangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeighChangeRecordRepository extends JpaRepository<WeightChangeRecord,String> {
}
