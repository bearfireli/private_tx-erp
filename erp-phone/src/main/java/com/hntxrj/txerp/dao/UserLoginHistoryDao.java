package com.hntxrj.txerp.dao;

import com.hntxrj.txerp.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/1/16
 */
@Repository
public interface UserLoginHistoryDao extends JpaRepository<UserLoginHistory, Integer> {

    UserLoginHistory findByToken(String token);

}
