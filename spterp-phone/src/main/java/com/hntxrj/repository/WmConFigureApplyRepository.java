package com.hntxrj.repository;

import com.hntxrj.entity.TaskPlan;
import com.hntxrj.entity.WmConFigureApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WmConFigureApplyRepository extends JpaRepository<WmConFigureApply, String> {
}
