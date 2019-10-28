package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.base.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
