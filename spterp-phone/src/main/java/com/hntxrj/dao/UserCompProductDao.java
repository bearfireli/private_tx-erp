package com.hntxrj.dao;

import com.hntxrj.entity.UserComp;
import com.hntxrj.entity.UserCompProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/2/26
 */
@Repository
public interface UserCompProductDao extends JpaRepository<UserCompProduct, Integer> {

    /**
     * 通过企业查询企业产品
     * @param comp      企业
     * @param pageable  分页
     * @return
     */
    Page<UserCompProduct> findByComp(UserComp comp, Pageable pageable);
    UserCompProduct findByProductIdAndComp(Integer productId, UserComp comp);



}
