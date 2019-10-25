package com.hntxrj.dao;

import com.hntxrj.entity.UserEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/1/16
 */
@Repository
public interface UserEmployeeDaoJpa extends JpaRepository<UserEmployee, String> {
    UserEmployee findByLoginName(String loginName);
}
