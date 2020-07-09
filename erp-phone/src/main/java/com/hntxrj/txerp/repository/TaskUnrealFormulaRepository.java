package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskUnrealFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskUnrealFormulaRepository extends JpaRepository<TaskUnrealFormula, String> {
}
