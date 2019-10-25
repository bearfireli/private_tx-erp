package com.hntxrj.dao;

import com.hntxrj.entity.UserPermissionGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhr
 * @create 2018/1/31
 */
@Repository
public interface UserPermissionGroupDao extends JpaRepository<UserPermissionGroup, Integer> {

    List<UserPermissionGroup> findByCompidAndGroupStatus(String compid, String groupStatus);

    Page<UserPermissionGroup> findByCompid(String compid, Pageable pageable);


}
