package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.EppAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EppAddressRepository extends JpaRepository<EppAddress, Integer> {


}
