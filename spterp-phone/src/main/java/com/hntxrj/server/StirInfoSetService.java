package com.hntxrj.server;

import com.hntxrj.vo.StirInfoSetVO;

import java.util.List;

public interface StirInfoSetService {
    /**
     * 楼号
     * @param compid  企业id
     */
    List<StirInfoSetVO> getStirInfoSet(String compid);
}
