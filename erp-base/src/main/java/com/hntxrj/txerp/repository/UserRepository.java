package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);
}
