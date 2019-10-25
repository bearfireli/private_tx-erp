package com.hntxrj.dao;

import com.hntxrj.entity.DeMenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhr
 * @create 2018/1/16
 */
@Repository
public interface DeMenuInfoDao extends JpaRepository<DeMenuInfo, Integer> {

    /**
     * 通过url查询
     * @param menuUrl
     * @return
     */
    DeMenuInfo findByMenuUrl(String menuUrl);


}
