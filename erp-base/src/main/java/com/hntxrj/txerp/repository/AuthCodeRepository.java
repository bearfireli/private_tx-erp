package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, String> {

}
