package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.DriverVO;
import com.hntxrj.txerp.vo.TaskJumpVO;
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
}