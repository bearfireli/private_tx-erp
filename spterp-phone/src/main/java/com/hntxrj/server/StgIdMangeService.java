package com.hntxrj.server;

import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.StgidManageVO;

public interface StgIdMangeService {
    /**
     * 砼标号管理
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @param page    当前页
     * @param pageSize  每页显示数
     * @return  砼标号管理
     */
    PageVO<StgidManageVO> getStgidManage(String compid, String stgId, String grade, Integer page, Integer pageSize);
}
