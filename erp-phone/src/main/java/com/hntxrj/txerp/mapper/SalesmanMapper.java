package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.SalesmanDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SalesmanMapper {

    /**
     * 获取销售员下拉
     *
     * @param salesName 销售员名称
     * @param compid    企业id
     * @return 销售员下拉对象
     */
    List<SalesmanDropDownVO> getSalesmanDropDown(String salesName, String compid);


    /**
     * 获取业务员的分组
     *
     * @param compid 企业id
     * @return 业务员对象集合
     */
    List<SalesmanDropDownVO> getSaleGroup(String compid);

    /**
     * 查询所有的业务员编号
     *
     * @param compid 企业id
     * @return 业务员编号集合
     */
    List<String> selectSaleCodes(String compid);

    /**
     * 添加业务员
     *
     * @param compid      企业id
     * @param saleName    销售员姓名
     * @param saleManCode 销售员编号
     * @param department  部门分组
     */
    void addSaleMan(String compid, String saleName, String saleManCode, String department);

    Map<String,String> getSaleMan(String compid, String saleManCode);

}
