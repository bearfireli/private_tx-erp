package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserLoginRepository
        extends JpaRepository<UserLogin, String> {


    List<UserLogin> findAllByUserId(Integer userId);

    UserLogin findByUserToken(String userToken);

    @Transactional
    void deleteAllByUserId(Integer userId);


}
