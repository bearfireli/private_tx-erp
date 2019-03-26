package com.hntxrj.txerp.service;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectDropDownVO;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.Project;

import java.util.List;

public interface ProjectService {

    /**
     * 获取项目下拉列表
     *
     * @param projectName 模糊查询项目名
     * @return 项目下拉列表
     */
    List<ProjectDropDownVO> getProjectDropDown(String projectName);

    /**
     * 获取项目下拉列表
     *
     * @return 项目下拉列表
     */
    List<ProjectDropDownVO> getProjectDropDown();


    /**
     * 获取项目列表
     *
     * @param token       用户令牌
     * @param projectName 项目名称模糊查询
     * @param status      项目状态
     * @param page        页码
     * @param pageSize    每页数量
     * @return 项目列表
     */
    PageVO<Project> getProjectList(String token, String projectName, Integer status,
                                   long page, long pageSize) throws ErpException;

    Project addProject(Project project);


    Project updateProject(Project project);


    void deleteProject(Integer pid) throws ErpException;


}
