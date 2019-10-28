package com.hntxrj.txerp.dao;

import com.hntxrj.txerp.entity.UserPermissionGroupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhr
 * @create 2018/1/16
 */
@Repository
public interface UserPermissionGroupDetailsDao extends JpaRepository<UserPermissionGroupDetails, Integer> {

    List<UserPermissionGroupDetails> findByGroupClass(String groupClass);

}
