package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriverMapper {

    List<DriverVO> getDriverList(String compid, String driverName);

    List<TaskJumpVO> getJumpVehicleList(String compid, String token, String taskId);

    void taskSaleInvoiceReceipt(String receiptPeople, Double receiptNum,
                                String jumpVehicle, String sign, String invoiceId, String signingTime);

    String getDriverName(String compid, String driverCode);


    String getDriverNames(String compid, String driverCode);

    TaskSaleInvoiceDetailVO driverGetTaskSaleInvoiceDetail(Integer id, String compid);

    List<TaskSaleInvoiceDriverListVO> driverGetTaskSaleInvoiceList(Integer id,String compid, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String placing, String driverCode);


    TaskSaleInvoiceSumVO getTaskSaleInvoiceSum(String compid, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String placing, String driverCode);

    void saveDriverWorkTime(Integer timeType, String compid, String driverCode, String workTime, String dateTime);

    DriverWorkTimeVO getDriverWorkTime(String compid, String driverCode, String dateTime);

    void updateDriverWorkTime(Integer timeType, String compid, String driverCode, String workTime, String dateTime);
}
