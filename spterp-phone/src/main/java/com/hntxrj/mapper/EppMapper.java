package com.hntxrj.mapper;

import com.hntxrj.entity.EppInfo;
import com.hntxrj.vo.EppDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EppMapper {

    List<EppDropDownVO> getDropDown(String compid, String eppName);
    EppInfo getEppInfo(String eppCode, String compid);

}
