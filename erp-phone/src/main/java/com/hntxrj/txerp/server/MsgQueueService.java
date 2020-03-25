package com.hntxrj.txerp.server;

import com.hntxrj.txerp.entity.MsgQueue;

import java.util.List;

public interface MsgQueueService {

    /**
     * 读取消息队列表中未处理的消息
     *
     * @return 未处理的消息集合
     */
    List<MsgQueue> readNotPushMsg();


    /**
     * 推送
     *
     * @param msgQueues 推送的内容
     */
    void push(List<MsgQueue> msgQueues);

}
