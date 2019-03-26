package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.base.Enterprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    List<Enterprise> findAllByEpNameLike(String epName);


}
