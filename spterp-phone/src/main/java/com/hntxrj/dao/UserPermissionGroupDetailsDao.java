package com.hntxrj.dao;

import com.hntxrj.entity.UserPermissionGroupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhr
 * @create 2018/1/16
 */
@Repository
public interface UserPermissionGroupDetailsDao
        extends JpaRepository<UserPermissionGroupDetails, Integer> {

    List<UserPermissionGroupDetails> findByGroupClassAndMenuIdAndCompid(String groupClass,
                                                                  List<Integer> menuId,
                                                                  String compid);


    List<UserPermissionGroupDetails> findByGroupClassAndCompid(String groupClass,
                                                                        String compid);




    List<UserPermissionGroupDetails> findByGroupClass(String groupClass);

}
