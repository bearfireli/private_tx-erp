package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.vo.EnterpriseInformationVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnterpriseMapper {
    int updateId(Enterprise enterpriseOld, Integer id);

    //手机erp获取企业信息
    EnterpriseInformationVO getEnterpriseInformation(Integer eid);

    //手机erp修改企业信息
    void updateEnterpriseInformation(Integer eid, String epName, String epShortName, String logoUrl);
}
