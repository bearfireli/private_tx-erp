package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.PlacingDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 浇灌部位 mapper
 */
@Mapper
public interface PlacingMapper {

    List<PlacingDropDownVO> getPlacingDropDown(String compid, String placing);

}