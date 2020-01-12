package com.hntxrj.txerp.conf;


import com.hntxrj.txerp.entity.MsgQueue;
import com.hntxrj.txerp.server.MsgQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private final MsgQueueService msgQueueService;

    @Autowired
    public ScheduledTasks(MsgQueueService msgQueueService) {
        this.msgQueueService = msgQueueService;
    }

    @Scheduled(fixedRate = 3000)
    public void pushTask() {
        // 读取退出里的内容
        List<MsgQueue> msgQueueList = msgQueueService.readNotPushMsg();
        // 推送处理
        msgQueueService.push(msgQueueList);
    }


}
