package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.StirInfoSetVO;

import java.util.List;

public interface StirInfoSetService {
    /**
     * 楼号
     * @param compid  企业id
     */
    List<StirInfoSetVO> getStirInfoSet(String compid);
}
