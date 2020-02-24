package com.hntxrj.txerp.server.impl;


import com.hntxrj.txerp.dao.PushInfoDao;
import com.hntxrj.txerp.server.PushInfoService;
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
public class PushInfoServiceImpl implements PushInfoService {

    private final PushInfoDao pushInfoDao;
    @Autowired
    public PushInfoServiceImpl(PushInfoDao pushInfoDao) {
        this.pushInfoDao = pushInfoDao;
    }


    @Override
    public boolean saveRecipient(List<String> nameList, String compId, int typeId) {

        for (String name:nameList
             ) {
            pushInfoDao.saveRecipient(name,compId,typeId);
        }

        return true;
    }
}
