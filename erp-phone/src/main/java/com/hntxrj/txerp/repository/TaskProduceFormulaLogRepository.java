package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskProduceFormulaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProduceFormulaLogRepository extends JpaRepository<TaskProduceFormulaLog, String> {
}
