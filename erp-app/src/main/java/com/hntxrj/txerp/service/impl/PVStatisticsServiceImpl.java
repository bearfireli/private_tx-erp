package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.util.RedisUtil;
import com.hntxrj.txerp.entity.base.Statistics;
import com.hntxrj.txerp.mapper.StatisticsMapper;
import com.hntxrj.txerp.service.PVStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PVStatisticsServiceImpl implements PVStatisticsService {

    private final RedisUtil redisUtil;
    private final StatisticsMapper statisticsMapper;

    @Autowired
    public PVStatisticsServiceImpl(RedisUtil redisUtil, StatisticsMapper statisticsMapper) {
        this.redisUtil = redisUtil;
        this.statisticsMapper = statisticsMapper;
    }

    @Override
    public void augmentPV(String name, Integer eid) {
        // 统计
        if (eid == null || eid == 0) {
            return;
        }
        String clickNumStr = redisUtil.stringRedisGetValue("clickNum");
        clickNumStr = (clickNumStr == null || "".equals(clickNumStr)) ? "{}" : clickNumStr;
        JSONObject clickNumJSON = JSONObject.parseObject(clickNumStr);
        JSONObject enterpriseClickNum = clickNumJSON.getJSONObject(eid.toString());
        enterpriseClickNum = enterpriseClickNum == null ? new JSONObject() : enterpriseClickNum;

        int oldNum = enterpriseClickNum.getInteger(name) == null ? 0 : enterpriseClickNum.getIntValue(name);

        enterpriseClickNum.put(name, oldNum + 1);
        clickNumJSON.put(eid.toString(), enterpriseClickNum);

        redisUtil.stringRedisSetKey("clickNum", clickNumJSON.toString());
    }

    @Override
    public void savePVData() {
        String clickNumStr = redisUtil.stringRedisGetValue("clickNum");
        clickNumStr = (clickNumStr == null || "".equals(clickNumStr)) ? "{}" : clickNumStr;
        JSONObject clickNumJSON = JSONObject.parseObject(clickNumStr);
//        List<Statistics> statisticsList = new ArrayList<>();

        Map<String, Object> innerMap = clickNumJSON.getInnerMap();
        for (Map.Entry<String, Object> entry : innerMap.entrySet()) {
            Statistics statistics = new Statistics();
            statistics.setEnterprise(Integer.valueOf(entry.getKey()));
            statistics.setStatisticsCode("pv_day_num");
            statistics.setStatisticsType("pv_day");
            statistics.setStatisticsVal(JSON.toJSONString(entry.getValue()));
            statisticsMapper.insertStatistics(statistics);
        }


    }
}
