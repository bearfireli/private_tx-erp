package com.hntxrj.txerp.controller.base;

import com.hntxrj.txerp.entity.UserComp;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.CompService;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.util.EntityTools;
import com.hntxrj.txerp.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业管理 (RESTful api)
 */
@RestController
@RequestMapping("/comp")
public class CompController {

    private final CompService compService;

    @Autowired
    public CompController(CompService compService) {
        this.compService = compService;
    }


    @GetMapping
    public Page<UserComp> queryList(String compName, Integer page) {
        return compService.getUserCompList(compName, page);
    }


    @PostMapping("/addComp")
    public ResultVO add(String compName, String compShortName, String compid, String securityKey) throws ErpException {
        if (!"adsfbnhjkwegbrw".equals(securityKey)) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_ERROR);
        }

        if (StringUtils.isBlank(compName) || StringUtils.isBlank(compShortName)) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_NAME_NULL);
        }
        UserComp userComp = new UserComp();
        userComp.setShortname(compShortName);
        userComp.setLongname(compName);
        userComp.setCompid(compid);
        EntityTools.setEntityDefaultValue(userComp);
        return ResultVO.create(compService.addComp(userComp));
    }

    @PostMapping("/deleteComp")
    public ResultVO deleteComp(String compid, String securityKey) throws ErpException {
        if (!"adsfbnhjkwegbrw".equals(securityKey)) {
            throw new ErpException(ErrEumn.DELETE_ENTERPRISE_ERROR);
        }
        compService.deleteComp(compid);
        return ResultVO.create();
    }

    @PostMapping("/updateComp")
    public ResultVO updateComp(String compName, String compShortName, String compid, String securityKey) throws ErpException {
        if (!"adsfbnhjkwegbrw".equals(securityKey)) {
            throw new ErpException(ErrEumn.UPDATE_ENTERPRISE_ERROR);
        }
        compService.updateComp(compid, compName, compShortName);
        return ResultVO.create();
    }


}
