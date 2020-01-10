package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.BuilderDao;
import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.mapper.BuilderMapper;
import com.hntxrj.txerp.mapper.ConstructionMapper;
import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.server.TaskListServer;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能:   施工单位服务接口实现层
 * <p>
 * 李帅
 * 2017-08-11.上午 11:13
 */
@Service
@Scope("prototype")

public class BuilderServiceImpl implements BuilderService {

    private final BuilderDao builderDao;
    private final BuilderMapper builderMapper;
    private final ConstructionMapper constructionMapper;


    @Autowired
    public BuilderServiceImpl(BuilderDao builderDao, BuilderMapper builderMapper, ConstructionMapper constructionMapper) {
        this.builderDao = builderDao;
        this.builderMapper = builderMapper;
        this.constructionMapper = constructionMapper;
    }


    /**
     * 加载详情列表
     *
     * @param builderName 工程名
     * @param pageBean    页码相关实体
     */
    @Override
    public JSONArray getEppList(String builderName, PageBean pageBean, String compid) {
        return builderDao.getBuilderList(builderName, pageBean, compid);
    }


    /**
     * 添加修改删除  施工单位
     *
     * @param Mark               操作标识 0 插入 1 更新 2 删除
     * @param compid             企业ID
     * @param OpId               操作员ID
     * @param BuilderCode        施工单位代号
     * @param BuilderName_1      施工单位名
     * @param BuilderShortName_2 施工单位名简称
     * @param Address_3          地址
     * @param CreateTime_4       创建时间
     * @param Corporation_5      法人
     * @param Fax_6              传真
     * @param LinkTel_7          联系电话
     * @param RecStatus_8        记录状态(有效)   0未启用 1启用(0无效1有效)
     */
    @Override
    public JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String OpId, String BuilderCode,
                                                String BuilderName_1, String BuilderShortName_2, String Address_3,
                                                Timestamp CreateTime_4, String Corporation_5, String Fax_6,
                                                String LinkTel_7, byte RecStatus_8) {
        return builderDao.insertUpDel_SM_BUILDERINFO(Mark, compid, OpId, BuilderCode, BuilderName_1, BuilderShortName_2,
                Address_3, CreateTime_4, Corporation_5, Fax_6, LinkTel_7, RecStatus_8);
    }

    @Override
    public PageVO<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "createTime desc");
        List<BuilderDropDownVO> builderDropDownVOS = builderMapper.getBuilderDropDown(compid, builderName);
        PageInfo<BuilderDropDownVO> pageInfo = new PageInfo<>(builderDropDownVOS);
        PageVO<BuilderDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public BuilderInfo getBuilderInfo(String builderCode, String compid) {
        return builderMapper.getBuilderInfo(builderCode, compid);
    }


    /**
     * 工地端App产销统计查询
     *
     * @param buildId   　施工方用户id
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
    public PageVO<ConcreteVO> getBuilderConcreteCount(Integer buildId, String eppCode, String placing,
                                                      String taskId, String stgId, String beginTime,
                                                      String endTime, Integer timeStatus, Integer page, Integer pageSize) {
        PageVO<ConcreteVO> pageVO = new PageVO<>();
        if (timeStatus == null) {
            timeStatus = 1;
        }

        //首先根据buildId查询出施工方用户关联的合同列表
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);
        if (contractDetailCodes.size() == 0 || contractUIDList.size() == 0) {
            pageVO.setArr(new ArrayList<>());
            return pageVO;
        }

        PageHelper.startPage(page, pageSize);
        //查询施工方用户关联合同下的产销统计
        List<ConcreteVO> concreteVOS = builderMapper.getBuilderConcreteCount(contractDetailCodes, contractUIDList, eppCode, placing, taskId,
                stgId, beginTime, endTime, timeStatus);



        //再根据合同列表为条件查询关联的任务单的生产消耗
        for (ConcreteVO c : concreteVOS) {

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
        PageInfo<ConcreteVO> pageInfo = new PageInfo<>(concreteVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 查询产销统计中发货方量统计
     *
     * @param buildId   　施工方用户id
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     */
    @Override
    public Map<String, BigDecimal> getBuilderConcreteSum(Integer buildId, String eppCode, String placing, String taskId, String stgId, String beginTime, String endTime, Integer timeStatus) {


        if (timeStatus == null) {
            timeStatus = 1;
        }
        Map<String, BigDecimal> map = new HashMap<>();
        //首先根据buildId查询出施工方用户关联的合同列表
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);

        if (contractDetailCodes.size() == 0 || contractUIDList.size() == 0) {
            return null;
        }
        BigDecimal totalSaleNum = builderMapper.getBuilderConcreteSum(contractDetailCodes, contractUIDList, eppCode, placing, taskId,
                stgId, beginTime, endTime, timeStatus);


        map.put("totalSaleNum", totalSaleNum);

        return map;
    }

    @Override
    public PageVO<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(Integer buildId, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String taskId, String placing, String taskStatus, Integer page, Integer pageSize) {
        PageVO<TaskSaleInvoiceListVO> pageVO = new PageVO<>();
        //首先根据buildId查询出施工方用户关联的合同列表
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);
        if (contractDetailCodes.size() == 0 || contractUIDList.size() == 0) {

            pageVO.setArr(new ArrayList<>());
            return pageVO;
        }

        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceListVO> taskSaleInvoiceLists = builderMapper.getBuildTaskSaleInvoiceList(contractDetailCodes, contractUIDList, beginTime,
                endTime, eppCode, upStatus, builderCode, taskId, placing, taskStatus);
        PageInfo<TaskSaleInvoiceListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<SendCarListVO> getBuildSendCarList(Integer buildId, String searchName, Integer page, Integer pageSize) {
        PageVO<SendCarListVO> pageVO = new PageVO<>();

        //首先根据buildId查询出施工方用户关联的合同列表
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);

        if (contractDetailCodes.size() == 0 || contractUIDList.size() == 0) {
            //说明施工方用户未绑定合同
            pageVO.setArr(new ArrayList<>());
            return pageVO;
        }

        PageHelper.startPage(page, pageSize);
        List<SendCarListVO> sendCarList = builderMapper.getBuildSendCarList(contractDetailCodes, contractUIDList, searchName);


        //查询出所有正在生产的任务单号集合。
        List<String> taskIds = new ArrayList<>();
        for (SendCarListVO sendCarListVO : sendCarList) {
            if (sendCarListVO.getTotalProduceNum() == null) {
                sendCarListVO.setTotalProduceNum("0.0");
            }
            if (sendCarListVO.getTaskId() != null) {
                taskIds.add(sendCarListVO.getTaskId());
            }

        }


        //根据任务单号集合查询出所有的车号。
        List<DispatchVehicle> cars = new ArrayList<>();
        if (taskIds.size() > 0) {
            cars = builderMapper.getCarsByTaskIds(contractDetailCodes,contractUIDList, taskIds);
        }

        //根据每个车辆的任务单号，把所有车辆关联到调度派车列表中
        for (SendCarListVO sendCarListVO : sendCarList) {
            List<DispatchVehicle> dispatchVehicleList = new ArrayList<>();
            for (DispatchVehicle car : cars) {
                //判断厦门华信特殊情况（先打票，再生产）
                if ("24".equals(sendCarListVO.getCompid())) {
                    if (car.getTaskStatus() == 1 && car.getInvoiceType() == 4) {
                        car.setVehicleStatus("3");
                        car.setStatusName("生产");
                    }
                }
                //根据任务单号关联调度派车列表和其对应的车辆
                if (sendCarListVO.getTaskId().equals(car.getTaskId())) {
                    dispatchVehicleList.add(car);
                }
            }
            sendCarListVO.setCars(dispatchVehicleList);
        }

        PageInfo<SendCarListVO> pageInfo = new PageInfo<>(sendCarList);

        pageVO.format(pageInfo);
        return pageVO;
    }


}
