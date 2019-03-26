package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.service.SystemLogService;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.system.SystemLog;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.core.web.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class SystemLogController {

    private final SystemLogService systemLogService;
    private ResultVO resultVO;

    @Autowired
    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
        this.resultVO = null;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
    }

    @PostMapping("/system")
    public String system(String action, String uri, Integer enterprise) throws ErpException {
        systemLogService.system(action, uri, enterprise);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/operate")
    public String operate(Integer uid, String action, String uri, Integer enterprise) throws Exception {
        systemLogService.operate(uid, action, uri, enterprise);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/select")
    public String select(Integer uid, String action, String uri, Integer enterprise) throws Exception {
        systemLogService.select(uid, action, uri, enterprise);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/list")
    public String list(Integer uid, Integer enterprise,
                       String uri,
                       @RequestParam(defaultValue = "1") long page,
                       @RequestParam(defaultValue = "10") long pageSize) {
        PageVO<SystemLog> list = systemLogService.list(uid, enterprise, uri, page, pageSize);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(list)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/cleanAllLog")
    public String cleanAllLog(Integer enterprise) throws Exception {
        if (enterprise == 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST);
        }
        systemLogService.cleanAllLog(enterprise);
        return JSON.toJSONString(resultVO);

    }

    @PostMapping("/cleanAllLogByType")
    public String cleanAllLogByType(Integer enterprise, Integer type) throws Exception {
        if (enterprise == 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST);
        }
        if (type == 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_TYPE_NOTEXIST);
        }
        systemLogService.cleanAllLogByType(enterprise, type);
        return JSON.toJSONString(resultVO);
    }

}
