package com.hntxrj.dao;

import com.hntxrj.entity.UserPermissionGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lhr
 * @create 2018/2/1
 */
@Repository
public interface UserPermissionGroupsDao extends JpaRepository<UserPermissionGroups, String> {

    List<UserPermissionGroups> findByOpId(String opId);


    void deleteByOpId(String opId);

    List<UserPermissionGroups> findByOpIdIn(List<String> opids);



}
