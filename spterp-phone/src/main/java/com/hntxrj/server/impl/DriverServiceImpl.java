package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.DriverDao;
import com.hntxrj.mapper.DriverMapper;
import com.hntxrj.server.DriverService;
import com.hntxrj.vo.DriverVO;
import com.hntxrj.vo.TaskJumpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("driverName", driverMapper.getDriverName(compid, driverCode));
        return resultMap;
    }

    @Override
    public String getDriverNames(String compid, String driverCode) {
        String driverName = driverMapper.getDriverNames(compid,driverCode);
        return driverName;
    }


}
