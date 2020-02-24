package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.ConsumeDao;
import com.hntxrj.txerp.entity.ProductConsume;
import com.hntxrj.txerp.mapper.ConsumeMapper;
import com.hntxrj.txerp.server.ConsumeService;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


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
     * @param mark  状态
     * @param compid 企业
     * @param currPage 当企业
     * @param pageSize 页长度
     * @param vehicleID 车号
     * @param stirId 线号
     * @param taskId 任务单好
     * @param stgId 砼标号
     * @param empname  操作员
     * @param placing 浇筑部位
     * @param eppName  工程名称
     * @param builderShorName 施工单位简称
     * @param openTime 查询时间
     * @param overTime 救赎时间
     * @return json
     */
    @Override
    public JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage, Integer pageSize, String vehicleID,
                                                 String stirId, String taskId, String stgId, String empname,
                                                 String placing, String eppName, String builderShorName, String openTime,
                                                 String overTime, Integer mark, Integer taskstatus, String opid) {
        return consumeDao.spQueryVQueryProduceConsume(compid, currPage, pageSize,
                vehicleID, stirId, taskId,
                stgId, empname, placing,
                eppName, builderShorName,
                openTime, overTime, mark,
                taskstatus, opid);
    }

    /**
     * 生产消耗汇总 list  一次执行3个
     *
     * @param compid          企业
     * @param currPage        当企业
     * @param pageSize        页长度
     * @param vehicleID       车号
     * @param stirId          线号
     * @param taskId          任务单好
     * @param stgId           砼标号
     * @param empname         操作员
     * @param placing         浇筑部位
     * @param eppName         工程名称
     * @param builderShorName 施工单位简称
     * @param openTime        查询时间
     * @param overTime        救赎时间
     * @return json
     */
    @Override
    public JSONArray spspQueryVAccountPTProduceConsumeList3(String compid, Integer currPage, Integer pageSize,
                                                            String vehicleID, Integer stirId, String taskId,
                                                            String stgId, String empname, String placing, String eppName,
                                                            String builderShorName, String openTime, String overTime) {
        return consumeDao.spspQueryVAccountPTProduceConsumeList3(compid, currPage, pageSize, vehicleID, stirId, taskId, stgId,
                empname, placing, eppName, builderShorName, openTime, overTime);
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
                                           String vehicleID, String beginTime, String endTime, String opid, Integer workclass) {
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
                                                    String vehicleId, String stgId, String taskId, String stirId, Integer page,
                                                    Integer pageSize) {
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
    public List<ConsumptionHistogram> getConsumptionHistogram(String compid, String beginTime, String endTime, String vehicleId, String stgId, String taskId, String stirId) {

        List<ConsumptionHistogram> consumptionHistograms = consumeMapper.getConsumptionHistogram(compid, beginTime, endTime,
                vehicleId, stgId, taskId, stirId);

        return consumptionHistograms;
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
        for (RawCollectVO raw : rawCollectVOS) {
            raw.setMatV1(String.format("%.2f", Double.valueOf(raw.getMatV1()) / 1000));
            raw.setMatV2(String.format("%.2f", Double.valueOf(raw.getMatV2()) / 1000));
            raw.setMatV3(String.format("%.2f", Double.valueOf(raw.getMatV3()) / 1000));
            raw.setMatV4(String.format("%.2f", Double.valueOf(raw.getMatV4()) / 1000));
            raw.setMatV5(String.format("%.2f", Double.valueOf(raw.getMatV5()) / 1000));
            raw.setMatV6(String.format("%.2f", Double.valueOf(raw.getMatV6()) / 1000));
            raw.setMatV7(String.format("%.2f", Double.valueOf(raw.getMatV7()) / 1000));
            raw.setMatV8(String.format("%.2f", Double.valueOf(raw.getMatV8()) / 1000));
            raw.setMatV9(String.format("%.2f", Double.valueOf(raw.getMatV9()) / 1000));
            raw.setMatV10(String.format("%.2f", Double.valueOf(raw.getMatV10()) / 1000));
            raw.setMatV11(String.format("%.2f", Double.valueOf(raw.getMatV11()) / 1000));
            raw.setMatV12(String.format("%.2f", Double.valueOf(raw.getMatV12()) / 1000));
            raw.setMatV13(String.format("%.2f", Double.valueOf(raw.getMatV13()) / 1000));
            raw.setMatV14(String.format("%.2f", Double.valueOf(raw.getMatV14()) / 1000));
            raw.setMatV15(String.format("%.2f", Double.valueOf(raw.getMatV15()) / 1000));
            raw.setMatV16(String.format("%.2f", Double.valueOf(raw.getMatV16()) / 1000));
            raw.setMatV17(String.format("%.2f", Double.valueOf(raw.getMatV17()) / 1000));
            raw.setMatV18(String.format("%.2f", Double.valueOf(raw.getMatV18()) / 1000));
            raw.setMatV19(String.format("%.2f", Double.valueOf(raw.getMatV19()) / 1000));
            raw.setMatV20(String.format("%.2f", Double.valueOf(raw.getMatV20()) / 1000));
            raw.setMatV21(String.format("%.2f", Double.valueOf(raw.getMatV21()) / 1000));
            raw.setMatV22(String.format("%.2f", Double.valueOf(raw.getMatV22()) / 1000));
            raw.setMatV23(String.format("%.2f", Double.valueOf(raw.getMatV23()) / 1000));
            raw.setMatL1(String.format("%.2f", Double.valueOf(raw.getMatL1()) / 1000));
            raw.setMatL2(String.format("%.2f", Double.valueOf(raw.getMatL2()) / 1000));
            raw.setMatL3(String.format("%.2f", Double.valueOf(raw.getMatL3()) / 1000));
            raw.setMatL4(String.format("%.2f", Double.valueOf(raw.getMatL4()) / 1000));
            raw.setMatL5(String.format("%.2f", Double.valueOf(raw.getMatL5()) / 1000));
            raw.setMatL6(String.format("%.2f", Double.valueOf(raw.getMatL6()) / 1000));
            raw.setMatL7(String.format("%.2f", Double.valueOf(raw.getMatL7()) / 1000));
            raw.setMatL8(String.format("%.2f", Double.valueOf(raw.getMatL8()) / 1000));
            raw.setMatL9(String.format("%.2f", Double.valueOf(raw.getMatL9()) / 1000));
            raw.setMatL10(String.format("%.2f", Double.valueOf(raw.getMatL10()) / 1000));
            raw.setMatL11(String.format("%.2f", Double.valueOf(raw.getMatL11()) / 1000));
            raw.setMatL12(String.format("%.2f", Double.valueOf(raw.getMatL12()) / 1000));
            raw.setMatL13(String.format("%.2f", Double.valueOf(raw.getMatL13()) / 1000));
            raw.setMatL14(String.format("%.2f", Double.valueOf(raw.getMatL14()) / 1000));
            raw.setMatL15(String.format("%.2f", Double.valueOf(raw.getMatL15()) / 1000));
            raw.setMatL16(String.format("%.2f", Double.valueOf(raw.getMatL16()) / 1000));
            raw.setMatL17(String.format("%.2f", Double.valueOf(raw.getMatL17()) / 1000));
            raw.setMatL18(String.format("%.2f", Double.valueOf(raw.getMatL18()) / 1000));
            raw.setMatL19(String.format("%.2f", Double.valueOf(raw.getMatL19()) / 1000));
            raw.setMatL20(String.format("%.2f", Double.valueOf(raw.getMatL20()) / 1000));
            raw.setMatL21(String.format("%.2f", Double.valueOf(raw.getMatL21()) / 1000));
            raw.setMatL22(String.format("%.2f", Double.valueOf(raw.getMatL22()) / 1000));
            raw.setMatL23(String.format("%.2f", Double.valueOf(raw.getMatL23()) / 1000));
        }
        PageInfo<RawCollectVO> pageInfo = new PageInfo<>(rawCollectVOS);
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
        //        PageHelper.startPage(page, pageSize);
        List<RawCollectVO> rawCollectVOS = consumeMapper.getProductionConsumptionDetails(compid, beginTime, endTime,
                stgId, vehicleId, taskId, stirId);
        for (RawCollectVO raw : rawCollectVOS) {
            raw.setMatV1(String.format("%.2f", Double.valueOf(raw.getMatV1()) / 1000));
            raw.setMatV2(String.format("%.2f", Double.valueOf(raw.getMatV2()) / 1000));
            raw.setMatV3(String.format("%.2f", Double.valueOf(raw.getMatV3()) / 1000));
            raw.setMatV4(String.format("%.2f", Double.valueOf(raw.getMatV4()) / 1000));
            raw.setMatV5(String.format("%.2f", Double.valueOf(raw.getMatV5()) / 1000));
            raw.setMatV6(String.format("%.2f", Double.valueOf(raw.getMatV6()) / 1000));
            raw.setMatV7(String.format("%.2f", Double.valueOf(raw.getMatV7()) / 1000));
            raw.setMatV8(String.format("%.2f", Double.valueOf(raw.getMatV8()) / 1000));
            raw.setMatV9(String.format("%.2f", Double.valueOf(raw.getMatV9()) / 1000));
            raw.setMatV10(String.format("%.2f", Double.valueOf(raw.getMatV10()) / 1000));
            raw.setMatV11(String.format("%.2f", Double.valueOf(raw.getMatV11()) / 1000));
            raw.setMatV12(String.format("%.2f", Double.valueOf(raw.getMatV12()) / 1000));
            raw.setMatV13(String.format("%.2f", Double.valueOf(raw.getMatV13()) / 1000));
            raw.setMatV14(String.format("%.2f", Double.valueOf(raw.getMatV14()) / 1000));
            raw.setMatV15(String.format("%.2f", Double.valueOf(raw.getMatV15()) / 1000));
            raw.setMatV16(String.format("%.2f", Double.valueOf(raw.getMatV16()) / 1000));
            raw.setMatV17(String.format("%.2f", Double.valueOf(raw.getMatV17()) / 1000));
            raw.setMatV18(String.format("%.2f", Double.valueOf(raw.getMatV18()) / 1000));
            raw.setMatV19(String.format("%.2f", Double.valueOf(raw.getMatV19()) / 1000));
            raw.setMatV20(String.format("%.2f", Double.valueOf(raw.getMatV20()) / 1000));
            raw.setMatV21(String.format("%.2f", Double.valueOf(raw.getMatV21()) / 1000));
            raw.setMatV22(String.format("%.2f", Double.valueOf(raw.getMatV22()) / 1000));
            raw.setMatV23(String.format("%.2f", Double.valueOf(raw.getMatV23()) / 1000));
            raw.setMatL1(String.format("%.2f", Double.valueOf(raw.getMatL1()) / 1000));
            raw.setMatL2(String.format("%.2f", Double.valueOf(raw.getMatL2()) / 1000));
            raw.setMatL3(String.format("%.2f", Double.valueOf(raw.getMatL3()) / 1000));
            raw.setMatL4(String.format("%.2f", Double.valueOf(raw.getMatL4()) / 1000));
            raw.setMatL5(String.format("%.2f", Double.valueOf(raw.getMatL5()) / 1000));
            raw.setMatL6(String.format("%.2f", Double.valueOf(raw.getMatL6()) / 1000));
            raw.setMatL7(String.format("%.2f", Double.valueOf(raw.getMatL7()) / 1000));
            raw.setMatL8(String.format("%.2f", Double.valueOf(raw.getMatL8()) / 1000));
            raw.setMatL9(String.format("%.2f", Double.valueOf(raw.getMatL9()) / 1000));
            raw.setMatL10(String.format("%.2f", Double.valueOf(raw.getMatL10()) / 1000));
            raw.setMatL11(String.format("%.2f", Double.valueOf(raw.getMatL11()) / 1000));
            raw.setMatL12(String.format("%.2f", Double.valueOf(raw.getMatL12()) / 1000));
            raw.setMatL13(String.format("%.2f", Double.valueOf(raw.getMatL13()) / 1000));
            raw.setMatL14(String.format("%.2f", Double.valueOf(raw.getMatL14()) / 1000));
            raw.setMatL15(String.format("%.2f", Double.valueOf(raw.getMatL15()) / 1000));
            raw.setMatL16(String.format("%.2f", Double.valueOf(raw.getMatL16()) / 1000));
            raw.setMatL17(String.format("%.2f", Double.valueOf(raw.getMatL17()) / 1000));
            raw.setMatL18(String.format("%.2f", Double.valueOf(raw.getMatL18()) / 1000));
            raw.setMatL19(String.format("%.2f", Double.valueOf(raw.getMatL19()) / 1000));
            raw.setMatL20(String.format("%.2f", Double.valueOf(raw.getMatL20()) / 1000));
            raw.setMatL21(String.format("%.2f", Double.valueOf(raw.getMatL21()) / 1000));
            raw.setMatL22(String.format("%.2f", Double.valueOf(raw.getMatL22()) / 1000));
            raw.setMatL23(String.format("%.2f", Double.valueOf(raw.getMatL23()) / 1000));
        }
        PageInfo<RawCollectVO> pageInfo = new PageInfo<>(rawCollectVOS);
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

    @Override
    public PageVO<StockVO> getProductDatail(String compid, Integer stirId) {
//        PageHelper.startPage(page, pageSize);
        List<StockVO> consumptionTotalVOS = consumeMapper.getProductDatail(compid, stirId);
        PageInfo<StockVO> pageInfo = new PageInfo<>(consumptionTotalVOS);
        PageVO<StockVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public Integer getErroPan(String compid, String beginTime, String endTime) {
        Integer panValue = 0;
        List<ProductConsume> productConsumeList = consumeMapper.getErroPan(compid, beginTime, endTime);
        for (ProductConsume productConsume : productConsumeList) {
            if (productConsume.getMatV1().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV1(), productConsume.getMatL1(), new BigDecimal(0.02))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV2().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV2(), productConsume.getMatL2(), new BigDecimal(0.02))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV3().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV3(), productConsume.getMatL3(), new BigDecimal(0.02))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV4().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV4(), productConsume.getMatL4(), new BigDecimal(0.02))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV5().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV5(), productConsume.getMatL5(), new BigDecimal(0.02))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV6().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV6(), productConsume.getMatL6(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV7().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV7(), productConsume.getMatL7(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV8().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV8(), productConsume.getMatL8(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV9().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV9(), productConsume.getMatL9(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV10().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV10(), productConsume.getMatL10(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV11().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV11(), productConsume.getMatL11(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV12().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV12(), productConsume.getMatL12(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV13().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV13(), productConsume.getMatL13(), new BigDecimal(0.01))) {

                panValue++;
                continue;
            }
            if (productConsume.getMatV14().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV14(), productConsume.getMatL14(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV15().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV15(), productConsume.getMatL15(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV16().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV16(), productConsume.getMatL16(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV17().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV17(), productConsume.getMatL17(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV18().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV18(), productConsume.getMatL18(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV19().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV19(), productConsume.getMatL19(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV20().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV20(), productConsume.getMatL20(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }
            if (productConsume.getMatV21().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV21(), productConsume.getMatL21(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV22().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV22(), productConsume.getMatL22(), new BigDecimal(0.01))) {
                panValue++;
                continue;

            }
            if (productConsume.getMatV23().compareTo(BigDecimal.ZERO) != 0 && outRange(productConsume.getMatV23(), productConsume.getMatL23(), new BigDecimal(0.01))) {
                panValue++;
                continue;
            }

        }

        return panValue;
    }


    private boolean outRange(BigDecimal theory, BigDecimal actual, BigDecimal standard) {

        //获得理论值和实际值的差值
        BigDecimal subtract = theory.subtract(actual);
        //获得差值占理论值的比重
        BigDecimal divide = subtract.divide(theory, 3, RoundingMode.HALF_UP);
        //获得差值所占百分比的绝对值
        BigDecimal percentage = divide.abs();


        return percentage.compareTo(standard) > 0;
    }

}
