package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.sell.Engineering;
import com.hntxrj.txerp.vo.EngineeringVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EngineeringMapper {

    List<EngineeringVO> engineeringList(String engineeringCode, String engineeringName, String linkMan, Integer eid);


}
