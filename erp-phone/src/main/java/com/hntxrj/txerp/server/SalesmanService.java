package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.SalesmanDropDownVO;

public interface SalesmanService {

    PageVO<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid, Integer page, Integer pageSize);
}
