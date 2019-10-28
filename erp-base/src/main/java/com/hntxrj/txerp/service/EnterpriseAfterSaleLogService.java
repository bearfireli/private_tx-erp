package com.hntxrj.txerp.service;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.base.EnterpriseAfterSaleLog;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.EnterpriseAfterSaleLogVO;
import com.hntxrj.txerp.vo.PageVO;

public interface EnterpriseAfterSaleLogService {

    PageVO<EnterpriseAfterSaleLogVO> enterpriseAfterSaleLogList(Integer enterprise, long page, long pageSize);

    EnterpriseAfterSaleLog saveEnterpriseAfterSaleLog(EnterpriseAfterSaleLog enterpriseAfterSaleLog, String token)
            throws ErpException;


    EnterpriseAfterSaleLogVO enterpriseAfterSaleLogInfo(Integer logId, String token) throws ErpException;

    void uploadFile(JSONArray files, Integer logId) throws ErpException;

    /**
     * 完成售后
     *
     * @param token          完成人token
     * @param processingType 售后类型
     * @param sumup          售后因果
     * @return 售后对象
     */
    EnterpriseAfterSaleLog overAfterSale(String token, String processingType, String sumup, Integer logId) throws ErpException;

    /**
     * 开始售后
     *
     * @param logId 日志id
     * @param token 接受售后用户id
     */
    void acceptTask(String token, Integer logId) throws ErpException;

}
