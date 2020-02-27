package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.DropDownVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.StockVO;
import com.hntxrj.txerp.vo.SupplierVO;

import java.util.List;

public interface DropDownService {

    /**
     * 获取公共下拉
     *
     * @param classId 查询类型
     * @param compid  企业id
     */
    List<DropDownVO> getDropDown(Integer classId,String compid);

    /**
     * 获取供应商下拉集合
     *
     * @param compid 企业id
     */
    PageVO<SupplierVO> getSupplierList(String compid, Integer page, Integer pageSize);

    /**
     * 获取库位下拉集合
     *
     * @param compid 企业id
     */
    PageVO<StockVO> getStockList(String compid,Integer page,Integer pageSize);
}
