package com.hntxrj.txerp.controller;


import com.alibaba.fastjson.JSON;
import com.hntxrj.txerp.service.MenuService;
import com.hntxrj.txerp.core.web.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/menu/")
@Validated
public class MenuControllerV1 {

    private final MenuService menuService;
    private ResultVO resultVO;

    @Autowired
    public MenuControllerV1(MenuService menuService) {
        this.menuService = menuService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getMenuList(Integer enterprise, Integer status) {
        resultVO.setData(JSON.toJSONString(menuService.getMenuTree(enterprise, status)));
        return JSON.toJSONString(resultVO);
    }

    @RequestMapping(value = "/functionmenulist", method = RequestMethod.POST)
    public String getFunctionMenuList() {
        resultVO.setData(JSON.toJSONString(menuService.getFunctionMenuList()));
        return JSON.toJSONString(resultVO);
    }
}
