package com.hntxrj.txerp.repository;


import com.hntxrj.txerp.entity.sell.ContractDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDetailsRepository extends JpaRepository<ContractDetails, String> {

}
