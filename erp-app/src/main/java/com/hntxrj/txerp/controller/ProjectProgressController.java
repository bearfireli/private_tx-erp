package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.service.ProjectProgressService;
import com.hntxrj.txerp.entity.base.ProjectProgress;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projectprogress")
public class ProjectProgressController {

    private final ProjectProgressService projectProgressService;

    private ResultVO resultVO;

    @Autowired
    public ProjectProgressController(ProjectProgressService projectProgressService) {
        this.projectProgressService = projectProgressService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
    }


    @PostMapping("/saveProjectProgress")
    public String saveProjectProgress(ProjectProgress projectProgress) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(projectProgressService.saveProjectProgress(projectProgress))));

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/list")
    public String list(String projectName, long page, long pageSize) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(projectProgressService.list(projectName, page, pageSize))));

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/start")
    public String start(Integer ppId, String token) throws ErpException {
        projectProgressService.start(ppId, token);

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/finish")
    public String finish(Integer ppId, String token) throws ErpException {
        projectProgressService.finish(ppId, token);

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/check")
    public String check(Integer ppId, String token) throws ErpException {
        projectProgressService.check(ppId, token);

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/addSchedule")
    public String addSchedule(Integer ppId, String token, String msg) throws ErpException {
        projectProgressService.addSchedule(ppId, token, msg);

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/info")
    public String addSchedule(Integer ppId) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(projectProgressService.info(ppId))));

        return JSON.toJSONString(resultVO);
    }
}

