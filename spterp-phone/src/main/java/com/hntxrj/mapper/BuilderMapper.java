package com.hntxrj.mapper;

import com.hntxrj.entity.BuilderInfo;
import com.hntxrj.vo.BuilderDropDownVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 施工单位 mapper
 */
@Mapper
public interface BuilderMapper {

    List<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName);

    BuilderInfo getBuilderInfo(String builderCode, String compid);
}
