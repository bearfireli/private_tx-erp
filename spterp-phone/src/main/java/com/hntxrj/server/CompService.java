package com.hntxrj.server;

import com.hntxrj.entity.UserComp;

import java.sql.SQLException;

/**
 * 企业服务
 * @author lhr
 * @create 2018/2/3
 */
public interface CompService {

    /**
     * 获取企业列表
     * @param compName  企业名 like
     * @param page      页数
     * @return          返回分页对象
     */
    org.springframework.data.domain.Page<UserComp> getUserCompList(String compName, Integer page);


    /**
     * 通过企业id 获取企业对象
     * @param compid    企业ID
     * @return          企业对象
     */
    UserComp getByCompId(String compid) throws SQLException;


    /**
     * 添加企业
     * @param userComp  企业对象
     */
    UserComp addComp(UserComp userComp);

    /**
     * 更新企业
     * @param userComp  企业对象
     */
    void updateComp(UserComp userComp);




}
