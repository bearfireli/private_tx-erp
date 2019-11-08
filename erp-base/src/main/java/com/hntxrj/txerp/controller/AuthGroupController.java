package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.hntxrj.txerp.enums.AuthGroupEnums;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.AuthGroup;
import com.hntxrj.txerp.entity.base.Menu;
import com.hntxrj.txerp.service.AuthGroupService;
import com.hntxrj.txerp.service.MenuService;
import com.hntxrj.txerp.vo.MenuListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthGroupController {


    private final AuthGroupService authGroupService;
    private final MenuService menuService;
    private ResultVO resultVO;

    @Autowired
    public AuthGroupController(AuthGroupService authGroupService, MenuService menuService) {
        this.menuService = menuService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
        this.authGroupService = authGroupService;
    }


    @PostMapping("/isPermission")
    public String isPermission(String token, String uri, Integer enterprise) throws ErpException {
        if (authGroupService.isPermission(token, enterprise, uri)) {
            return JSON.toJSONString(resultVO);
        } else {
            throw new ErpException(ErrEumn.NOT_PERMISSION);
        }
    }

    @PostMapping("/getAuthGroupDropDown")
    public String getAuthGroupDropDown(Integer enterprise) throws ErpException {
        resultVO.setData(JSON.toJSONString(
                authGroupService.getAuthGroupDropDown(enterprise)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getMenuList")
    public String getMenuList(Integer enterprise) {
        resultVO.setData(JSON.toJSONString(menuService.getMenuTree(enterprise)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getMenuListByProject")
    public String getMenuListByProject(Integer pid) {
        resultVO.setData(JSON.toJSONString(menuService.getMenuTreeByProject(pid)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getMenu")
    public String getMenu(Integer mid) {
        resultVO.setData(JSON.toJSONString(menuService.getMenuById(mid)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveMenu")
    public String saveMenu(Menu menu) {
        menu.setCreateTime(null);
        menu.setUpdateTime(null);
        log.info("【保存菜单】menu={}", menu);
        resultVO.setData(JSON.toJSONString(menuService.saveMenu(menu)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getAuthGroup")
    public String getAuthGroup(
            String token,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String agName,
            @RequestParam(required = false, defaultValue = "0") Integer agStatus,
            @RequestParam Integer enterpriseId) throws ErpException {
        resultVO.setData(JSON.toJSONString(authGroupService
                .selectAuthGroup(token, page, pageSize, agName, agStatus, enterpriseId)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/editAuthGroup")
    public String editAuthGroup(AuthGroup authGroup)
            throws ErpException {

        if (authGroup == null) {
            throw new ErpException(ErrEumn.AUTH_GROUP_NULL);
        }
        if (authGroup.getAgName() == null ||
                "".equals(authGroup.getAgName())) {
            throw new ErpException(ErrEumn.AUTH_GROUP_NAME_NULL);
        }
        if (authGroup.getAgStatus() == null) {
            authGroup.setAgStatus(AuthGroupEnums.USE.getCode());
        }
        if (authGroup.getEnterprise() == null) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }
        if (authGroup.getUpdateUser() == null) {
            throw new ErpException(ErrEumn.UPDATEUSER_NULL);
        }


        resultVO.setData(JSON.toJSONString(
                authGroupService.editAuthGroup(authGroup)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/getAuthGroups")
    public String getAuthGroups(String token, Integer enterprise) throws ErpException {

        if (token == null || token.equals("")) {
            throw new ErpException(ErrEumn.TOKEN_IS_NULL);
        }

        List<AuthGroup> authGroupList =
                authGroupService.getAuthGroup(token, enterprise);

        resultVO.setData(JSON.toJSONString(authGroupList));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getAuthValue")
    public String getAuthValue(Integer groupId) {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("openAuth", JSON.toJSONString(authGroupService.getOpenAuth(groupId)));
        mapData.put("authValue", JSON.toJSONString(authGroupService.getAuthValue(groupId)));
        resultVO.setData(JSON.toJSONString(mapData));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/openAuth")
    public String getOpenAuth(Integer groupId) {
        resultVO.setData(JSON.toJSONString(authGroupService.getOpenAuth(groupId)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveAuthValue")
    public String saveAuthValue(String authValues, Integer groupId, Integer pid, String token) throws ErpException {

        String[] avs;
        if (authValues != null) {
            avs = authValues.split("\\|");
            List<String> funNames = Arrays.asList(new String[avs.length]);
            int i = 0;
            for (String funName : avs) {
                if (!funName.equals("")) {
                    funNames.set(i, funName);
                    i++;
                }
            }
            Map<String, String> mapData = new HashMap<>();

            mapData.put("openAuth", JSON.toJSONString(
                    authGroupService.getOpenAuth(groupId)));
            mapData.put("authValue", JSON.toJSONString(
                    authGroupService.saveAuthValue(funNames, groupId, token, pid)));

        } else {
            resultVO.setData(JSON.toJSONString(new ArrayList<>()));
        }



        return JSON.toJSONString(resultVO);
    }


}
