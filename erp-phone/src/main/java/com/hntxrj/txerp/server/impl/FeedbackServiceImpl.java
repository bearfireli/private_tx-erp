package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.FeedbackDao;
import com.hntxrj.txerp.server.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 功能:
 *
 * @Auther 李帅
 * @Data 2017-09-07.下午 3:22
 */
@Service
@Scope("prototype")
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao feedbackDao;

    @Autowired
    public FeedbackServiceImpl(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }


    /**
     *  反馈
     * @param compid  企业ID
     * @param empname  用户名
     * @param title  标题
     * @param content  内容
     * @return
     */
    @Override
    public JSONArray sendFeedback(String compid, String empname, String title, String content){
        return feedbackDao.sendFeedback(compid, empname, title, content);
    }
}
