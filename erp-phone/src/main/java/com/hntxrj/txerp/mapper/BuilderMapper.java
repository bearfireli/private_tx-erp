package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.vo.BuilderDropDownVO;
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
