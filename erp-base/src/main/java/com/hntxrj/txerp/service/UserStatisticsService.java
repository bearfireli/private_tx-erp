package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.UserStatistic;

import java.util.List;

public interface UserStatisticsService {

    List<UserStatistic> userCount(List<String> list);

    List<UserStatistic>  loginCount(Integer compid,List<String> list,int appCode);

    void userLogin(Integer compid, Integer uid, Integer appCode,String time);

    void functionClick(String methodName,String functionName, String time,Integer appCode,Integer compid);

    Integer selectUser();

    void addUserStatistics(UserStatistic userStatistic);

    List<UserStatistic> functionCount( List<String> dateList, String methodName, Integer appCode, Integer compid);

    List<UserStatistic> functionRank(Integer compid, String date, Integer appCode);

    List<Enterprise> getEnterprise();
}