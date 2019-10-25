package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.TotalAllDao;
import com.hntxrj.server.TotalAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 功能:
 *
 * @Auther 李帅
 * @Data 2017-08-28.下午 8:03
 */
@Service
@Scope("prototype")
public class TotalAllServiceImpl implements TotalAllService {

    @Autowired
    public void setTotalAllDao(TotalAllDao totalAllDao) {
        this.totalAllDao = totalAllDao;
    }


    private TotalAllDao totalAllDao;
    /**
     * 统计查询
     * @param mark 标记
     * @return
     */
    @Override
    public JSONArray totalAll(Integer mark, String compid) {
        return totalAllDao.totalAll(mark,compid);
    }
}
