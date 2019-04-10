package com.hntxrj.txerp.conf;


import com.hntxrj.txerp.mapper.UserLoginMapper;
import com.hntxrj.txerp.service.PVStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final UserLoginMapper userLoginMapper;
    private final PVStatisticsService pvStatisticsService;

    @Autowired
    public ScheduledTasks(UserLoginMapper userLoginMapper, PVStatisticsService pvStatisticsService) {
        this.userLoginMapper = userLoginMapper;
        this.pvStatisticsService = pvStatisticsService;
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


}
