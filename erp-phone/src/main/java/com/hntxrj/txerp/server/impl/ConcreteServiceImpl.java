package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.ConcreteMapper;
import com.hntxrj.txerp.server.ConcreteService;
import com.hntxrj.txerp.vo.ConcreteVO;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qyb
 * ConcreteServiceImpl
 * TODO
 * 19-6-10 下午3:23
 **/

@Service
public class ConcreteServiceImpl implements ConcreteService {

    private ConcreteMapper concreteMapper;

    @Autowired
    public ConcreteServiceImpl(ConcreteMapper concreteMapper) {
        this.concreteMapper = concreteMapper;
    }

    /**
     * 砼产量统计
     *
     * @param compid    　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     */
    @Override
    public PageVO<ConcreteVO> getConcreteCount(String compid, String eppCode, String placing,
                                               String taskId, String stgId, String beginTime,
                                               String endTime, Integer timeStatus, Integer page, Integer pageSize) {
        PageVO<ConcreteVO> pageVO = new PageVO<>();
        if (timeStatus == null) {
            timeStatus = 1;
        }
        PageHelper.startPage(page, pageSize);
        List<ConcreteVO> vehicleWorkloadSummaryVOS =
                concreteMapper.getConcreteCount(compid, eppCode, placing, taskId,
                        stgId, beginTime, endTime, timeStatus);
        for (ConcreteVO c : vehicleWorkloadSummaryVOS) {

            //生产方量从生产消耗表中查询，不从小票表中查询，因为小票中生产方量不准确。
            String produceBeginTime = c.getSendTime() + " 00:00:00";
            String produceEndTime = c.getSendTime() + " 23:59:59";
            BigDecimal productConcrete = concreteMapper.getProductConcreteByTaskId(compid, c.getTaskId(),produceBeginTime,produceEndTime);
            c.setProduceNum("0.00");
            if (productConcrete != null) {
                c.setProduceNum(productConcrete.toString());
            }

            // 保留小数点后两位数
            if (c.getProduceNum() != null && !"".equals(c.getProduceNum())) {
                String produceNum = c.getProduceNum();
                produceNum = produceNum.substring(0, produceNum.indexOf(".") + 3);
                c.setProduceNum(produceNum);
            }
            if (c.getSaleNum() != null && !"".equals(c.getSaleNum())) {
                String saleNum = c.getSaleNum();
                saleNum = saleNum.substring(0, saleNum.indexOf(".") + 3);
                c.setSaleNum(saleNum);
            }
        }
        PageInfo<ConcreteVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 砼产量统计合计
     *
     * @param compid    　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     */
    @Override
    public PageVO<ConcreteVO> getConcreteSum(String compid, String eppCode, String placing,
                                             String taskId, String stgId, String beginTime,
                                             String endTime, Integer timeStatus, Integer page, Integer pageSize) {
        PageVO<ConcreteVO> pageVO = new PageVO<>();
//        PageHelper.startPage(page, pageSize);
        if (timeStatus == null) {
            timeStatus = 1;
        }
        List<ConcreteVO> vehicleWorkloadSummaryVOS =
                concreteMapper.getConcreteSum(compid, eppCode, placing, taskId,
                        stgId, beginTime, endTime, timeStatus);
        for (ConcreteVO c : vehicleWorkloadSummaryVOS) {
            if (c != null) {
                String saleNumList = c.getSaleNumList();
                saleNumList = saleNumList.substring(0, saleNumList.indexOf(".") + 3);
                c.setSaleNumList(saleNumList);
            }
        }
        PageInfo<ConcreteVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }
}
