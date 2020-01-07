package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.EnterpriseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface EnterpriseMapper {

    /**
     * 获取搅拌站地址坐标
     *
     * @param compid 企业
     * @return 公司地址
     */
    EnterpriseVO selectEnterpriseAddress(String compid);

    /**
     * 设置搅拌站地址坐标
     *
     * @param compid     企业
     * @param address    地址坐标
     * @param opid       操作员代号
     * @param updateTime 修改时间
     */
    void updateEnterpriseAddress(String compid, String address, String opid, Date updateTime);
}
