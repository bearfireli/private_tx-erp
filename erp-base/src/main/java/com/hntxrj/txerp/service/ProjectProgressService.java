package com.hntxrj.txerp.service;

import com.hntxrj.txerp.entity.base.ProjectProgress;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectProgressVO;

public interface ProjectProgressService {

    ProjectProgress saveProjectProgress(ProjectProgress projectProgress) throws ErpException;

    PageVO<ProjectProgressVO> list(String projectName, long page, long pageSize);

    ProjectProgressVO info(Integer ppId);

    void start(Integer ppId, String token) throws ErpException;

    /**
     * 完成项目
     * 只有项目负责人才能提交完成
     *
     * @param ppId  完成项目id
     * @param token 项目负责人token
     */
    void finish(Integer ppId, String token) throws ErpException;

    /**
     * 验收项目
     * 只有项目验收人才能验收项目
     *
     * @param ppId  项目id
     * @param token 验收人token
     */
    void check(Integer ppId, String token) throws ErpException;

    /**
     * 添加项目进度
     *
     * @param ppId  项目id
     * @param token 添加人id
     * @param msg   添加的内容
     */
    void addSchedule(Integer ppId, String token, String msg) throws ErpException;
}
