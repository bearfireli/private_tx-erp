package com.hntxrj.driver.services.impl;

import com.hntxrj.driver.entity.EppAddress;
import com.hntxrj.driver.mapper.EppAddressMapper;
import com.hntxrj.driver.repository.EppAddressRepository;
import com.hntxrj.driver.services.EppAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class EppAddressServiceImpl implements EppAddressService {

    private final EppAddressMapper eppAddressMapper;
    private final EppAddressRepository eppAddressRepository;

    @Autowired
    public EppAddressServiceImpl(EppAddressMapper eppAddressMapper, EppAddressRepository eppAddressRepository) {
        this.eppAddressMapper = eppAddressMapper;
        this.eppAddressRepository = eppAddressRepository;
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
}
