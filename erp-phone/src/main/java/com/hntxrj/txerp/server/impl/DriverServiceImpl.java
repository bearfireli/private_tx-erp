package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.DriverDao;
import com.hntxrj.txerp.mapper.DriverMapper;
import com.hntxrj.txerp.server.DriverService;
import com.hntxrj.txerp.vo.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能及介绍：司机模块数据处理层
 * <p>
 */
@Service()
public class DriverServiceImpl implements DriverService {

    private final DriverDao driverDao;
    private final DriverMapper driverMapper;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao, DriverMapper driverMapper) {
        this.driverDao = driverDao;
        this.driverMapper = driverMapper;
    }

    /**
     * 司机排班数据获取
     *
     * @param compId        企业id
     * @param stirId        线号
     * @param vehicleStatus 车辆状态
     * @param vehicleClass  班次
     * @return
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
    public void taskSaleInvoiceReceipt(String receiptPeople, Double receiptNum,
                                       String jumpVehicle, String sign, String invoiceId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (receiptPeople == null) {
            receiptPeople = "";
        }
        if (sign == null) {
            sign = "";
        }
        driverMapper.taskSaleInvoiceReceipt(receiptPeople, receiptNum, jumpVehicle == null ? "" : jumpVehicle,
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
        String driverName = driverMapper.getDriverNames(compid, driverCode);
        return driverName;
    }

    @Override
    public void getTaskSaleInvoiceReceiptSign(String taskSaleInvoiceUploadPath, String fileName, HttpServletResponse response) throws IOException {

        File file = new File(taskSaleInvoiceUploadPath + fileName);
        if (!file.exists()) {
            file = new File(taskSaleInvoiceUploadPath + "default.png");
        }
        try {
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    @Override
    public PageVO<TaskSaleInvoiceDriverListVO> getTaskSaleInvoiceList(Integer id, String compid, String beginTime,
                                                                      String endTime, String eppCode, Byte upStatus,
                                                                      String builderCode, String placing,
                                                                      Integer page,
                                                                      Integer pageSize, String driverCode) {
        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceDriverListVO> taskSaleInvoiceLists =
                driverMapper.driverGetTaskSaleInvoiceList(id, compid, beginTime,
                        endTime, eppCode, upStatus, builderCode, placing, driverCode);


        for (TaskSaleInvoiceDriverListVO taskSaleInvoice : taskSaleInvoiceLists) {
            //判断如果此小票是未签收,并且小票状态是完成生产，将车辆状态修改为开始卸料。
            if (taskSaleInvoice.getUpStatus() == 0 && taskSaleInvoice.getInvoiceType() == 3
                    && taskSaleInvoice.getVehicleStatus() != 13 && taskSaleInvoice.getVehicleStatus() != 14) {
                taskSaleInvoice.setVehicleStatus(17);
                taskSaleInvoice.setVehicleStatusName("开始卸料");
            }
        }

        PageInfo<TaskSaleInvoiceDriverListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        PageVO<TaskSaleInvoiceDriverListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetail(String compid, Integer id) {
        return driverMapper.driverGetTaskSaleInvoiceDetail(id, compid);
    }

    @Override
    public void saveSaleFileImage(String saleFileImage, String invoiceId, String compid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        driverMapper.saveSaleFileImage(compid, saleFileImage, invoiceId, dateFormat.format(new Date()));
    }

    @Override
    public void saveNumberOfSignings(String compid, Double receiptNum, String invoiceId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        driverMapper.saveNumberOfSignings(compid, receiptNum, invoiceId, dateFormat.format(new Date()));
    }

    @Override
    public void updateVehicleStatus(String compid, Integer id, Integer vehicleStatus) {
        driverMapper.updateVehicleStatus(compid, id, vehicleStatus);
    }

    @Override
    public TaskSaleInvoiceSumVO getTaskSaleInvoiceSum(String compid, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String placing, Integer page, Integer pageSize, String driverCode) {
        return driverMapper.getTaskSaleInvoiceSum(compid, beginTime,
                endTime, eppCode, upStatus, builderCode, placing, driverCode);

    }

    /**
     * 司机App保存上下班打卡
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime   打卡时间
     * @param timeType   打卡类型  0:上班打卡    1：下班打卡
     */
    @Override
    public void saveDriverWorkTime(String compid, String driverCode, String workTime, Integer timeType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(new Date());

        //先查询当前日期此司机有没有打卡记录。
        DriverWorkTimeVO driverWorkTimeVO = driverMapper.getDriverWorkTime(compid, driverCode, dateTime);
        if (driverWorkTimeVO == null) {
            //说明当天没有打卡记录
            driverMapper.saveDriverWorkTime(timeType, compid, driverCode, workTime, dateTime);
        } else {
            driverMapper.updateDriverWorkTime(timeType, compid, driverCode, workTime, dateTime);
        }
    }

    @Override
    public DriverWorkTimeVO getDriverWorkTime(String compid, String driverCode, String dateTime) {
        return driverMapper.getDriverWorkTime(compid, driverCode, dateTime);
    }


}
