package com.hntxrj.repository;

import com.hntxrj.entity.ContractPriceMarkup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPriceMarkupRepository extends JpaRepository<ContractPriceMarkup, String> {
}
