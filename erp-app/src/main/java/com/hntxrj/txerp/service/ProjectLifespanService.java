package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectLifespanVO;

import java.util.Date;

/**
 * (ProjectLifespan)表服务接口
 *
 * @author zzlhr
 * @since 2018-10-15 09:35:24
 */
public interface ProjectLifespanService {

    /**
     * 获取到期时间
     *
     * @param pid   项目id
     * @param token 令牌
     * @return 到期时间
     */
    Date getExpireTime(Integer pid, String token, Integer enterprise) throws ErpException;

    ProjectLifespanVO saveProjectLifespan(String token, ProjectLifespan projectLifespan) throws ErpException;

    void delProjectLifespan(String token, Integer enterpriseId, Integer pid) throws ErpException;

    PageVO<ProjectLifespanVO> list(String token, Integer enterpriseId, long page, long pageSize) throws ErpException;
}