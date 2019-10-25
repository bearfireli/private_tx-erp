package com.hntxrj.repository;

import com.hntxrj.entity.TaskPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPlanRepository extends JpaRepository<TaskPlan, String> {
}
