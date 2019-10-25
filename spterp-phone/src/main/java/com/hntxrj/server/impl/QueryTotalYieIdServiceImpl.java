package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.QueryTotalYieldDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.QueryTotalYieIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class QueryTotalYieIdServiceImpl implements QueryTotalYieIdService {
    private final QueryTotalYieldDao queryTotalYieldDao;

@Autowired
    public QueryTotalYieIdServiceImpl(QueryTotalYieldDao queryTotalYieldDao) {
        this.queryTotalYieldDao = queryTotalYieldDao;
    }

    /**
     * 跟据砼标号统计砼产量
     *
     * @param taskStatus       任务单状态
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param compid           生产站点
     * @param stirId           搅拌楼编号
     * @param opId             调度员
     * @param eppname          工程名
     * @param taskId           任务单ID
     * @param builderShortName 施工单位名简称
     * @param reportCode       报告编号
     * @param placing          浇灌部位
     * @param placeStyle       浇灌方式
     * @param invoiceType      小票类型
     * @param pageBean         分页
     * @return                 json
     */
    @Override
    public JSONArray selectQueryTotalYieId(String taskStatus, String beginTime, String endTime, String compid, String
            stirId, String opId, String eppname, String taskId, String builderShortName, String reportCode, String
            placing, String placeStyle, String invoiceType, PageBean pageBean, String stgid, String opid) {
        return queryTotalYieldDao.getQueryTotalYieId(taskStatus,beginTime,endTime,compid,stirId,opId,eppname,taskId,
                builderShortName,reportCode, placing,placeStyle,invoiceType,pageBean,stgid,opid);
    }
}

