package com.hntxrj.txerp.controller;


import com.alibaba.fastjson.JSON;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.mapper.PublicInfoMapper;
import com.hntxrj.txerp.service.PublicInfoService;
import com.hntxrj.txerp.entity.base.PublicInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping({"/v1/pi", "/pi"})
@Slf4j
@Validated
public class PublicInfoControllerV1 {


    private final PublicInfoService publicInfoService;


    private ResultVO resultVO;

    @Autowired
    public PublicInfoControllerV1(PublicInfoService publicInfoService) {
        this.publicInfoService = publicInfoService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData("");
    }

    @PostMapping("/add")
    public String add(PublicInfo publicInfo) {
        log.info("【添加公共信息】publicInfo={}", publicInfo);
        resultVO.setData(
                JSON.toJSONString(publicInfoService.addPublicInfo(publicInfo)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/update")
    public String update(PublicInfo publicInfo) {
        log.info("【修改公共信息】publicInfo={}", publicInfo);
        resultVO.setData(
                JSON.toJSONString(publicInfoService.updatePublicInfo(publicInfo)));
        return JSON.toJSONString(resultVO);
    }

    @RequestMapping("/delete")
    public String delete(Integer id) throws ErpException {
        log.info("【删除eid】id={}", id);
        resultVO.setData(
                JSON.toJSONString(publicInfoService.deletePublicInfo(id)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/select")
    public String select(Integer fid, @RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) Integer delete) {
        log.info("【查询公共信息】fid={}，status={}， delete={}", fid, status, delete);

        resultVO.setData(
                JSON.toJSONString(publicInfoService.getPublicInfo(fid, name, delete, status))
        );
        return JSON.toJSONString(resultVO);
    }


    @RequestMapping("/dropDown")
    public ResultVO dropDown(String fVal) {
        resultVO.setData(publicInfoService.getPublicInfoByFValue(fVal));
        return resultVO;
    }

}
