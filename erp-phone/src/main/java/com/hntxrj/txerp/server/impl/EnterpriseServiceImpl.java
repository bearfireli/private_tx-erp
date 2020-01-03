package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.mapper.EnterpriseMapper;
import com.hntxrj.txerp.server.EnterpriseService;
import com.hntxrj.txerp.vo.EnterpriseVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("prototype")
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;

    @Autowired
    public EnterpriseServiceImpl(EnterpriseMapper enterpriseMapper) {
        this.enterpriseMapper = enterpriseMapper;
    }

    /**
     * 设置搅拌站地址坐标
     *
     * @param compid   企业
     * @param address  地址坐标
     * @param opid     操作员代号
     * */
    @Override
    public void setEnterpriseAddress(String compid, String address, String opid) {
        EnterpriseVO enterpriseVO= enterpriseMapper.selectEnterpriseAddress(compid);
        if (enterpriseVO != null) {
            enterpriseMapper.updateEnterpriseAddress(compid, address, opid, new Date());
        }
    }


    /**
     * 获取搅拌站地址坐标
     *
     * @param compid   企业
     * */
    @Override
    public EnterpriseVO getEnterpriseAddress(String compid) {

        EnterpriseVO enterpriseVO = enterpriseMapper.selectEnterpriseAddress(compid);
        String addr = enterpriseVO.getAddr();
        //如果企业没有设置搅拌站位置，则addr返回null
        if (!addr.startsWith("{")) {
            enterpriseVO.setAddr(null);
        }

        return enterpriseVO;
    }
}
