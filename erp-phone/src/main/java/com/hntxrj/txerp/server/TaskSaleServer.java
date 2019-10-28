package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;

public interface TaskSaleServer {
    /**
     *泵车工作量统计
     * @param mark 1  泵车工作量汇总统计 2 泵车工作量统计（按司机）3泵车工作量明细汇总 4泵车泵工工作量明细汇总 5泵车工作量-工程方量统计
     * @param compid 企业
     * @param opid 用户
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param vehicleid 车号
     * @param personalname 司机
      * @param eppname 工程名称
     * @param stirid 楼号
     * @return
     */
    JSONArray sp_V_VM_PumpTruckPersonalWorksrq(String mark, String compid, String opid, String beginTime, String endTime, Integer currPage, Integer pageSize, String vehicleid, String personalname, String eppname, String stirid);
}
