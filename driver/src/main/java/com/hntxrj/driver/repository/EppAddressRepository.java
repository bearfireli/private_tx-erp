package com.hntxrj.driver.repository;

import com.hntxrj.driver.entity.EppAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EppAddressRepository extends JpaRepository<EppAddress, Integer> {


}
