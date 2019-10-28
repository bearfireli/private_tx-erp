package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.PlacingDropDownVO;


/**
 */
public interface PlacingService {


    PageVO<PlacingDropDownVO> getPlacingDropDown(String compid, String builderName, Integer page, Integer pageSize);
}