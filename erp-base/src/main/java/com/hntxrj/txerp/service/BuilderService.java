package com.hntxrj.txerp.service;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.entity.sell.Builder;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.BuilderVO;
import com.hntxrj.txerp.vo.PageVO;

import java.util.List;

public interface BuilderService {
    Builder saveBuilder(Builder build, String token, Integer enterprise) throws ErpException;

    Builder delBuilder(Integer id) throws ErpException;

    Builder getOne(Integer id) throws ErpException;

    PageVO<BuilderVO> findBuilder(String name, Integer eid, Integer page, Integer pageSize) throws ErpException;


    List<JSONObject> builderDownDrop(String builderName, Integer eid, Integer page, Integer pageSize) throws ErpException;
}
