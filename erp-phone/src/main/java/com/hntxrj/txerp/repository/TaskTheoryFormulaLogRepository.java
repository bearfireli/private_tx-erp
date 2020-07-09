package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskTheoryFormulaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTheoryFormulaLogRepository extends JpaRepository<TaskTheoryFormulaLog, String> {
}
