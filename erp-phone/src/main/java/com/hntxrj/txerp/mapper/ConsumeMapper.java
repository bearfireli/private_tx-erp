package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.ProductConsume;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumeMapper {
/**
     * 任务单消耗列表
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 任务单消耗列表
     */
    List<TaskConsumeVO> getTaskConsumeList(String compid, String beginTime, String endTime,
                                           String vehicleId, String stgId , String taskId, String stirId,
                                           Integer page, Integer pageSize);



    /**
     * 每盘配料明细
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 每盘配料明细
     */
    List<PlatelngredientsVO> getFormulaDetails(String compid, String beginTime, String endTime,
                                               String vehicleId, String stgId , String taskId, String stirId,
                                               Integer page, Integer pageSize);

    /**
     * 标号消耗汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 标号消耗汇总
     */
    List<ConsumeptionTotalVO> getConsumptionTotal(String compid, String beginTime, String endTime,
                                                  String vehicleId, String stgId , String taskId, String stirId,
                                                  Integer page, Integer pageSize);
    /**
     * 原材料统计汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @return 原材料统计汇总
     */
    List<RawCollectVO> getMatTotal(String compid, String beginTime, String endTime,
                                   String stgId, String vehicleId, String taskId, String stirId);
    /**
     * 生产消耗汇总合计方量
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间MP_StkPrice
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<ConsumePtionCloseVO>getConsumeClose(String compid, String beginTime, String endTime,
                                             String vehicleId, String stgId , String taskId, String stirId,
                                             Integer page, Integer pageSize);

    List<StockVO> getProductDatail(String compid,Integer stirid);

    List<ProductConsume> getErroPan(String compid, String beginTime, String endTime);

    /**
     * 标号消耗汇总柱状图
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @return 标号消耗汇总
     */
    List<ConsumptionHistogram> getConsumptionHistogram(String compid, String beginTime, String endTime, String vehicleId, String stgId, String taskId, String stirId);
}

