package com.hntxrj.dao;

import com.hntxrj.entity.DeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/2/26
 */
@Repository
public interface DeProductDao extends JpaRepository<DeProduct, Integer> {
}
