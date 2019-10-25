package com.hntxrj.server;

import com.hntxrj.vo.BuilderDropDownVO;
import com.hntxrj.vo.DropDownVO;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.PlacingDropDownVO;


/**
 */
public interface PlacingService {


    PageVO<PlacingDropDownVO> getPlacingDropDown(String compid, String builderName, Integer page, Integer pageSize);
}