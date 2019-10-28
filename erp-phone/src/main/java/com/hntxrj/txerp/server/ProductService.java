package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;

/**
 * 功能:  生产模块服务层
 *
 * @Auther 李帅
 * @Data 2017-09-04.下午 6:42
 */
public interface ProductService {

    /**
     *   生产查询统计
     * @param mark -- 1 查询统计
     * @param compid  企业ID
     * @param begintime  起始时间
     * @param endtime 结束时间
     * @param taskId   任务单ID
     * @param taskStatus  任务单状态
     * @param pageBean  分页
     * @return
     */
    JSONArray productTotal(Integer mark, String compid, String begintime, String endtime, String taskId,
                           String taskStatus, PageBean pageBean, String opid);


    /**
     *   销售方量统计
     * @param mark  标记
     * @param beginTime  起始时间
     * @param endTime   结束时间
     * @param builderName   施工单位名称
     * @param eppName   工程名称
     * @param pageBean  分页
     * @return
     */
    JSONArray builderSalaTotal(Integer mark,String beginTime,String endTime,String builderName,
                                      String eppName,String compid,PageBean pageBean,String opid);


    /**
     *
     *   小票签收查询
     * @param mark 标记 1加载列表  2详情
     * @param compid 企业ID
     * @param id   id
     * @param begintime 起始时间
     * @param endtime   结束时间
     * @param vehicleID  车号
     * @param taskId    任务单号
     * @param InvoiceType   小票类型
     * @param placeStyle    浇筑方式
     * @param placing   浇筑部位
     * @param builderName   施工单位
     * @param stgId  搅拌楼
     * @param eppName   工程名称
     * @param pageBean 分页
     * @return
     */
    JSONArray deliveryTicketTotal(Integer mark, String compid, Integer id, String begintime, String endtime, String vehicleID,
                                  String taskId, String InvoiceType, String placeStyle, String placing, String builderName,
                                  String stgId, String eppName, PageBean pageBean, String opid);


    /**
     *   调度派车
     * @param mark 1加载列表
     * @param compid 企业ID
     * @param begintime  起始时间
     * @param endtime  结束时间
     * @param taskId    任务单号
     * @param taskStatus    任务状态
     * @param placing   浇筑部位
     * @param builderName   施工单位
     * @param eppName   工程名称
     * @param pageBean  分页
     * @return
     */
    JSONArray vehicleCarTotal(Integer mark,String compid,String begintime,String endtime,String taskId,Integer taskStatus,
                              String placing,String builderName,String eppName,PageBean pageBean);


    /**
     *   调度管理修改
     *
     *      version 1.1
     *
     * @param mark 标记  1 加载列表 2 详情
     * @param compid 企业ID
     * @param taskID  任务单号
     * @param pageBean  分页
     * @return
     */
    JSONArray vehicleCarTotal_Two(Integer mark, String compid, String taskID, PageBean pageBean, String opid);
}
