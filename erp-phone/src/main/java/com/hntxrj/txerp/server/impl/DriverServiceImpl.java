package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.dao.DriverDao;
import com.hntxrj.txerp.entity.GpsLocateTempInfo;
import com.hntxrj.txerp.mapper.DriverMapper;
import com.hntxrj.txerp.rabbitmq.JPushUtil;
import com.hntxrj.txerp.server.DriverService;
import com.hntxrj.txerp.server.VehicleService;
import com.hntxrj.txerp.vo.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能及介绍：司机模块数据处理层
 * <p>
 */
@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDao driverDao;
    private final DriverMapper driverMapper;
    private final VehicleService vehicleService;
    private final JPushUtil jPushUtil;

    @Resource
    private RedisTemplate<String, Date> redisTemplate;

    @Resource
    private RedisTemplate<String, List<VehicleIdVO>> driverPushRedisTemplate;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao, DriverMapper driverMapper, VehicleService vehicleService, JPushUtil jPushUtil) {
        this.driverDao = driverDao;
        this.driverMapper = driverMapper;
        this.vehicleService = vehicleService;
        this.jPushUtil = jPushUtil;
    }

    /**
     * 司机排班数据获取
     *
     * @param compId        企业id
     * @param stirId        线号
     * @param vehicleStatus 车辆状态
     * @param vehicleClass  班次
     */
    @Override
    public JSONArray getDriverScheduling(String compId, String stirId, String vehicleStatus, String vehicleClass) {
        return driverDao.getDriverScheduling(compId, stirId, vehicleStatus, vehicleClass);
    }

    @Override
    public List<DriverVO> getDriverList(String compid, String token, String driverName) {
        return driverMapper.getDriverList(compid, driverName);
    }

    @Override
    public List<TaskJumpVO> getJumpVehicleList(String compid, String token, String taskId) {
        return driverMapper.getJumpVehicleList(compid, token, taskId);
    }

    @Override
    public void taskSaleInvoiceReceipt(String receiptPeople, Double numberOfSignings,
                                       String jumpVehicle, String sign, String invoiceId) {
        SimpleDateFormat dateFormat = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        if (receiptPeople == null) {
            receiptPeople = "";
        }
        if (sign == null) {
            sign = "";
        }
        driverMapper.taskSaleInvoiceReceipt(receiptPeople, numberOfSignings, jumpVehicle == null ? "" : jumpVehicle,
                sign, invoiceId, dateFormat.format(new Date()));
    }

    @Override
    public Map<String, String> getDriverName(String compid, String driverCode) {
        //如果传递的公司代号是个位数，前面加一个0
        if (compid.length() == 1) {
            compid = "0" + compid;
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("driverName", driverMapper.getDriverName(compid, driverCode));
        return resultMap;
    }

    @Override
    public String getDriverNames(String compid, String driverCode) {
        //如果传递的公司代号是个位数，前面加一个0
        if (compid.length() == 1) {
            compid = "0" + compid;
        }
        return driverMapper.getDriverNames(compid, driverCode);
    }

    @Override
    public void getTaskSaleInvoiceReceiptSign(String taskSaleInvoiceUploadPath, String fileName,
                                              HttpServletResponse response) throws ErpException {

        File file = new File(taskSaleInvoiceUploadPath + fileName);
        if (!file.exists()) {
            file = new File(taskSaleInvoiceUploadPath + "default.png");
        }
        try {
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.GET_PICTURE_ERROR);
        }
    }

    @Override
    public PageVO<TaskSaleInvoiceDriverListVO> getTaskSaleInvoiceList(Integer invoiceId, String compid, String beginTime,
                                                                      String endTime, String eppCode, Byte upStatus,
                                                                      String builderCode, String placing,
                                                                      Integer page,
                                                                      Integer pageSize, String driverCode) {
        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceDriverListVO> taskSaleInvoiceLists =
                driverMapper.driverGetTaskSaleInvoiceList(invoiceId == null ? null : String.valueOf(invoiceId),
                        compid, beginTime, endTime, eppCode, upStatus, builderCode, placing, driverCode);

        PageInfo<TaskSaleInvoiceDriverListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        PageVO<TaskSaleInvoiceDriverListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetail(String compid, Integer id) {
        TaskSaleInvoiceDetailVO taskSaleInvoiceDetailVO = driverMapper.driverGetTaskSaleInvoiceDetail(id, compid);
        //兼容老版本，老版本前台用的签收方量时qianNum.
        if (taskSaleInvoiceDetailVO != null) {
            taskSaleInvoiceDetailVO.setQianNum(taskSaleInvoiceDetailVO.getNumberOfSignings());
        }
        return taskSaleInvoiceDetailVO;
    }

    @Override
    public void saveSaleFileImage(String saleFileImage, String invoiceId, String compid, Double numberOfSignings,
                                  String jumpVehicle) {
        SimpleDateFormat dateFormat = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        driverMapper.saveSaleFileImage(compid, saleFileImage, invoiceId, dateFormat.format(new Date()), numberOfSignings,
                jumpVehicle);
    }

    @Override
    public void saveNumberOfSignings(String compid, Double numberOfSignings, String invoiceId) {
        SimpleDateFormat dateFormat = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        driverMapper.saveNumberOfSignings(compid, numberOfSignings, invoiceId, dateFormat.format(new Date()));
    }


    /**
     * 修改小票中的车辆状态
     *
     * @param compid        企业
     * @param id            小票id
     * @param vehicleStatus 车辆状态   13：正在卸料； 14：卸料完毕；
     */
    @Override
    public void updateInvoiceVehicleStatus(String compid, Integer id, Integer vehicleStatus) {
        driverMapper.updateInvoiceVehicleStatus(compid, id, vehicleStatus, new Date());
    }

    @Override
    @Transactional
    public Map<String, Object> updateVehicleStatus(String compid, String driverCode, Integer vehicleStatus) {
        //兼容老版本，老版本vehicleStatus传的是1
        if (vehicleStatus == 1) {
            vehicleStatus = 16;
        }

        Map<String, Object> map = new HashMap<>();
//          先判断这个小票的出场时间，满足以下两个条件时才修改车辆状态为自动回厂
//        1,当前时间比出场时间大于30分钟
//        2，当前车辆的状态时运输状态,或者是正在卸料,或者完成卸料的状态

        DriverTaskSaleDetailVO taskSaleInvoiceDetail = driverMapper.getTaskSaleInvoiceDetail(driverCode, compid);
        if (taskSaleInvoiceDetail == null || taskSaleInvoiceDetail.getVehicleStatus() == null) {
            map.put("code", 1);
            map.put("message", "司机没有打票，自动回厂失败");
            return map;
        }
        Date nowDate = new Date();
        //获取当前时间与离厂时间的分钟数插值
        long minute = (nowDate.getTime() - taskSaleInvoiceDetail.getLeaveTime().getTime()) / 1000 / 60;
        //如果当前时间距离出场时间大于30分钟,并且车辆状态为运输，等待卸料，完成卸料，则修改车辆状态为自动回厂
        if (minute > 30) {
            if (taskSaleInvoiceDetail.getVehicleStatus() == 2 || taskSaleInvoiceDetail.getVehicleStatus() == 12 ||
                    taskSaleInvoiceDetail.getVehicleStatus() == 13 || taskSaleInvoiceDetail.getVehicleStatus() == 14) {
                //修改小票表中的车辆状态为回厂待班。
                driverMapper.updateInvoiceVehicleStatus(compid, taskSaleInvoiceDetail.getId(), vehicleStatus, nowDate);
                //修改车辆表中的车辆状态为回厂待班。
                driverMapper.updateVehicleStatus(compid, taskSaleInvoiceDetail.getVehicleID(), vehicleStatus, nowDate);
                map.put("code", 0);
                map.put("message", "自动回厂成功");
            } else if (taskSaleInvoiceDetail.getVehicleStatus() == 16) {
                map.put("code", 1);
                map.put("message", "车辆已回厂");
            } else {
                map.put("code", 1);
                map.put("message", "车辆不是运输状态，自动回厂失败");
            }
        } else {
            map.put("code", 1);
            map.put("message", "出厂时间小于30分钟，自动回厂失败");
        }

        return map;
    }

    @Override
    public Map<String, Object> updateVehicleStatusByHand(String compid, String driverCode, Integer vehicleStatus) {
        Map<String, Object> map = new HashMap<>();

        DriverTaskSaleDetailVO taskSaleInvoiceDetail = driverMapper.getTaskSaleInvoiceDetail(driverCode, compid);
        if (taskSaleInvoiceDetail == null) {
            map.put("code", 1);
            map.put("message", "司机没有打票，自动回厂失败");
            return map;
        }
        if (taskSaleInvoiceDetail.getVehicleStatus() == 2) {
            Date nowDate = new Date();
            //修改小票表中的车辆状态为回厂待班。
            driverMapper.updateInvoiceVehicleStatus(compid, taskSaleInvoiceDetail.getId(), vehicleStatus, nowDate);
            //修改车辆表中的车辆状态为回厂待班。
            driverMapper.updateVehicleStatus(compid, taskSaleInvoiceDetail.getVehicleID(), vehicleStatus, nowDate);
            map.put("code", 0);
            map.put("message", "自动回厂成功");
        } else if (taskSaleInvoiceDetail.getVehicleStatus() == 16) {
            map.put("code", 1);
            map.put("message", "车辆已回厂");
        } else {
            map.put("code", 1);
            map.put("message", "车辆不是运输状态，自动回厂失败");
        }
        return map;
    }

    @Override
    public TaskSaleInvoiceSumVO getTaskSaleInvoiceSum(Integer invoiceId, String compid, String beginTime,
                                                      String endTime, String eppCode, Byte upStatus,
                                                      String builderCode, String placing,
                                                      Integer page, Integer pageSize, String driverCode) {
        return driverMapper.getTaskSaleInvoiceSum(invoiceId == null ? null : String.valueOf(invoiceId),
                compid, beginTime, endTime, eppCode, upStatus, builderCode, placing, driverCode);
    }

    /**
     * 司机App保存上下班打卡
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime   打卡时间
     * @param timeType   打卡类型  0:上班打卡    1：下班打卡
     * @param cardNumber 打卡次数
     */
    @Override
    public void saveDriverWorkTime(String compid, String driverCode, String workTime, Integer timeType, Integer cardNumber) {
        SimpleDateFormat dateFormat = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(new Date());

        if (timeType == 0) {
            //说明当天没有打卡记录
            if (cardNumber != 0) {
                cardNumber++;
            }
            driverMapper.saveDriverWorkTime(timeType, compid, driverCode, workTime, dateTime, cardNumber);
        } else {
            driverMapper.updateDriverWorkTime(timeType, compid, driverCode, workTime, dateTime, cardNumber);
        }
    }

    @Override
    public List<DriverWorkTimeVO> getDriverWorkTime(String compid, String driverCode, String dateTime) {
        return driverMapper.getDriverWorkTime(compid, driverCode, dateTime);
    }

    @Override
    public void driverOnlineStatus(String compid, String driverCode) {
        String key = compid + driverCode;
        //以该司机的公司代号和司机代号为key,把请求时间存进缓存中
        redisTemplate.opsForValue().set(key, new Date());
    }

    @Override
    public GpsLocateTempInfo getDriverLocation(String compid, String vehicleId) {
        return driverMapper.getDriverLocation(compid, vehicleId);
    }

    @Override
    public List<DriverVehicleCountVO> driverVehicleCount(String compid, String driverCode, String beginTime, String endTime) {
        return driverMapper.driverVehicleCount(compid, driverCode, beginTime, endTime);
    }

    @Override
    public void messagePush(String compid, MessagePushVO messagePushVO) throws ErpException {
        messagePushVO.setCompid(compid);
        //当前正在等待的车辆
        List<VehicleIdVO> waitCars = vehicleService.getWaitCars(compid);

        //获取缓存中等待生产的车辆
        List<VehicleIdVO> vehicleIdVOS = driverPushRedisTemplate.opsForValue().get("waitCars:" + compid);

        //先判断等待派车的车辆与缓存中的车辆是否一致，如果不一致，则发送消息提示
        if (vehicleIdVOS == null || vehicleIdVOS.size() < 1) {
            //缓存中等待派车的车辆为0辆
            for (int j = 0; j < waitCars.size(); j++) {
                //给每个查出来的等待派车的车辆发消息
                switch (j) {
                    case 0:
                        //给第一辆车发信息
                        firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                        break;
                    case 1:
                        //给第二辆车发信息
                        secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                        break;
                    case 2:
                        //给第三辆车发信息
                        thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
            }

        } else if (vehicleIdVOS.size() < 2) {
            //缓存中等待派车的车辆为1辆
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中第一辆车比较，如果不一样就给第一辆车辆发消息，其余等待派车的车辆直接发消息
                if (!waitCars.get(0).equals(vehicleIdVOS.get(0)) && j == 0) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j >= 1) {
                    switch (j) {
                        case 1:
                            //给第二辆车发信息
                            secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                            break;
                        case 2:
                            //给第三辆车发信息
                            thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                    }
                }

            }
        } else if (vehicleIdVOS.size() < 3) {
            //缓存中等待派车的车辆为2辆
            //跟缓存中前两辆车比较，如果不一样就给前两辆车辆发消息，其余等待派车的车辆直接发消息
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中第一辆车比较，如果不一样就给第一辆车辆发消息，其余等待派车的车辆直接发消息
                if (j == 0 && !waitCars.get(0).equals(vehicleIdVOS.get(0))) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j >= 1 && !waitCars.get(1).equals(vehicleIdVOS.get(1))) {
                    //给第二辆车发信息
                    secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
                if (j >= 2) {
                    //给第三辆车发信息
                    thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
            }
        } else {
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中所有三辆等待车辆比较，如果不一样就给发消息
                if (j == 0 && !waitCars.get(0).equals(vehicleIdVOS.get(0))) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j == 1 && !waitCars.get(1).equals(vehicleIdVOS.get(1))) {
                    //给第二辆车发信息
                    secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
                if (j == 2 && !waitCars.get(2).equals(vehicleIdVOS.get(2))) {
                    //给第三辆车发信息
                    thirdWaitCarPush(compid, messagePushVO, waitCars.get(2).getVehicleId());
                }
            }
        }

        //把新的等待派车的集合存进缓存中
        driverPushRedisTemplate.opsForValue().set("waitCars:" + compid, waitCars);
    }


    //获取司机代号
    private List<String> getDriverCode(String compid, String vehicleId) {
        return vehicleService.getDriverByVehicleId(compid, vehicleId);
    }


    //给第一辆等待生产的车辆发消息
    private void firstWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车辆即将生产，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }

    //给第二辆等待生产的车辆发消息
    private void secondWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车前面还有一辆等待车辆，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }


    //给第三辆等待生产的车辆发消息
    private void thirdWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车前面还有两辆等待车辆，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }

}
