package com.hntxrj.dao;


import com.alibaba.fastjson.JSONArray;

/**
 * 功能:
 *
 * @Auther 李帅
 * @Data 2017-09-07.下午 3:11
 */
public interface FeedbackDao {


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
