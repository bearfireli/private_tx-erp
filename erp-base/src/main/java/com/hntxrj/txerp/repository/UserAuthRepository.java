package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    List<UserAuth> findAllByUser(User uid);


}
