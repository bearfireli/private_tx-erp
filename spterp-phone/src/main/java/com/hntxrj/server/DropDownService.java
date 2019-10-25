package com.hntxrj.server;

import com.hntxrj.vo.DropDownVO;

import java.util.List;

public interface DropDownService {

    List<DropDownVO> getDropDown(Integer classId,String compid);

}
