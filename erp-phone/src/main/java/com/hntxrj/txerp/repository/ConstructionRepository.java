package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.BdInvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionRepository extends JpaRepository<BdInvitationCode, String> {
}
