package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.EnterpriseVO;
import com.hntxrj.txerp.vo.ResultVO;

public interface EnterpriseService {

    /**
     * 设置搅拌站地址坐标
     *
     * @param compid   企业
     * @param address  地址坐标
     * @param opid     操作员代号
     *
     * */
    void setEnterpriseAddress(String compid, String address, String opid);


    /**
     * 获取搅拌站地址坐标（经纬度）
     *
     * @param compid   企业
     * */
    EnterpriseVO getEnterpriseAddress(String compid);
}
