package com.hntxrj.txerp.services;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.EppAddress;

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
     * @return 返回结果对象
     */
    EppAddress addEppAddress(String compid, String eppCode, String address, Integer addressType, String userName) throws ErpException;

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

    /**
     * 保存司机地址
     *
     * @param taskSaleInvoiceId 小票id
     * @param compid            企业id
     * @param location          位置信息
     * @param token             令牌
     */
    void saveDriverLocation(Integer taskSaleInvoiceId, String compid, String location, String token) throws ErpException;
}
