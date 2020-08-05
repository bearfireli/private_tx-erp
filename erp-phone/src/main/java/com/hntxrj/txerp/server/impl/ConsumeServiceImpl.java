package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.ConsumeDao;
import com.hntxrj.txerp.entity.ProductConsume;
import com.hntxrj.txerp.mapper.ConsumeMapper;
import com.hntxrj.txerp.server.ConsumeService;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Scope("prototype")
public class ConsumeServiceImpl implements ConsumeService {

    private final ConsumeDao consumeDao;
    private final ConsumeMapper consumeMapper;

    @Autowired
    public ConsumeServiceImpl(ConsumeDao consumeDao, ConsumeMapper consumeMapper) {
        this.consumeDao = consumeDao;
        this.consumeMapper = consumeMapper;
    }

    /**
     * 生产消耗汇总
     *
     * @param mark             状态
     * @param compid           企业
     * @param currPage         当企业
     * @param pageSize         页长度
     * @param vehicleID        车号
     * @param stirId           线号
     * @param taskId           任务单好
     * @param stgId            砼标号
     * @param empName          操作员
     * @param placing          浇筑部位
     * @param eppName          工程名称
     * @param builderShortName 施工单位简称
     * @param openTime         查询时间
     * @param overTime         救赎时间
     * @return json
     */
    @Override
    public JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage, Integer pageSize, String vehicleID,
                                                 String stirId, String taskId, String stgId, String empName,
                                                 String placing, String eppName, String builderShortName,
                                                 String openTime, String overTime, Integer mark,
                                                 Integer taskstatus, String opid) {
        return consumeDao.spQueryVQueryProduceConsume(compid, currPage, pageSize,
                vehicleID, stirId, taskId,
                stgId, empName, placing,
                eppName, builderShortName,
                openTime, overTime, mark,
                taskstatus, opid);
    }

    /**
     * 生产消耗汇总 list  一次执行3个
     *
     * @param compid           企业
     * @param currPage         当企业
     * @param pageSize         页长度
     * @param vehicleID        车号
     * @param stirId           线号
     * @param taskId           任务单好
     * @param stgId            砼标号
     * @param empName          操作员
     * @param placing          浇筑部位
     * @param eppName          工程名称
     * @param builderShortName 施工单位简称
     * @param openTime         查询时间
     * @param overTime         救赎时间
     * @return json
     */
    @Override
    public JSONArray spspQueryVAccountPTProduceConsumeList3(String compid, Integer currPage, Integer pageSize,
                                                            String vehicleID, Integer stirId, String taskId,
                                                            String stgId, String empName, String placing,
                                                            String eppName, String builderShortName, String openTime,
                                                            String overTime) {
        return consumeDao.spspQueryVAccountPTProduceConsumeList3(compid, currPage,
                pageSize, vehicleID, stirId, taskId, stgId, empName, placing, eppName,
                builderShortName, openTime, overTime);
    }


    /**
     * 司机排班
     *
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param personalCode 司机名称
     * @param vehicleID    车号
     * @return jsonarray
     */
    @Override
    public JSONArray spQueryVMWorkClassLog(String compid, Integer currPage, Integer pageSize, String personalCode,
                                           String vehicleID, String beginTime, String endTime, String opid,
                                           Integer workclass) {
        return consumeDao.spQueryVMWorkClassLog(compid, currPage, pageSize, personalCode, vehicleID, beginTime,
                endTime, opid, workclass);
    }

    /**
     * 查询企业名称
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     */
    @Override
    public JSONArray spQueryUsercomp(Integer currPage, Integer pageSize, String var1, String var2) {
        return consumeDao.spQueryUsercomp(currPage, pageSize, var1, var2);
    }


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
    @Override
    public PageVO<TaskConsumeVO> getTaskConsumeList(String compid, String beginTime, String endTime,
                                                    String vehicleId, String stgId, String taskId, String stirId,
                                                    Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<TaskConsumeVO> taskConsumeVOS = consumeMapper.getTaskConsumeList(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId, page, pageSize);
        PageInfo<TaskConsumeVO> pageInfo = new PageInfo<>(taskConsumeVOS);
        PageVO<TaskConsumeVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

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
    @Override
    public PageVO<PlatelngredientsVO> getFormulaDetails(String compid, String beginTime, String endTime,
                                                        String vehicleId, String stgId, String taskId, String stirId,
                                                        Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<PlatelngredientsVO> platelngredientsVOS = consumeMapper.getFormulaDetails(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId, page, pageSize);
        PageInfo<PlatelngredientsVO> pageInfo = new PageInfo<>(platelngredientsVOS);
        PageVO<PlatelngredientsVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 超差盘数列表
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
    @Override
    public List<PlatelngredientsVO> getErrorProductList(String compid, String beginTime, String endTime,
                                                        String vehicleId, String stgId, String taskId, String stirId,
                                                        Integer page, Integer pageSize) {

        return consumeMapper.getErrorProductList(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId, page, pageSize);

    }


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
    @Override
    public PageVO<ConsumeptionTotalVO> getConsumptionTotal(String compid, String beginTime, String endTime,
                                                           String vehicleId, String stgId, String taskId,
                                                           String stirId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ConsumeptionTotalVO> consumptionTotalVOS = consumeMapper.getConsumptionTotal(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId, page, pageSize);
        PageInfo<ConsumeptionTotalVO> pageInfo = new PageInfo<>(consumptionTotalVOS);
        PageVO<ConsumeptionTotalVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


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
    @Override
    public List<ConsumptionHistogram> getConsumptionHistogram(String compid, String beginTime, String endTime,
                                                              String vehicleId, String stgId, String taskId,
                                                              String stirId) {

        return consumeMapper.getConsumptionHistogram(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId);
    }

    /**
     * 原材料统计汇总(老版本)
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param stgId     线号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @Override
    public PageVO<RawCollectVO> getMatTotal(String compid, String beginTime, String endTime,
                                            String vehicleId, String taskId, String stirId,
                                            String stgId,
                                            Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<RawCollectVO> rawCollectVOS = consumeMapper.getMatTotal(compid, beginTime, endTime,
                stgId, vehicleId, taskId, stirId);
        PageInfo<RawCollectVO> pageInfo = new PageInfo<>(changeUnit(rawCollectVOS));
        PageVO<RawCollectVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 原材料统计汇总(新版本)
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param stgId     线号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @Override
    public PageVO<RawCollectVO> getProductionConsumptionDetails(String compid, String beginTime, String endTime,
                                                                String vehicleId, String taskId, String stirId,
                                                                String stgId,
                                                                Integer page, Integer pageSize) {
        List<RawCollectVO> rawCollectVOS = consumeMapper.getProductionConsumptionDetails(compid, beginTime, endTime,
                stgId, vehicleId, taskId, stirId);

        PageInfo<RawCollectVO> pageInfo = new PageInfo<>(changeUnit(rawCollectVOS));
        PageVO<RawCollectVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 生产消耗汇总合计方量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @Override
    public PageVO<ConsumePtionCloseVO> getConsumeClose(String compid, String beginTime, String endTime,
                                                       String vehicleId, String stgId, String taskId,
                                                       String stirId, Integer page, Integer pageSize) {
        List<ConsumePtionCloseVO> consumPtionCloseVOS = consumeMapper.getConsumeClose(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId, page, pageSize);
        PageInfo<ConsumePtionCloseVO> pageInfo = new PageInfo<>(consumPtionCloseVOS);
        PageVO<ConsumePtionCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 查询生产材料详细名称
     *
     * @param compid 企业id
     * @param stirId 线号
     * @return 查询材料名
     */
    @Override
    public PageVO<StockVO> getProductDatail(String compid, Integer stirId) {
        List<StockVO> consumptionTotalVOS = consumeMapper.getProductDatail(compid, stirId);
        PageInfo<StockVO> pageInfo = new PageInfo<>(consumptionTotalVOS);
        PageVO<StockVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public Integer getErrorPan(String compid, String beginTime, String endTime) {
        return consumeMapper.getErrorPan(compid, beginTime, endTime);
    }

    @Override
    public PageVO<VehicleConsumeVO> getVehicleConsumeList(String compid, String beginTime, String endTime, String stirId,
                                                          String stgId, String taskId, String vehicleId, Integer page,
                                                          Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<VehicleConsumeVO> vehicleConsumeVO = consumeMapper.getVehicleConsumeList(compid, beginTime, endTime,
                stirId, stgId, taskId, vehicleId);

        PageInfo<VehicleConsumeVO> pageInfo = new PageInfo<>(vehicleConsumeVO);
        PageVO<VehicleConsumeVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public Map<String, Integer> getVehicleConsumeSum(String compid, String beginTime, String endTime, String stirId,
                                                       String stgId, String taskId, String vehicleId) {
        Map<String, Integer> map = new HashMap<>();
        Integer count = consumeMapper.getVehicleConsumeSum(compid, beginTime, endTime, stirId, stgId, taskId, vehicleId);
        if (count == null) {
            count = 0;
        }
        map.put("count", count);
        return map;
    }

    @Override
    public RawCollectVO getVehicleConsumeDetail(String compid, String vehicleId, Integer stirId) {
        return consumeMapper.getVehicleConsumeDetail(compid, vehicleId, stirId);
    }

    private List<RawCollectVO> changeUnit(List<RawCollectVO> rawCollectVOS) {
        for (RawCollectVO raw : rawCollectVOS) {
            raw.setMatV1(String.format("%.2f", Double.parseDouble(raw.getMatV1()) / 1000));
            raw.setMatV2(String.format("%.2f", Double.parseDouble(raw.getMatV2()) / 1000));
            raw.setMatV3(String.format("%.2f", Double.parseDouble(raw.getMatV3()) / 1000));
            raw.setMatV4(String.format("%.2f", Double.parseDouble(raw.getMatV4()) / 1000));
            raw.setMatV5(String.format("%.2f", Double.parseDouble(raw.getMatV5()) / 1000));
            raw.setMatV6(String.format("%.2f", Double.parseDouble(raw.getMatV6()) / 1000));
            raw.setMatV7(String.format("%.2f", Double.parseDouble(raw.getMatV7()) / 1000));
            raw.setMatV8(String.format("%.2f", Double.parseDouble(raw.getMatV8()) / 1000));
            raw.setMatV9(String.format("%.2f", Double.parseDouble(raw.getMatV9()) / 1000));
            raw.setMatV10(String.format("%.2f", Double.parseDouble(raw.getMatV10()) / 1000));
            raw.setMatV11(String.format("%.2f", Double.parseDouble(raw.getMatV11()) / 1000));
            raw.setMatV12(String.format("%.2f", Double.parseDouble(raw.getMatV12()) / 1000));
            raw.setMatV13(String.format("%.2f", Double.parseDouble(raw.getMatV13()) / 1000));
            raw.setMatV14(String.format("%.2f", Double.parseDouble(raw.getMatV14()) / 1000));
            raw.setMatV15(String.format("%.2f", Double.parseDouble(raw.getMatV15()) / 1000));
            raw.setMatV16(String.format("%.2f", Double.parseDouble(raw.getMatV16()) / 1000));
            raw.setMatV17(String.format("%.2f", Double.parseDouble(raw.getMatV17()) / 1000));
            raw.setMatV18(String.format("%.2f", Double.parseDouble(raw.getMatV18()) / 1000));
            raw.setMatV19(String.format("%.2f", Double.parseDouble(raw.getMatV19()) / 1000));
            raw.setMatV20(String.format("%.2f", Double.parseDouble(raw.getMatV20()) / 1000));
            raw.setMatV21(String.format("%.2f", Double.parseDouble(raw.getMatV21()) / 1000));
            raw.setMatV22(String.format("%.2f", Double.parseDouble(raw.getMatV22()) / 1000));
            raw.setMatV23(String.format("%.2f", Double.parseDouble(raw.getMatV23()) / 1000));
            raw.setMatL1(String.format("%.2f", Double.parseDouble(raw.getMatL1()) / 1000));
            raw.setMatL2(String.format("%.2f", Double.parseDouble(raw.getMatL2()) / 1000));
            raw.setMatL3(String.format("%.2f", Double.parseDouble(raw.getMatL3()) / 1000));
            raw.setMatL4(String.format("%.2f", Double.parseDouble(raw.getMatL4()) / 1000));
            raw.setMatL5(String.format("%.2f", Double.parseDouble(raw.getMatL5()) / 1000));
            raw.setMatL6(String.format("%.2f", Double.parseDouble(raw.getMatL6()) / 1000));
            raw.setMatL7(String.format("%.2f", Double.parseDouble(raw.getMatL7()) / 1000));
            raw.setMatL8(String.format("%.2f", Double.parseDouble(raw.getMatL8()) / 1000));
            raw.setMatL9(String.format("%.2f", Double.parseDouble(raw.getMatL9()) / 1000));
            raw.setMatL10(String.format("%.2f", Double.parseDouble(raw.getMatL10()) / 1000));
            raw.setMatL11(String.format("%.2f", Double.parseDouble(raw.getMatL11()) / 1000));
            raw.setMatL12(String.format("%.2f", Double.parseDouble(raw.getMatL12()) / 1000));
            raw.setMatL13(String.format("%.2f", Double.parseDouble(raw.getMatL13()) / 1000));
            raw.setMatL14(String.format("%.2f", Double.parseDouble(raw.getMatL14()) / 1000));
            raw.setMatL15(String.format("%.2f", Double.parseDouble(raw.getMatL15()) / 1000));
            raw.setMatL16(String.format("%.2f", Double.parseDouble(raw.getMatL16()) / 1000));
            raw.setMatL17(String.format("%.2f", Double.parseDouble(raw.getMatL17()) / 1000));
            raw.setMatL18(String.format("%.2f", Double.parseDouble(raw.getMatL18()) / 1000));
            raw.setMatL19(String.format("%.2f", Double.parseDouble(raw.getMatL19()) / 1000));
            raw.setMatL20(String.format("%.2f", Double.parseDouble(raw.getMatL20()) / 1000));
            raw.setMatL21(String.format("%.2f", Double.parseDouble(raw.getMatL21()) / 1000));
            raw.setMatL22(String.format("%.2f", Double.parseDouble(raw.getMatL22()) / 1000));
            raw.setMatL23(String.format("%.2f", Double.parseDouble(raw.getMatL23()) / 1000));
        }

        return rawCollectVOS;
    }

}
