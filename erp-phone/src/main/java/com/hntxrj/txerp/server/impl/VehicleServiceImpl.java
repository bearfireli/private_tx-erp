package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.VehicleDao;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.mapper.VehicleWorkloadMapper;
import com.hntxrj.txerp.server.VehicleService;
import com.hntxrj.txerp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能:  搅拌车服务层接口实现类
 *
 * @Auther 李帅
 * @Data 2017-08-15.下午 6:21
 */
@Service
@Scope("prototype")
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleDao vehicleDao;
    private final VehicleWorkloadMapper vehicleWorkloadMapper;

    @Autowired
    public VehicleServiceImpl(VehicleDao vehicleDao, VehicleWorkloadMapper vehicleWorkloadMapper) {
        this.vehicleDao = vehicleDao;
        this.vehicleWorkloadMapper = vehicleWorkloadMapper;
    }

    /**
     * 搅拌车工作量统计
     *
     * @param mark             标记
     * @param beginTime        开始日期
     * @param endTime          结束日期
     * @param taskId           任务单ID
     * @param builderShortName 工程名简称
     * @param placing          浇灌部位
     * @param eppName          工程名称
     * @param vehicleID        车号
     * @param personalCode     司机代号
     * @param compid           站别代号
     * @param pageBean         分页
     * @param pageBeanList     多组分页
     */
    @Override
    public JSONArray getTotalVehicle(Integer mark, String beginTime, String endTime, String taskId,
                                     String builderShortName, String placing, String eppName,
                                     String vehicleID, String personalCode, String compid, String personalName,
                                     PageBean pageBean, List<PageBean> pageBeanList, Integer id, String opid) {
        return vehicleDao.getTotalVehicle(mark, beginTime, endTime, taskId, builderShortName, placing, eppName,
                vehicleID, personalCode, compid, personalName, pageBean, pageBeanList, id, opid);
    }


    /**
     * 砼产量统计
     *
     * @param TaskStatus       任务单状态
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param compid           生产站点
     * @param StirId           搅拌楼编号
     * @param OpId             调度员
     * @param eppname          工程名
     * @param TaskId           任务单ID
     * @param BuilderShortName 施工单位名简称
     * @param ReportCode       报告编号
     * @param Placing          浇灌部位
     * @param PlaceStyle       浇灌方式
     * @param InvoiceType      小票类型
     * @param pageBean         分页
     */
    @Override
    public JSONArray getTotalYield(String TaskStatus, String beginTime, String endTime,
                                   String compid, String StirId, String OpId,
                                   String eppname, String TaskId, String BuilderShortName,
                                   String ReportCode, String Placing, String PlaceStyle, String InvoiceType,
                                   PageBean pageBean, String stgid, String opid) {
        return vehicleDao.getTotalYield(TaskStatus, beginTime, endTime, compid, StirId, OpId, eppname,
                TaskId, BuilderShortName, ReportCode, Placing, PlaceStyle, InvoiceType, pageBean, stgid, opid);
    }


    /**
     * 司机排班信息管理 sp_insertUpDel_VM_PersonalWorkClass
     *
     * @param mark         --操作标识 0 插入 1 更新 2 删除
     * @param compid       企业
     * @param opid         登录用户
     * @param id           操作表的主键  VM_PersonalWorkClass
     * @param vehicleid    车号
     * @param workclass    班次
     * @param personalcode 司机code
     * @param workstartime 上班时间
     * @param workovertime 下班时间
     * @return json
     */
    @Override
    public JSONArray sp_insertUpDel_VM_PersonalWorkClass(Integer mark, String compid, String opid, String id,
                                                         String vehicleid, String workclass, String personalcode,
                                                         String remarks, String workstartime, String workovertime) {
        return vehicleDao.sp_insertUpDel_VM_PersonalWorkClass(mark, compid, opid, id, vehicleid,
                workclass, personalcode, remarks, workstartime, workovertime);
    }

    /**
     * 搅拌车砼运输明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @Override
    public PageVO<VehicleWorkloadDetailVO> getVehicleWorkloadDetail(String compid, String personalName,
                                                                    String eppCode, String placing,
                                                                    String taskId, String vehicleId,
                                                                    String beginTime, String endTime,
                                                                    Integer page, Integer pageSize) {
        PageVO<VehicleWorkloadDetailVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<VehicleWorkloadDetailVO> vehicleWorkloadDetailVOS =
                vehicleWorkloadMapper.getVehicleWorkloadDetail(compid, personalName, eppCode, placing, taskId,
                        vehicleId, beginTime, endTime);
        PageInfo<VehicleWorkloadDetailVO> pageInfo = new PageInfo<>(vehicleWorkloadDetailVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 搅拌车砼运输汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     * @return 乔亚北
     */
    @Override
    public PageVO<VehicleWorkloadSummaryVO> getVehicleWorkloadSummary(String compid, String personalName, String eppCode,
                                                                      String placing, String taskId,
                                                                      String vehicleId,
                                                                      String beginTime, String endTime,
                                                                      Integer page, Integer pageSize) {
        PageVO<VehicleWorkloadSummaryVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<VehicleWorkloadSummaryVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getVehicleWorkloadSummary(compid, personalName, eppCode, placing, taskId,
                        vehicleId, beginTime, endTime);
        PageInfo<VehicleWorkloadSummaryVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 搅拌车砼运输汇总合计
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     * @return 乔亚北
     */
    @Override
    public PageVO<VehicleWorkloadSummaryVO> getVehicleWorkloadSummaryCount(String compid, String personalName,
                                                                           String eppCode,
                                                                           String placing, String taskId,
                                                                           String vehicleId,
                                                                           String beginTime, String endTime,
                                                                           Integer page, Integer pageSize) {
        PageVO<VehicleWorkloadSummaryVO> pageVO = new PageVO<>();
//        PageHelper.startPage(page, pageSize);
        List<VehicleWorkloadSummaryVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getVehicleWorkloadSummaryCount(compid, personalName, eppCode, placing, taskId,
                        vehicleId, beginTime, endTime);
        PageInfo<VehicleWorkloadSummaryVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 搅拌车拖水拖泵明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @Override
    public PageVO<VehicleWorkTowingPumpDetailVO> getVehicleWorkTowingPumpDetail(String compid, String personalName,
                                                                                String eppCode, String placing,
                                                                                String taskId,
                                                                                String vehicleId,
                                                                                String beginTime, String endTime,
                                                                                Integer page, Integer pageSize) {
        PageVO<VehicleWorkTowingPumpDetailVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize, "SendTime,taskId Desc");
        List<VehicleWorkTowingPumpDetailVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getVehicleWorkTowingPumpDetail(compid, personalName, eppCode, placing, taskId,
                        vehicleId, beginTime, endTime);
        PageInfo<VehicleWorkTowingPumpDetailVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 搅拌车拖水拖泵汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @Override
    public PageVO<VehicleWorkTowingPumpCountVO> getVehicleWorkTowingPumpCount(String compid, String personalName,
                                                                              String eppCode, String placing,
                                                                              String taskId,
                                                                              String vehicleId,
                                                                              String beginTime, String endTime,
                                                                              Integer page, Integer pageSize) {
        PageVO<VehicleWorkTowingPumpCountVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<VehicleWorkTowingPumpCountVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getVehicleWorkTowingPumpCount(compid, personalName, eppCode, placing, taskId,
                        vehicleId, beginTime, endTime);
        PageInfo<VehicleWorkTowingPumpCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 搅拌车过磅查询
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empName   过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param page       分页
     * @param pageSize   每页显示条数
     */
    @Override
    public PageVO<WorkloadStatisticsVo> getWorkloadStatistics(String compid, String eppCode, String empName,
                                                              Integer weightType,
                                                              String beginTime, String endTime,
                                                              Integer page, Integer pageSize) {
        PageVO<WorkloadStatisticsVo> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize, "SecondTime desc");

        log.info("搅拌车过磅查询:compid:{},eppCode:{},empNameb:{},weightType:{},beginTime:{},endTime:{}",
                compid, eppCode, empName, weightType,
                beginTime, endTime);
        List<WorkloadStatisticsVo> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getWorkloadStatistics(compid, eppCode, empName, weightType,
                        beginTime, endTime);
        PageInfo<WorkloadStatisticsVo> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机砼运输明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */

    @Override
    public PageVO<DriverworkloadStatisticsVO> getDriverTransportationDetails(String compid, String eppCode,
                                                                             String placing, String taskId,
                                                                             String vehicleId, String personalName,
                                                                             String beginTime, String endTime,
                                                                             Integer page, Integer pageSize) {
        PageVO<DriverworkloadStatisticsVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize, "SendTime");
        List<DriverworkloadStatisticsVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getDriverTransportationDetails(compid, eppCode, placing,
                        taskId, vehicleId, personalName,
                        beginTime, endTime);
        PageInfo<DriverworkloadStatisticsVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机砼运输汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<DriverTransportationCountVO> getDriverTransportationCount(String compid, String eppCode,
                                                                            String placing, String taskId,
                                                                            String vehicleId, String personalName,
                                                                            String isNewVersion,
                                                                            String beginTime, String endTime,
                                                                            Integer page, Integer pageSize) {

        if ("1".equals(isNewVersion)) {
            //说明是新版本
            PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                    vehicleWorkloadMapper.getDriverTransportationCountNew(compid, eppCode, placing,
                            taskId, vehicleId, personalName,
                            beginTime, endTime);
            PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
            pageVO.format(pageInfo);
            return pageVO;

        }else{
            PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                    vehicleWorkloadMapper.getDriverTransportationCount(compid, eppCode, placing,
                            taskId, vehicleId, personalName,
                            beginTime, endTime);
            PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
            pageVO.format(pageInfo);
            return pageVO;
        }

    }

    /**
     * 司机砼运输合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<DriverTransportationCountVO> getDriverTransportationSum(String compid, String eppCode,
                                                                          String placing, String taskId,
                                                                          String vehicleId, String personalName,
                                                                          String beginTime, String endTime,
                                                                          Integer page, Integer pageSize) {
        PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getDriverTransportationSum(compid, eppCode, placing,
                        taskId, vehicleId, personalName,
                        beginTime, endTime);
        PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机拖水拖泵运输车数合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<DriverTransportationCountVO> getDriverTransportationCarNumList(String compid,
                                                                                 String eppCode,
                                                                                 String placing, String taskId,
                                                                                 String vehicleId,
                                                                                 String personalName,
                                                                                 String beginTime,
                                                                                 String endTime,
                                                                                 Integer page, Integer pageSize) {
        PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getDriverTransportationCarNumList(compid, eppCode, placing,
                        taskId, vehicleId, personalName,
                        beginTime, endTime);
        PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机拖水拖泵汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<DriverTransportationCountVO> getDriverDragPumpCount(String compid, String eppCode,
                                                                      String placing, String taskId,
                                                                      String vehicleId, String personalName,
                                                                      String isNewVersion,
                                                                      String beginTime, String endTime,
                                                                      Integer page, Integer pageSize) {

        if ("1".equals(isNewVersion)) {
            //说明是新版本
            PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                    vehicleWorkloadMapper.getDriverDragPumpCountNew(compid, eppCode, placing, taskId,
                            vehicleId, personalName, beginTime, endTime);
            PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
            pageVO.format(pageInfo);
            return pageVO;
        }else{
            PageVO<DriverTransportationCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            List<DriverTransportationCountVO> vehicleWorkloadSummaryVOS =
                    vehicleWorkloadMapper.getDriverDragPumpCount(compid, eppCode, placing, taskId,
                            vehicleId, personalName, beginTime, endTime);
            PageInfo<DriverTransportationCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
            pageVO.format(pageInfo);
            return pageVO;
        }

    }


    /**
     * 泵车汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<PumpTruckCountVO> getPumpTruckCount(String compid, String eppCode, String personalName,
                                                      String stirId, String vehicleId,String taskId,
                                                      String beginTime, String endTime,
                                                      Integer page, Integer pageSize) {
        PageVO<PumpTruckCountVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        try {
            List<PumpTruckCountVO> vehicleWorkloadSummaryVOS =
                    vehicleWorkloadMapper.getPumpTruckCount(compid, eppCode, personalName,
                            stirId, vehicleId,taskId,
                            beginTime, endTime);
            for (PumpTruckCountVO ptc : vehicleWorkloadSummaryVOS) {
                if (ptc.getVehicleId() == null) {
                    ptc.setVehicleId(ptc.getTypeName());
                }
            }
            PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
            pageVO.format(pageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>();
            pageVO.format(pageInfo);
            return pageVO;
        }
        return pageVO;
    }

    /**
     * 泵工汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<PumpTruckCountVO> getPumpOperatorTruckCount(String compid, String eppCode,
                                                              String personalName, String stirId,
                                                              String vehicleId,String taskId,String isNewVersion,
                                                              String beginTime, String endTime,
                                                              Integer page, Integer pageSize) {

        if ("1".equals(isNewVersion)) {
            //说明是新版本
            PageVO<PumpTruckCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            try {
                List<PumpTruckCountVO> vehicleWorkloadSummaryVOS =
                        vehicleWorkloadMapper.getPumpOperatorTruckCountNew(compid, eppCode,
                                personalName, stirId, vehicleId,taskId,
                                beginTime, endTime);
                PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
                pageVO.format(pageInfo);
            } catch (Exception e) {
                e.printStackTrace();
                PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>();
                pageVO.format(pageInfo);
                return pageVO;
            }
            return pageVO;

        }else{
            PageVO<PumpTruckCountVO> pageVO = new PageVO<>();
            PageHelper.startPage(page, pageSize);
            try {
                List<PumpTruckCountVO> vehicleWorkloadSummaryVOS =
                        vehicleWorkloadMapper.getPumpOperatorTruckCount(compid, eppCode,
                                personalName, stirId, vehicleId,taskId,
                                beginTime, endTime);
                for (PumpTruckCountVO ptc : vehicleWorkloadSummaryVOS) {
                    if (ptc.getVehicleId() == null) {
                        ptc.setVehicleId(ptc.getTypeName());
                    }
                }
                PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
                pageVO.format(pageInfo);
            } catch (Exception e) {
                e.printStackTrace();
                PageInfo<PumpTruckCountVO> pageInfo = new PageInfo<>();
                pageVO.format(pageInfo);
                return pageVO;
            }
            return pageVO;
        }

    }

    /**
     * 泵车明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<PumpTruckDetailsVO> getPumpTruckDetails(String compid, String eppCode, String personalName,
                                                          String stirId, String vehicleId,String typeName,String taskId,
                                                          String beginTime, String endTime,
                                                          Integer page, Integer pageSize) {
        PageVO<PumpTruckDetailsVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<PumpTruckDetailsVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getPumpTruckDetails(compid, eppCode, personalName,
                        stirId, vehicleId,typeName,taskId,
                        beginTime, endTime);
        PageInfo<PumpTruckDetailsVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 工作方量统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<PumpTruckWorkloadStatisticsVO> getPumpTruckWorkloadStatistics(String compid, String eppCode,
                                                                                String personalName, String taskId,
                                                                                String stirId,
                                                                                String vehicleId, String beginTime,
                                                                                String endTime, Integer page,
                                                                                Integer pageSize) {
        PageVO<PumpTruckWorkloadStatisticsVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<PumpTruckWorkloadStatisticsVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getPumpTruckWorkloadStatistics(compid, eppCode, personalName,taskId,
                        stirId, vehicleId,
                        beginTime, endTime);
        PageInfo<PumpTruckWorkloadStatisticsVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 泵车合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @Override
    public PageVO<PumpTruckWorkloadStatisticsVO> getPumpTruckSum(String compid, String eppCode,
                                                                 String personalName, String stirId,
                                                                 String vehicleId, String beginTime,
                                                                 String endTime, Integer page,
                                                                 Integer pageSize) {
        PageVO<PumpTruckWorkloadStatisticsVO> pageVO = new PageVO<>();
//        PageHelper.startPage(page, pageSize);
        List<PumpTruckWorkloadStatisticsVO> vehicleWorkloadSummaryVOS =
                vehicleWorkloadMapper.getPumpTruckSum(compid, eppCode, personalName,
                        stirId, vehicleId,
                        beginTime, endTime);


        PageInfo<PumpTruckWorkloadStatisticsVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 搅拌车过磅查询汇总
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empName   过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     */
    @Override
    public Map<String, Double> getWorkloadStatisticsCount(String compid, String eppCode, String empName,
                                                          Integer weightType, String beginTime, String endTime) {
        Double totalNum=vehicleWorkloadMapper.getWorkloadStatisticsCount(compid,eppCode,empName,weightType,
                beginTime,endTime);
        Map<String, Double> map = new HashMap<>();
        map.put("totalNum", totalNum);
        return map;
    }

}
