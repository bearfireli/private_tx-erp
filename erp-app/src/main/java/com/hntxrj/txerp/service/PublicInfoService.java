package com.hntxrj.txerp.service;

import com.hntxrj.txerp.entity.base.PublicInfo;
import com.hntxrj.txerp.core.exception.ErpException;

import java.util.List;

/**
 * (PublicInfo)表服务接口
 *
 * @author lhr
 * @since 2018-08-14 13:19:07
 */
public interface PublicInfoService {

    PublicInfo addPublicInfo(PublicInfo publicInfo);

    PublicInfo updatePublicInfo(PublicInfo publicInfo);

    PublicInfo deletePublicInfo(Integer id) throws ErpException;

    List<PublicInfo> getPublicInfo(Integer fid, String name, Integer delete, Integer status);

    List<PublicInfo> getPublicInfoByFValue(String fValue);

}