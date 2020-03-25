package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.SalesmanDropDownVO;

import java.util.List;

public interface SalesmanService {

    /**
     * 获取销售员下拉
     *
     * @param salesName 销售员名称
     * @param compid    企业id
     * @param page      分页
     * @param pageSize  每页数量
     * @return 销售员下拉对象
     */
    PageVO<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid, Integer page, Integer pageSize);


    /**
     * 获取业务员的分组
     *
     * @param compid 企业id
     * @param page      分页
     * @param pageSize  每页数量
     */
    PageVO<SalesmanDropDownVO> getSaleGroup(String compid,Integer page, Integer pageSize);

    /**
     * 添加销售员
     *
     * @param compid   企业id
     * @param saleName 销售员姓名
     * @param department 销售员部门分组
     * */
    void addSaleMan(String compid, String saleName, String department);
}
