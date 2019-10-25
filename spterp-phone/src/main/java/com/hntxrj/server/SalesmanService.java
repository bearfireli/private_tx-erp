package com.hntxrj.server;

import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.SalesmanDropDownVO;

public interface SalesmanService {

    PageVO<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid, Integer page, Integer pageSize);
}
