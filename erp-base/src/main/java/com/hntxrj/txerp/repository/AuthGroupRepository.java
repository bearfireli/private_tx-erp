package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.base.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {

}
