package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.DropDownVO;
import com.hntxrj.txerp.vo.QueryTimeSetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicInfoMapper {

    List<DropDownVO> getDropDown(Integer classId, String compid);

    //从DD_QueryTimeSet表中查询用户设置的查询时间
    QueryTimeSetVO getSystemQueryTime(String compid, int queryCode, int queryType);


}
