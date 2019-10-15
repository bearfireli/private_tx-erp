package com.hntxrj.txerp.conf;


import com.hntxrj.txerp.entity.base.UserStatistic;
import com.hntxrj.txerp.mapper.UserLoginMapper;
import com.hntxrj.txerp.service.PVStatisticsService;
import com.hntxrj.txerp.service.UserStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
@EnableScheduling
public class ScheduledTasks {

    private final UserLoginMapper userLoginMapper;
    private final PVStatisticsService pvStatisticsService;
    private final UserStatisticsService userStatisticsService;

    @Autowired
    public ScheduledTasks(UserLoginMapper userLoginMapper, PVStatisticsService pvStatisticsService,UserStatisticsService userStatisticsService) {
        this.userLoginMapper = userLoginMapper;
        this.pvStatisticsService = pvStatisticsService;
        this.userStatisticsService = userStatisticsService;
    }

    @Scheduled(fixedDelay = 500000)
    public void cleanToken() {
        userLoginMapper.delExpiredToken();
        log.info("【定时任务】taskName={}", "cleanToken");
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void savePVStatistics() {
        pvStatisticsService.savePVData();
        log.info("【定时任务】taskName={}", "savePVStatistics");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void saveUserCount() {
        //查询user表中有多少用户插入到用户统计表中。
        String time = timeFormat(new Date());

        Integer userNumber = userStatisticsService.selectUser();
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setTypeName("用户统计");
        userStatistic.setTypeCode(1);
        userStatistic.setValue(userNumber);
        userStatistic.setStatDate(time);
        userStatisticsService.addUserStatistics(userStatistic);
    }


    private String timeFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = format.format(date);
        return timeStr;
    }


}
