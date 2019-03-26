package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.system.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, String> {

    void deleteAllByEnterprise(Integer enterprise);

    void deleteAllByEnterpriseAndType(Integer enterprise, Integer type);

}
