package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.StirInfoSetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StirInfoSetMapper {

    /**
     * 楼号
     * @param compid
     */
    List<StirInfoSetVO> getStirInfoSet(String compid);

}
