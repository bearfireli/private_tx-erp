package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.sell.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuilderRepository extends JpaRepository<Builder, Integer> {

}
