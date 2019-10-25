package com.hntxrj.mapper;

import com.hntxrj.vo.BuilderDropDownVO;
import com.hntxrj.vo.DropDownVO;
import com.hntxrj.vo.PlacingDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 浇灌部位 mapper
 */
@Mapper
public interface PlacingMapper {

    List<PlacingDropDownVO> getPlacingDropDown(String compid, String placing);

}