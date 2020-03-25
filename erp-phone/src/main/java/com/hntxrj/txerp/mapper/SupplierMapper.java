package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.SupplierVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {

    /**
     * 获取供应商下拉集合
     *
     * @param compid 企业id
     * @param supName  供应商名称
     */
    List<SupplierVO> getSupplierList(String compid,String supName);
}
