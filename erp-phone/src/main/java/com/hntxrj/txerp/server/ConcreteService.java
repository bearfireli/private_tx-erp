package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.ConcreteHistogram;
import com.hntxrj.txerp.vo.ConcreteVO;
import com.hntxrj.txerp.vo.PageVO;

import java.util.List;

public interface ConcreteService {

    /**
     * 砼产量统计
     * @param compid　企业
     * @param eppCode　工程代码
     * @param placing　浇筑部位
     * @param taskId　　任务单号
     * @param stgId　　　砼标记
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     * @param page　　　　页数
     * @param pageSize　　每页显示多少条
     */
    PageVO<ConcreteVO> getConcreteCount(String compid, String eppCode, String placing,
                                        String taskId, String stgId,
                                        String beginTime,
                                        String endTime, Integer timeStatus,
                                        Integer page, Integer pageSize);

    /**
     * 砼产量统计合计
     * @param compid　企业
     * @param eppCode　工程代码
     * @param placing　浇筑部位
     * @param taskId　　任务单号
     * @param stgId　　　砼标记
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     * @param page　　　　页数
     * @param pageSize　　每页显示多少条
     */
    PageVO<ConcreteVO> getConcreteSum(String compid, String eppCode, String placing,
                                        String taskId, String stgId,
                                        String beginTime,
                                        String endTime,Integer timeStatus,
                                      Integer page, Integer pageSize);


    /**
     * 产销统计中柱状图数据
     * @param compid　企业
     * @param eppCode　工程代码
     * @param placing　浇筑部位
     * @param taskId　　任务单号
     * @param stgId　　　砼标记
     * @param beginTime　　开始时间
     * @param endTime　　　结束时间
     */
    List<ConcreteHistogram> getConcreteSaleNum(String compid, String eppCode, String placing,
                                               String taskId, String stgId,
                                               String beginTime,
                                               String endTime, Integer timeStatus);


}
