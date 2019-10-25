package com.hntxrj.server;

import com.hntxrj.vo.ConcreteVO;
import com.hntxrj.vo.DriverTransportationCountVO;
import com.hntxrj.vo.PageVO;

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
}
