package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (UserAccount)表数据库访问层
 *
 * @author lhr
 * @since 2018-09-18 15:22:47
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByAcTypeAndAcValue(String acType, String acValue);
}