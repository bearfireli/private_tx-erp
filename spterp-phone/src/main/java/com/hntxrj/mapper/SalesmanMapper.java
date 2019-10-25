package com.hntxrj.mapper;

import com.hntxrj.vo.SalesmanDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesmanMapper {

    List<SalesmanDropDownVO> getSalesmanDropDown(String salesMan, String compid, Integer page, Integer pageSize);

}
