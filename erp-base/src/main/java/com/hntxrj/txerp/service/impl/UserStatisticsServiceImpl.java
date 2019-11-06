package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.UserStatistic;
import com.hntxrj.txerp.mapper.UserStatisticsMapper;
import com.hntxrj.txerp.service.UserStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final UserStatisticsMapper userStatisticsMapper;


    @Autowired
    public UserStatisticsServiceImpl(UserStatisticsMapper userStatisticsMapper) {
        this.userStatisticsMapper = userStatisticsMapper;
    }

    @Override
    public List<UserStatistic> userCount(List<String> list) {
        List<UserStatistic> userStatisticList = new ArrayList<>();
        for (String statDate : list) {
            UserStatistic userStatistic = userStatisticsMapper.userCount(statDate);
            if (userStatistic == null) {
                userStatisticList.add(returnUserStatistic(statDate));
            } else {
                userStatistic.setStatDate(statDate.substring(5));
                userStatisticList.add(userStatistic);
            }
        }
        return userStatisticList;
    }

    @Override
    public List<UserStatistic> loginCount(Integer compid,List<String> list, int appCode) {

        List<UserStatistic> userStatisticList = new ArrayList<>();
        for (String statDate : list) {
            UserStatistic userStatistic = userStatisticsMapper.loginCount(compid,statDate, appCode);
            if (userStatistic == null) {

                userStatisticList.add(returnUserStatistic(statDate));
            } else {
                userStatistic.setStatDate(statDate.substring(5));
                userStatisticList.add(userStatistic);
            }
        }
        return userStatisticList;
    }

    /**
     * 查询某一天某个功能的点击总数
     */
    @Override
    public List<UserStatistic> functionCount(List<String> timeList, String methodName, Integer appCode, Integer compid) {
        List<UserStatistic> userStatisticList = new ArrayList<>();
        for (String statDate : timeList) {
            UserStatistic userStatistic = userStatisticsMapper.functionCount(statDate, methodName, appCode, compid);
            if (userStatistic == null) {
                userStatisticList.add(returnUserStatistic(statDate));
            } else {
                userStatistic.setStatDate(statDate.substring(5));
                userStatisticList.add(userStatistic);
            }

        }

        return userStatisticList;
    }

    @Override
    public List<UserStatistic> functionRank(Integer compid, String date, Integer appCode) {

        return userStatisticsMapper.functionRank(compid, date, appCode);
    }

    @Override
    public List<Enterprise> getEnterprise() {
        return userStatisticsMapper.getEnterprise();
    }

    /*
    * 用户每次登录向user_statistics表中插入一条数据
    * */
    @Override
    public void userLogin(Integer compid, Integer uid, Integer appCode, String statDate) {
        UserStatistic loginUser1 = userStatisticsMapper.loginUser(compid, uid, appCode, statDate);

        if (loginUser1 == null) {
            //说明用户今天第一次登录
            UserStatistic loginUser = new UserStatistic();
            loginUser.setAppCode(appCode);
            if (appCode == 1) {
                loginUser.setAppName("商砼ERP");
            }
            if (appCode == 2) {
                loginUser.setAppName("司机ERP");
            }
            if (appCode == 3) {
                loginUser.setAppName("施工方ERP");
            }
            loginUser.setCompid(compid);
            loginUser.setStatDate(statDate);
            loginUser.setTypeCode(2);
            loginUser.setTypeName("用户登录");
            loginUser.setUid(uid);
            loginUser.setValue(1);
            userStatisticsMapper.addUserStatistic(loginUser);
        } else {
            //用户今天不是第一次登录
            loginUser1.setValue(loginUser1.getValue() + 1);
            userStatisticsMapper.updateUserStatistic(loginUser1);
        }

    }


    /**
     * 此方法每次用户点击某一个功能时调用，统计表中添加记录
     */
    @Transactional
    @Override
    public void functionClick(String methodName, String functionName, String statData, Integer appCode, Integer compid) {
        UserStatistic functionClick = userStatisticsMapper.functionClick(methodName, statData, appCode, compid);
        if (functionClick == null) {
            //说明此功能今天第一次点击
            UserStatistic userStatistic = new UserStatistic();
            userStatistic.setTypeName("功能点击");
            userStatistic.setTypeCode(3);
            userStatistic.setStatDate(statData);
            userStatistic.setMethodName(methodName);
            userStatistic.setFunctionName(functionName);
            userStatistic.setValue(1);
            userStatistic.setCompid(compid);
            userStatistic.setAppCode(appCode);
            if (appCode == 1) {
                userStatistic.setAppName("商砼ERP");
            } else if (appCode == 2) {
                userStatistic.setAppName("司机ERP");
            } else if (appCode == 3) {
                userStatistic.setAppName("施工方ERP");
            }

            userStatisticsMapper.addUserStatistic(userStatistic);
        } else {
            //说明此功能今天不是第一次登录
            functionClick.setValue(functionClick.getValue() + 1);
            userStatisticsMapper.updateUserStatistic(functionClick);
        }


    }
/*
* 查询用户个数
* */
    @Override
    public Integer selectUser() {
        return userStatisticsMapper.selectUser();
    }


    /*
    * 每日凌晨向统计表中插入今日用户数量
    * */
    @Override
    public void addUserStatistics(UserStatistic userStatistic) {
        UserStatistic userCount = userStatisticsMapper.userCount(userStatistic.getStatDate());
        if (userCount == null) {
            userStatisticsMapper.addUserStatistic(userStatistic);
        } else {
            userCount.setValue(userStatistic.getValue());
            userStatisticsMapper.updateUserStatistic(userCount);
        }
    }

/*
* 此方法用户把1900-01-01时间格式转换成01-01格式
* */
    private UserStatistic returnUserStatistic(String statDate) {
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setValue(0);
        userStatistic.setStatDate(statDate.substring(5));
        return userStatistic;
    }

}