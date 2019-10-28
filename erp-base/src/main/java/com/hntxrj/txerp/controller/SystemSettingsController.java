package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.SystemSettingsService;
import com.hntxrj.txerp.entity.base.SystemSettings;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/settings")
public class SystemSettingsController {
    private final SystemSettingsService systemSettingsService;
    private ResultVO resultVO;

    @Autowired
    public SystemSettingsController(SystemSettingsService systemSettingsService) {
        this.systemSettingsService = systemSettingsService;
        this.resultVO = null;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
    }

    @PostMapping("/selectSystemsettings")
    public String selectSystemSettings(String token, String settingCode, Integer enterprise,
                                       @RequestParam(defaultValue = "1") long page,
                                       @RequestParam(defaultValue = "10") long pageSize) throws Exception {
        PageVO<SystemSettings> systemSettingsPageVO = systemSettingsService.selectSystemSettings(
                token, settingCode, enterprise, page, pageSize);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(systemSettingsPageVO)));
        System.out.println(resultVO);
        return JSON.toJSONString(resultVO);

    }

    @PostMapping("/addSystemsettings")
    public String addSystemsettings(SystemSettings systemSettings) throws Exception {
        systemSettingsService.addSystemSettings(systemSettings);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/updatesystemsettings")
    public String updatesystemsettings(SystemSettings systemSettings) throws Exception {
        systemSettingsService.updateSystemSettings(systemSettings);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/deletesystemsetting")
    public String deletesystemsetting(String settingCode) throws Exception {
        if (!(settingCode == null && StringUtils.isEmpty(settingCode))) {
            systemSettingsService.deleteSystemSetting(settingCode);
        } else {
            throw new ErpException(ErrEumn.SETTING_ENTERPRISE_NOT_NULL);
        }
        return JSON.toJSONString(resultVO);
    }
}
