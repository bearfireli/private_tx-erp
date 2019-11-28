package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;

import java.util.List;

public interface StockInServer {
    /**
     * 材料过磅统计
     * @param mark
     * @param sup_linkMan  联系人
     * @param mu 母公司
     * @param empName  过磅员
     * @param vehicleID 车号
     * @param for_name 运输商
     * @param matNS 入库库位
     * @param matNS 材料名称
     * @param sup_name 供货商
     * @param stI_status 记录状态
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param compid 当前登录用户的企业
     * @param opid  登录用户
     * @param currPage 当前页
     * @param pageSize 页长度
     * @return json
     */
    JSONArray sp_V_MP_StockInQuerysql(String mark, String sup_linkMan, String mu, String empName, String vehicleID, String for_name, String sto_name, String matNS, String sup_name, Boolean stI_status, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize);

    /**
     * srqq 材料过磅  下拉
     * @param id  1 联系人下拉 MP_Supplier   2  过磅员查询 User_employee 3 运输商查询      这个表  MP_Forwarder 4 入库库位  MP_Stock 5 材料名称    V_MatCodeToName 6 供货商名称 MP_Supplier
     * @param sqlwhere 查询条件
     * @param compid
     *@param opid @return json
     */
    JSONArray sp_query_downsrq(String id, String sqlwhere, String compid, String opid,Integer PageSize, Integer currPage);


    /**
     *  srqq  原材料过磅查询详情
     * @param mark  1 list表     2 详情
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param  compid    当前登录用户的企业
     * @param opid  登录用户
     * @param currPage
     * @param pageSize
     * @param sup_name   供货商
     * @param for_name  运输商
     * @param empName  过磅员
     * @param sto_name  入库库位
     * @param matNS   材料名称
     * @param vehicleID   车号
     * @param saletype  业务类别
     * @param stI_status 记录状态
     * @return  json
     */
    JSONArray sp_V_MP_StockInQuerylistsrq(String mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String sup_name, String for_name, String empName, String sto_name, String matNS, String vehicleID, Integer saletype, Integer stI_status,String sticode);


    /**
     * 原材料统计汇总
     * @param mark 1原材林明细汇总  2原材林汇总统计  3 材料入库汇总   4 实时库存 查询
     * @param beginTime
     * @param endTime
     * @param compid
     * @param opid
     * @param currPage
     * @param pageSize
     * @param matname   材料名称
     * @param sup_name   供货商名称
     * @param vehicleID  车号
     * @return json
     */
    JSONArray sp_V_MP_StockInQueryManNsrq(String mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String matname, String sup_name, String vehicleID);


    /**
     *srqq 搅拌车过磅查询
     * @param mark 1列表
     * @param beginTime  开始时间
     * @param endTime 结束时间
     * @param compid  企业
     * @param opid 用户
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param empname   过磅员
     * @param weightType  过磅类别
     * @param vehicleID 车号
     * @param eppname 业务员
     * @return
     */
    JSONArray sp_Query_VM_VehicleWeightListsrq(Integer mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String empname, Integer weightType, String vehicleID, String eppname);


    /**
     /*原材料过磅统计  材料名称
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightMatNameVO> getWeightByMat(String empName, String compid, String vehicleId,
                                           String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);
    /**
     /*原材料过磅统计  车辆代号
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightVechicIdVO> getWeightByVechicId(String empName, String compid, String vehicleId,
                                                 String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。供应商名
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightSupNameVO> getWeightByStoName(String empName, String compid, String vehicleId,
                                               String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。入库库位
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightStorageVO> getWeightBySupName(String empName, String compid, String vehicleId,
                                               String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);
    /**
     /*原材料过磅统计。过磅员
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightEmpNameVO> getWeightByEmpName(String empName, String compid, String vehicleId,
                                               String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);
    /**
     /*原材料过磅统计。综合信息
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightSynthesizeVO> getSynthesizeByMat(String empName, String compid, String vehicleId,
                                                  String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);
    /**
     /*原材料过磅统计合计入库量
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    PageVO<WeightCloseVO> getWeightClose(String empName, String compid, String vehicleId,
                                         String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);


    List<WeightMatParentNameVO> getWeightByMatParent(String compid,String beginTime, String endTime);

    List<SupNamePieChartVO> getPieChartBySupName(String compid, String empName, String vehicleId,
                                                 String stoName, String supName,String beginTime,String endTime);


}
