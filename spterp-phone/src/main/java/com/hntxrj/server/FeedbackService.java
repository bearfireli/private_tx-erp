package com.hntxrj.server;

import com.alibaba.fastjson.JSONArray;

/**
 * 功能:  反馈模块
 *
 * @Auther 李帅
 * @Data 2017-09-07.下午 3:22
 */
public interface FeedbackService {
    /**
     *  反馈
     * @param compid  企业ID
     * @param empname  用户名
     * @param title  标题
     * @param content  内容
     * @return
     */
    JSONArray sendFeedback(String compid, String empname, String title, String content);
}
