package com.hntxrj.txerp.conf;


import com.hntxrj.txerp.mapper.UserLoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final UserLoginMapper userLoginMapper;

    @Autowired
    public ScheduledTasks(UserLoginMapper userLoginMapper) {
        this.userLoginMapper = userLoginMapper;
    }

    @Scheduled(fixedDelay = 500000)
    public void cleanToken() {
        userLoginMapper.delExpiredToken();
        log.info("【定时任务】taskName={}", "cleanToken");
    }


}
