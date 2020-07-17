package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskUnrealFormulaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskUnrealFormulaLogRepository extends JpaRepository<TaskUnrealFormulaLog, String> {
}
