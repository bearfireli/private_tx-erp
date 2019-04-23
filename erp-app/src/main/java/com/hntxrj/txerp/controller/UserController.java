package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyuncs.exceptions.ClientException;
import com.hntxrj.txerp.service.AuthCodeService;
import com.hntxrj.txerp.service.UserAccountService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.util.DingUtil;
import com.hntxrj.txerp.core.util.IpUtil;
import com.hntxrj.txerp.entity.base.AuthCode;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserAccountService userAccountService;
    private ResultVO resultVO;
    private final AuthCodeService authCodeService;


    @Autowired
    public UserController(UserService userService,
                          UserAccountService userAccountService, AuthCodeService authCodeService) {
        this.userAccountService = userAccountService;
        this.authCodeService = authCodeService;
        resultVO = null;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        this.userService = userService;
    }

    /**
     * 绑定第三方账号
     *
     * @param token  用户token
     * @param acType 用户类型
     * @param value  第三方账号openid
     * @return 绑定结果
     */
    @PostMapping("/bind_wechat")
    // 微信绑定
    public String bind(String token, String authCode, String authCodeId, String acType, String value)
            throws ErpException {
        userAccountService.bindAccount(token, authCode, authCodeId, acType, value);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/unbindWeChat")
    public ResultVO unbindWeChat(Integer uid) throws ErpException {
        userAccountService.unbind(uid, "wechat");
        return resultVO;
    }

    @PostMapping("/bind_ding")
    public String bindDing(String token, String code) throws ErpException {
        userAccountService.bindAccount(token, "ding", DingUtil.getUserId(code));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/bind")
    public String bind_1(String token, String acType, String value)
            throws ErpException {
        userAccountService.bindAccount(token, acType, value);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/sendBindAuthCode")
    public String sendAuthCode(String token) throws ErpException, ClientException {
        User user = userService.tokenGetUser(token);
        JSONObject resultJson = new JSONObject();
        resultJson.put("authCodeId", authCodeService.sendAuthCode(user.getPhone(), AuthCode.AU_TYPE_BING));
        resultVO.setData(resultJson);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/forcedBinding")
    public String forcedBinding(String token) throws ErpException, IOException {
        //强制绑定企业
        User user = userService.tokenGetUser(token);
        File file = new File("/data/erp_base/forcedbinding.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), UTF_8);
        // 构建InputStreamReader对象,编码与写入相同

        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append((char) reader.read());
            // 转成char加到StringBuffer对象中
        }
        String[] enterpriseIds = sb.toString().split("\\|");

        reader.close();
        log.info("【enterpriseIds】enterpriseIds={}", Arrays.toString(enterpriseIds));
        for (String en : enterpriseIds) {
            if (en.equals(userService.gerEnterprisesById(user.getUid()).toString())) {
                resultVO.setData("{\"forcedBinding\":\"true\"}");
                return JSON.toJSONString(resultVO);

            }
        }
        resultVO.setData("{\"forcedBinding\":\"false\"}");
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/unbind")
    public String unbind(String token, String acType) throws ErpException {
        userAccountService.unbindAccount(token, acType);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/binds")
    public String binds(String token) throws ErpException {
        resultVO.setData(JSON.parseArray(JSON.toJSONString(userAccountService.binds(token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/login")
    public String login(String phone,
                        String password,
                        HttpServletRequest request)
            throws ErpException {
        log.info("【登录v1】phone={}, password={}", phone, password);
        resultVO.setData(JSON.toJSONString(userService.login(phone, password, request),
                SerializerFeature.DisableCircularReferenceDetect));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/loginOut")
    public String loginOut(String token) {
        log.info("【退出登录v1】token={}", token);
        userService.loginOut(token);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/thirdLogin")
    public String thirdLogin(String openId, String type, HttpServletRequest request)
            throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(
                userService.login(openId, type, IpUtil.getIp(request)),
                SerializerFeature.DisableCircularReferenceDetect)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/tokenUse")
    public String tokenUse(String token) throws ErpException {
        log.debug("【验证token是否可用】token={}", token);

        resultVO.setData(JSON.toJSONString(userService.tokenCanUse(token)));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/userList")
    public String userList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phoneNum,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "0") Integer enterpriseId,
            String token,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) throws ErpException {
        return getUser(username, phoneNum, email, enterpriseId,
                page, pageSize, request, token, resultVO, userService);
    }


    private static String getUser(String username, String phoneNum,
                                  String email, Integer enterpriseId,
                                  Integer page, Integer pageSize,
                                  HttpServletRequest request, String token,
                                  ResultVO resultVO, UserService userService) throws ErpException {
        log.info("【查询用户】params={}", request.getParameterMap());
        User user = new User();
        user.setUsername(username);
        user.setPhone(phoneNum);
        user.setEmail(email);
        resultVO.setData(JSON
                .toJSONString(userService.getUser(user, token, enterpriseId, request, page, pageSize),
                        SerializerFeature.DisableCircularReferenceDetect));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getUser")
    public String getUser(Integer uid,
                          @RequestParam(defaultValue = "0") int showPhone)
            throws ErpException {
        log.info("【获取用户】uid={}", uid);
        resultVO.setData(JSON.toJSONString(
                userService.findUserById(uid, showPhone == 1)
        ));

        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/details")
    public String details(Integer uid)
            throws ErpException {
        log.info("【查看用户】uid={}", uid);
        resultVO.setData(JSON.toJSONString(
                userService.findUserdetails(uid)
        ));

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getUsers")
    public String getUsers(String uids) {
        String[] es = uids.split("\\|");
        Integer[] eis = new Integer[es.length];
        int i = 0;
        for (String e : es) {
            eis[i] = Integer.parseInt(e);
        }
        resultVO.setData(JSON.parseArray(JSON.toJSONString(userService.getUsers(eis))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/addUser")
    public String addUser(User user, String enterprise) throws ErpException {
        log.info("【添加用户】user={}", user);
        resultVO.setData(JSON.toJSONString(userService.addUser(user)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/setUserAuth")
    public String setUserAuth(@RequestBody String param, @RequestHeader String token) throws ErpException {
        userService.setUserAuth(param, token);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/editUser")
    public String editUser(User user) throws ErpException {
        log.info("【修改用户】user={}", user);
        resultVO.setData(JSON.toJSONString(userService.updateUser(user)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/updatePassword")
    public String updatePassword(String oldPassword, String newPassword,
                                 String token) throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(userService.updatePassword(oldPassword, newPassword, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/initUser")
    public String initUser(String token, Integer uid, String password) throws ErpException {
        userService.initPassword(token, uid, password);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/phoneIsExist")
    public String phoneIsExist(String phone) throws ErpException {
        userService.phoneIsExist(phone);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/useOpenIdGetUser")
    public String useOpenIdGetUser(String type, String openId, HttpServletRequest request) throws ErpException {
        resultVO.setData(userAccountService.userOpenIdGetUser(type, openId, IpUtil.getIp(request)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/tokenGetUser")
    public String tokenGetUser(String token) throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(userService.tokenGetUser(token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getUserEnterprise")
    public String getUserEnterprise(Integer uid) throws ErpException {
        resultVO.setData(JSON.parseArray(JSON.toJSONString(userService.gerEnterprisesById(uid))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/checkPassword")
    public String checkPassword(String token, String password) throws ErpException {
        userService.checkPassword(token, password);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/setHeader")
    public String setHeader(MultipartFile file,
                            @RequestHeader String token) throws ErpException {

        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(userService.setHeader(file, token))));

        return JSON.toJSONString(resultVO);
    }

    @GetMapping("/header.png")
    public void header(@RequestHeader String token, HttpServletResponse response) throws ErpException, IOException {
        userService.getHeader(token, response);
    }


    @PostMapping("/getUserFavorite")
    public ResultVO getUserFavoriteConfig(@RequestHeader String token) throws ErpException {
        resultVO.setData(userService.getUserFavoriteConfig(token));
        return resultVO;
    }


    @PostMapping("/setUserFavorite")
    public ResultVO setUserFavoriteConfig(@RequestHeader String token, String config) throws ErpException {
        userService.setUserFavoriteConfig(token, config);
        return resultVO;
    }
}
