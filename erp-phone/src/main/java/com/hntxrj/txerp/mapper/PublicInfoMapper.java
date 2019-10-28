package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.DropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicInfoMapper {

    List<DropDownVO> getDropDown(Integer classId, String compid);


}
