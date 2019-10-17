package com.hntxrj.driver.services;

import com.hntxrj.driver.entity.EppAddress;

import java.util.List;

/**
 * 工程项目地址服务
 */
public interface EppAddressService {

    /**
     * 添加工程地址
     *
     * @param compid      企业id
     * @param eppCode     工程代号
     * @param address     地址
     * @param addressType 地址类型, 0手动添加，1获取签收位置
     */
    void addEppAddress(String compid, String eppCode, String address, Integer addressType, String userName);

    /**
     * 通过工程获取地址集合
     *
     * @param eppCode 工程代号
     * @param compid  企业id
     * @return 该工程的地址集合
     */
    List<EppAddress> getAddressByEpp(String eppCode, String compid);

    /**
     * 删除工程地址
     *
     * @param id 主键
     */
    void delEppAddress(Integer id);
}
