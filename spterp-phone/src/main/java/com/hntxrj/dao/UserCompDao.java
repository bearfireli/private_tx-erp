package com.hntxrj.dao;

import com.hntxrj.entity.UserComp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/1/30
 */
@Repository
public interface UserCompDao extends JpaRepository<UserComp, String>, org.springframework.data.repository.Repository<UserComp,String> {


    Page<UserComp> findByLongnameLike(String longName, Pageable pageable);


    /**
     *  根据企业代号查询企业信息
     * @param compid 企业信息
     * @return {@link UserComp}
     */
    UserComp findUserCompByCompid(String compid);

}
