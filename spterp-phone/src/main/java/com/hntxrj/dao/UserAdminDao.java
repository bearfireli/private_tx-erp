package com.hntxrj.dao;

import com.hntxrj.entity.UserAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/1/22
 */
@Repository
public interface UserAdminDao extends JpaRepository<UserAdmin, String>{

    /**
     * 通过企业查询
     * @param compid        企业id
     * @param pageable      分页
     * @return              查询分页对象
     */
    Page<UserAdmin> findByAdminCompid(String compid, Pageable pageable);

}
