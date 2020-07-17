package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskTheoryFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTheoryFormulaRepository extends JpaRepository<TaskTheoryFormula, String> {
}
