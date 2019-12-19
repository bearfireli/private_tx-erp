package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.ConcreteHistogram;
import com.hntxrj.txerp.vo.ConcretePieChart;
import com.hntxrj.txerp.vo.ConcreteVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 砼产量统计
 */
@Mapper
public interface ConcreteMapper {

    /**
     * 砼产量统计
     * @param compid　企业
     * @param eppCode　工程代码
     * @param placing　浇筑部位
     * @param taskId　　任务单号
     * @param stgId　　　砼标记
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     */
    List<ConcreteVO> getConcreteCount(String compid, String eppCode, String placing,
                                      String taskId, String stgId, String beginTime, String endTime, Integer timeStatus);

    /**
     * 砼产量统计合计
     * @param compid　企业
     * @param eppCode　工程代码
     * @param placing　浇筑部位
     * @param taskId　　任务单号
     * @param stgId　　　砼标记
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     */
    List<ConcreteVO> getConcreteSum(String compid, String eppCode, String placing, String taskId, String stgId,
                                    String beginTime, String endTime,Integer timeStatus);


    /**
     * 生产方量统计（从生产消耗表中查询）
     * @param compid　企业
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     * */
    BigDecimal getProductConcreteSum(String compid,String beginTime,String endTime);

    /**
     * 任务单的生产方量（从生产消耗表中查询）
     * @param compid　企业
     * @param taskId　　任务单号
     * */
    BigDecimal getProductConcreteByTaskId(String compid,String taskId,String produceBeginTime,String produceEndTime);

    /**
     * 产销统计中柱状图数据
     * */
    List<ConcreteHistogram> getConcreteSaleNum(String compid);

    /**
     * 产销统计中饼状图数据
     * */
    List<ConcretePieChart> getConcreteStgIdNum(String compid, String eppCode, String placing, String taskId, String stgId, String beginTime, String endTime, Integer timeStatus);
}
