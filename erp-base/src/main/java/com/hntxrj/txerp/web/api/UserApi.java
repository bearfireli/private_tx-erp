package com.hntxrj.txerp.web.api;


import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserApi {

    private final UserService userService;
    private ResultVO resultVO;

    @Autowired
    public UserApi(UserService userService) {
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResultVO add(User user) throws ErpException {
        resultVO.setData(userService.addUser(user));
        return resultVO;
    }

    @GetMapping("/users/{uid}")
    public ResultVO get(@PathVariable Integer uid) throws ErpException {
        resultVO.setData(userService.findById(uid));
        return resultVO;
    }


    @PostMapping("/user/login")
    public ResultVO login(String phone, String password, HttpServletRequest request,String version) throws ErpException {
        String loginUa = request.getHeader("loginUa");
        resultVO.setData(userService.login(phone, password, request, loginUa,version));
        return resultVO;
    }


}
