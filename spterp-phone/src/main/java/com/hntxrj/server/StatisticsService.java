package com.hntxrj.server;

import com.alibaba.fastjson.JSONArray;


public interface StatisticsService {

    /**
     * 手机端统计接口
     *
     * @param compid    企业id
     * @param type      统计类型 1.任务单完成数量统计，2.预计产量统计（只能统计以后的数据）,3.出入库(入库，消耗)
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    JSONArray phoneStatistics(String compid, int type, String beginTime, String endTime);

}
