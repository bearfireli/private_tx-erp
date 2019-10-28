package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.mapper.StirInfoSetMapper;
import com.hntxrj.txerp.server.StirInfoSetService;
import com.hntxrj.txerp.vo.StirInfoSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qyb
 *  BuildingNumberServiceImpl
 *  TODO
 *  19-6-5 下午7:03
 **/
@Service
@Scope("prototype")
public class StirInfoSetServiceImpl implements StirInfoSetService {

    private final StirInfoSetMapper stirInfoSetMapper;

    @Autowired
    public StirInfoSetServiceImpl(StirInfoSetMapper stirInfoSetMapper) {
        this.stirInfoSetMapper = stirInfoSetMapper;
    }

    /**
     * 楼号
     * @param compid  企业id
     */
    @Override
    public List<StirInfoSetVO> getStirInfoSet(String compid) {

        return stirInfoSetMapper.getStirInfoSet(compid);
    }
}
