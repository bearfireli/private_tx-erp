package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.EppInfo;
import com.hntxrj.txerp.vo.EppDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EppMapper {

    List<EppDropDownVO> getDropDown(String compid, String eppName);
    EppInfo getEppInfo(String eppCode, String compid);

    List<EppDropDownVO> getBuildDropDown(List<String> contractDetailCodes, List<String> contractUIDList, String eppName);
}
