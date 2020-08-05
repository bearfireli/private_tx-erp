package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.CompService;
import com.hntxrj.txerp.server.DriverService;
import com.hntxrj.txerp.vo.MessagePushVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    private final CompService compService;
    private final DriverService driverService;

    @Autowired
    public ScheduledTasks(CompService compService, DriverService driverService) {
        this.compService = compService;
        this.driverService = driverService;
    }


    @Scheduled(cron = "*/60 * * * * ?")
    public void waitCarsPush() throws ErpException {
        //获取所有的企业id
        List<String> compIds = compService.getAllCompId();
        MessagePushVO messagePushVO = new MessagePushVO();

        //对每个企业等待生产的车辆进行消息推送
        for (String compid : compIds) {
            driverService.messagePush(compid, messagePushVO);
        }
    }

}
