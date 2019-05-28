package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.service.ProjectLifespanService;
import com.hntxrj.txerp.service.ProjectService;
import com.hntxrj.txerp.entity.base.Project;
import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/project")
@Validated
public class ProjectControllerV1 {

    private ResultVO resultVO;

    private final ProjectService projectService;
    private final ProjectLifespanService projectLifespanService;

    @Autowired
    public ProjectControllerV1(ProjectService projectService, ProjectLifespanService projectLifespanService) {
        this.projectService = projectService;
        this.projectLifespanService = projectLifespanService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
    }

    @PostMapping("/dropDown")
    public String dropDown(@RequestParam(defaultValue = "") String name) {
        resultVO.setData(JSON.toJSONString(
                projectService.getProjectDropDown(name)));

        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/list")
    public String list(
            String token,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long pageSize) throws ErpException {
        resultVO.setData(JSON.toJSONString(projectService.getProjectList(
                token, projectName, status, page, pageSize)));

        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/add")
    public String add(Project project) {
        resultVO.setData(JSON.toJSONString(projectService.addProject(project)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/update")
    public String update(Project project) {
        resultVO.setData(JSON.toJSONString(projectService.updateProject(project)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/del")
    public String del(Integer pid) throws ErpException {
        projectService.deleteProject(pid);
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/getExpireTime")
    public String getExpireTime(Integer pid, @RequestHeader Integer enterprise, String token) throws ErpException {
        JSONObject result = new JSONObject();
        result.put("expireTime", projectLifespanService.getExpireTime(pid, token, enterprise));
        resultVO.setData(result);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveProjectLifespan")
    public String saveProjectLifespan(String token, ProjectLifespan projectLifespan) throws ErpException {
        System.out.println(projectLifespan);
        resultVO.setData(projectLifespanService
                .saveProjectLifespan(token, projectLifespan));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/delProjectLifespan")
    public String delProjectLifespan(String token,Integer enterpriseId, Integer pid) throws ErpException {
        projectLifespanService
                .delProjectLifespan(token, enterpriseId, pid);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/projectLifespanList")
    public String projectLifespanList(String token, Integer enterpriseId, long page, long pageSize) throws ErpException {
        resultVO.setData(projectLifespanService
                .list(token, enterpriseId, page, pageSize));
        return JSON.toJSONString(resultVO);
    }

}
