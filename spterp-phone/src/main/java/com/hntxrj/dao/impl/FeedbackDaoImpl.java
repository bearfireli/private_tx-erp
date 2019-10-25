package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.FeedbackDao;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 反馈模块
 *
 * @Auther 李帅
 * @Data 2017-09-07.下午 3:09
 */
@Component
@Scope("prototype")
public class FeedbackDaoImpl implements FeedbackDao {
    @Autowired
    private Procedure procedure;

    /**
     * 反馈
     *
     * @param compid  企业ID
     * @param empname 用户名
     * @param title   标题
     * @param content 内容
     * @return
     */
    @Override
    public JSONArray sendFeedback(String compid, String empname, String title, String content) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), title));
        params.add(new Param(3, ParamType.INPARAM.getType(), content));
        params.add(new Param(4, ParamType.INPARAM.getType(), empname));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_insert_Feedback", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }
}
