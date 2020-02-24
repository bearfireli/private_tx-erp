package com.hntxrj.txerp.server.impl;


import com.hntxrj.txerp.dao.PushTypeDao;
import com.hntxrj.txerp.dao.UserEmployeeDao;
import com.hntxrj.txerp.entity.PushType;
import com.hntxrj.txerp.server.PushTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能:   推送类型服务层实现类
 *
 * @Auther 陈世强
 */
@Service
@Scope("prototype")
public class PushTypeServiceImpl implements PushTypeService {

    private final PushTypeDao pushTypeDao;
    @Autowired
    public PushTypeServiceImpl(PushTypeDao pushTypeDao) {
        this.pushTypeDao = pushTypeDao;
    }


    @Override
    public List<PushType> getPushType() {
        return pushTypeDao.getPushType();
    }
}
