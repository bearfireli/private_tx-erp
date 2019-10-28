package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.DropDownVO;

import java.util.List;

public interface DropDownService {

    List<DropDownVO> getDropDown(Integer classId,String compid);

}
