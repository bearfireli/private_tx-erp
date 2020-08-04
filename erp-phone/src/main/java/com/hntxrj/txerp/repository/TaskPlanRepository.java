package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.TaskPLanPK;
import com.hntxrj.txerp.entity.TaskPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPlanRepository extends JpaRepository<TaskPlan, TaskPLanPK> {
}
