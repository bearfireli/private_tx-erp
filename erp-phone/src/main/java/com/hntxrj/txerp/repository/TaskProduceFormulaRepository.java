package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskProduceFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProduceFormulaRepository extends JpaRepository<TaskProduceFormula, String> {
}
