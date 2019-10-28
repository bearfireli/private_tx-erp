package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.UserBindDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBindDriverRepository extends JpaRepository<UserBindDriver, Integer> {
    UserBindDriver findUserBindDriverByUidAndCompid(Integer paramInteger, String paramString);

    UserBindDriver findUserBindDriverByDriverCodeAndCompid(String paramString1, String paramString2);
}
