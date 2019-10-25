package com.hntxrj.dao;

import com.hntxrj.entity.UserCompOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/2/26
 */
@Repository
public interface UserCompOrderDao extends JpaRepository<UserCompOrder, Integer> {

    Page<UserCompOrder> findByOrderComp(String compid, Pageable pageable);


}
