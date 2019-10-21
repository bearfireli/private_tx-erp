package com.hntxrj.txerp.mapper;


import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.UserStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface UserStatisticsMapper {
    UserStatistic userCount(@Param(value = "statDate") String statDate);
    UserStatistic loginCount(Integer compid,String statDate,int appCode);

    UserStatistic loginUser(Integer compid, Integer uid, Integer appCode,String statDate);

    void updateUserStatistic(UserStatistic userStatistic);

    void addUserStatistic(UserStatistic userStatistic);

    UserStatistic functionClick(@Param(value = "methodName")String methodName, @Param(value = "statDate")String statData,Integer appCode,Integer compid);

    Integer selectUser();

    UserStatistic functionCount(String statDate, String methodName, Integer appCode, Integer compid);

    List<UserStatistic> functionRank(Integer compid, String statDate, Integer appCode);

    List<Enterprise> getEnterprise();
}