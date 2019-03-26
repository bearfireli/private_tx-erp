package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.sell.Engineering;
import com.hntxrj.txerp.vo.EngineeringVO;
import com.hntxrj.txerp.vo.PageVO;

import java.util.List;

/**
 * 工程服务
 *
 * @author haoran liu
 */
public interface EngineeringService {

    /**
     * 添加多个工程
     *
     * @param engineerings 工程对象集合
     * @return
     */
    List<Engineering> addList(List<Engineering> engineerings);

    PageVO<EngineeringVO> select(String engineeringCode, String engineeringName, String userToken,
                                 String linkMan, Integer enterprise, Integer page, Integer pageSize)
            throws ErpException;


    Engineering save(Engineering Engineering, String token) throws ErpException;

    Engineering findById(Integer id) throws ErpException;

    PageVO<Engineering> engineeringDownDrop(Integer eid, String engineeringName, Integer page, Integer pageSize)
            throws ErpException;
}
