package com.hntxrj.txerp.service.impl;


import com.hntxrj.txerp.mapper.TankMapper;
import com.hntxrj.txerp.service.TankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;


/**
 * @author nsk
 * @create 2020/2/26
 */
@Service
@Scope("prototype")
public class TankServiceImpl implements TankService {

    private final TankMapper tankMapper;
    @Autowired
    public TankServiceImpl(TankMapper tankMapper) {
        this.tankMapper = tankMapper;
    }

    @Override
    public void powderTanDeviceList() {
        tankMapper.powderTanDeviceList();
    }
}
