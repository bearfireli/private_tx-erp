package com.hntxrj.txerp.services.impl;

import com.hntxrj.txerp.entity.DriverLocation;
import com.hntxrj.txerp.entity.EppAddress;
import com.hntxrj.txerp.mapper.EppAddressMapper;
import com.hntxrj.txerp.repository.EppAddressRepository;
import com.hntxrj.txerp.services.EppAddressService;
import com.hntxrj.txerp.services.TaskSaleInvoiceService;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class EppAddressServiceImpl implements EppAddressService {

    private final EppAddressMapper eppAddressMapper;
    private final EppAddressRepository eppAddressRepository;

    private final TaskSaleInvoiceService taskSaleInvoiceService;

    @Autowired
    public EppAddressServiceImpl(EppAddressMapper eppAddressMapper, EppAddressRepository eppAddressRepository, TaskSaleInvoiceService taskSaleInvoiceService) {
        this.eppAddressMapper = eppAddressMapper;
        this.eppAddressRepository = eppAddressRepository;
        this.taskSaleInvoiceService = taskSaleInvoiceService;
    }


    @Override
    public void addEppAddress(String compid, String eppCode, String address, Integer addressType, String userName) {
        EppAddress eppAddress = new EppAddress();
        eppAddress.setCompid(compid);
        eppAddress.setAddress(address);
        //TODO: 位置检测，如果太近不存储
        eppAddress.setAddressType(addressType);
        eppAddress.setCreateTime(new Timestamp(new Date().getTime()));
        eppAddress.setEppCode(eppCode);
        eppAddress.setCreateUser(userName);
        eppAddress.setOrder(eppAddressMapper.getCountByEppCode(compid, eppCode));
        eppAddress.setStatus(true);
        eppAddressRepository.save(eppAddress);
    }

    @Override
    public List<EppAddress> getAddressByEpp(String eppCode, String compid) {
        return eppAddressMapper.getEppAddress(compid, eppCode);
    }

    @Override
    public void delEppAddress(Integer id) {
        EppAddress address = eppAddressRepository.getOne(id);
        address.setStatus(false);
    }


    @Override
    public void saveDriverLocation(Integer id, String compid, String location, String token) {
        DriverLocation driverLocation = new DriverLocation();
        driverLocation.setDlTsiId(id);
        driverLocation.setAddress(location);

        // 查小票
        TaskSaleInvoiceDetailVO taskSaleInvoiceDetailVO = taskSaleInvoiceService.getTaskSaleInvoice(id, compid);

        driverLocation.setDlCarId(taskSaleInvoiceDetailVO.getVehicleID());
        driverLocation.setDlDriverCode(taskSaleInvoiceDetailVO.getPersonalCode());
        driverLocation.setDlEppCode(taskSaleInvoiceDetailVO.getEppCode());
    }

}
