package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.sell.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman, Integer> {
}
