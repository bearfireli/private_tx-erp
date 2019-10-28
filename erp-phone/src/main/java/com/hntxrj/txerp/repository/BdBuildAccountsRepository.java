package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.BdBuildAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BdBuildAccountsRepository extends JpaRepository<BdBuildAccounts, String> {
}
