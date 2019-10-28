package com.hntxrj.txerp.push;

import com.hntxrj.txerp.entity.PushMessage;

import java.util.List;

public class PushTask {

    // 推送人信息
    private List pushReceiver;

    void init() {
        // 读取推送人
        initPushReceiver();
        // 启动被动定时任务
        startPassivenessTask();
        // 启动推送定时任务
        startPushTask();
    }

    private void initPushReceiver() {
    }

    private void startPassivenessTask() {
    }

    private void startPushTask() {
    }


    /**
     * 主动推送创建一个推送消息
     *
     * @param message 推送消息
     */
    public static void createPushMessage(PushMessage message) {

    }
}
