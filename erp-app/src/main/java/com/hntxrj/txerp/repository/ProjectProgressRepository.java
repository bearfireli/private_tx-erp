package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.ProjectProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectProgressRepository extends JpaRepository<ProjectProgress, Integer> {

}
