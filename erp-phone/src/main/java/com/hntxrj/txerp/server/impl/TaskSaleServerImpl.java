package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.TaskSaleDao;
import com.hntxrj.txerp.server.TaskSaleServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class TaskSaleServerImpl implements TaskSaleServer {
    private final TaskSaleDao taskSaleDao;

    @Autowired
    public TaskSaleServerImpl(TaskSaleDao taskSaleDao) {
        this.taskSaleDao = taskSaleDao;
    }

    /**
     * 泵车工作量统计
     *
     * @param mark         1  泵车工作量汇总统计 2 泵车工作量统计（按司机）3泵车工作量明细汇总 4泵车泵工工作量明细汇总 5泵车工作量-工程方量统计
     * @param compid       企业
     * @param opid         用户
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param vehicleid    车号
     * @param personalname 司机
     * @param eppname      工程名称
     * @param stirid       楼号
     * @return
     */
    @Override
    public JSONArray sp_V_VM_PumpTruckPersonalWorksrq(String mark, String compid, String opid, String beginTime, String endTime, Integer currPage, Integer pageSize, String vehicleid, String personalname, String eppname, String stirid) {
        return taskSaleDao.sp_V_VM_PumpTruckPersonalWorksrq(mark, compid, opid, beginTime, endTime, currPage, pageSize, vehicleid, personalname, eppname, stirid);
    }
}
