package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.QueryTimeSetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryTimeSetMapper {
    List<QueryTimeSetVO> getQueryTimeSetList(String compid);
}
