package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.hntxrj.txerp.entity.base.ProjectLifespanKeys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (ProjectLifespan)表数据库访问层
 *
 * @author zzlhr
 * @since 2018-10-15 09:35:24
 */
@Repository
public interface ProjectLifespanRepository extends JpaRepository<ProjectLifespan, ProjectLifespanKeys> {

}