package com.hntxrj.txerp.service;

import com.hntxrj.txerp.entity.system.SystemLog;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;


public interface SystemLogService {

    void system(String action, String uri, Integer enterprise) throws ErpException;

    void operate(Integer uid, String action, String uri, Integer enterprise) throws ErpException;

    void select(Integer uid, String action, String uri, Integer enterprise) throws ErpException;

    /**
     * 查询日志
     *
     * @param uid        用户id
     * @param enterprise @NotNull
     * @param uri        链接
     */
    PageVO<SystemLog> list(Integer uid, Integer enterprise, String uri, long page, long pageSize);


    void cleanAllLog(Integer enterprise);

    void cleanAllLogByType(Integer enterprise, Integer type);

}
