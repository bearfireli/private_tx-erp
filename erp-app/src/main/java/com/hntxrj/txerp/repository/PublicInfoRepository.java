package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.PublicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (PublicInfo)表数据库访问层
 *
 * @author lhr
 * @since 2018-08-14 13:19:10
 */
@Repository
public interface PublicInfoRepository extends JpaRepository<PublicInfo, Integer> {

}