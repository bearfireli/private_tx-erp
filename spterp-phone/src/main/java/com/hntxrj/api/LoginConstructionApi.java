package com.hntxrj.api;

import com.hntxrj.exception.ErpException;
import com.hntxrj.server.LoginConstructionService;
import com.hntxrj.vo.ResultVO;
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
    public ResultVO getAccountPassword(String userName,String passWord,String tokens) throws ErpException {
        return  ResultVO.create(loginConstructionService.getAccountPassword(userName,passWord,tokens));
    }

    /**
     * 注册
     * @param userName          用户名
     * @param passWord          密码
     */
    @RequestMapping("/addUser")
    public ResultVO addUser(String userName,String passWord) throws ErpException {
        loginConstructionService.addUser(userName,passWord);
        return  ResultVO.create();
    }


}
