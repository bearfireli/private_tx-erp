package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.LoginConstructionService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginConstructionApi {
    private final LoginConstructionService loginConstructionService;

    public LoginConstructionApi(LoginConstructionService loginConstructionService) {
        this.loginConstructionService = loginConstructionService;
    }

    //校对账户和密码是否正确
    @RequestMapping("/getAccountPassword")
    public ResultVO getAccountPassword(String userName, String passWord, String tokens) throws ErpException {
        return ResultVO.create(loginConstructionService.getAccountPassword(userName, passWord, tokens));
    }

    /**
     * 注册
     *
     * @param userName  用户名
     * @param passWord  密码
     * @param buildName 施工方名称
     */
    @RequestMapping("/addUser")
    public ResultVO addUser(String userName, String passWord, String buildName) throws ErpException {
        loginConstructionService.addUser(userName, passWord, buildName);
        return ResultVO.create();
    }

    /**
     * 修改密码
     *
     * @param buildId      施工方用户Id
     * @param oldPassword  旧密码
     * @param newPassword  新密码
     */

    @RequestMapping("/updatePassword")
    public ResultVO updatePassword(String buildId, String oldPassword, String newPassword) throws ErpException {
        loginConstructionService.updatePassword(buildId, oldPassword, newPassword);
        return ResultVO.create();
    }


}
