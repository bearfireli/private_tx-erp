package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.PublicInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicInfoMapper {

    List<PublicInfo> findPublicInfo(Integer fid, Integer status, Integer del, String name);

    List<PublicInfo> findPublicInfoByFValue(String val);
}
