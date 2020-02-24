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
import com.hntxrj.txerp.vo.UserSaveVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private RedisTemplate redisTemplate;



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

    //登录接口
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


    //推出接口
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

    //查询token是否可用
    @PostMapping("/tokenUse")
    public ResultVO tokenUse(String token) throws ErpException {
        log.debug("【验证token是否可用】token={}", token);
        resultVO.setData(userService.tokenCanUse(token));
        return resultVO;
    }


    /**
     * 用户列表
     *
     * @param username  用户名
     * @param phoneNum  手机号
     * @param email      邮箱
     * @param enterpriseId  公司
     * @param token      用户认证标识
     * @param page       页码
     * @param pageSize   每页条数
     * */
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


    /**
     * 查询用户信息（内部调用）
     * @param username  用户名
     * @param phoneNum  手机号
     * @param email      邮箱
     * @param enterpriseId  公司
     * @param token      用户认证标识
     * */
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

    /**
     * 查询用户
     * */
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


    /**
     * 用户详情
     * */
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


    /**
     * 添加用户
     * @param user  用户对象
     * @param enterprise  企业id
     * */
    @PostMapping("/addUser")
    public String addUser(User user, String enterprise) throws ErpException {
        log.info("【添加用户】user={}", user);

        //用户先不添加到数据库user表中，等用户添加权限时再和用户权限统一添加到数据库中。
        user = userService.addUser(user);
        //得到这个用户的hashcode值作为区分用户的唯一标示
        int hashCode = user.hashCode();

        //以此用户的hashcode值作为key把用户存进缓存中
        redisTemplate.opsForValue().set(String.valueOf(hashCode),user);
        UserSaveVO userSaveVO = new UserSaveVO();
        userSaveVO.setUid(0);
        userSaveVO.setIdentification(hashCode);


        resultVO.setData(JSON.toJSONString(userSaveVO));
        return JSON.toJSONString(resultVO);
    }


    /**
     * 设置用户权限
     * @param param 用户权限json对象
     * @param token  用户认证标识
     * */
    @PostMapping("/setUserAuth")
    public String setUserAuth(@RequestBody String param, @RequestHeader String token) throws ErpException {

        JSONObject data = JSONObject.parseObject(param);

        Integer uid = data.getInteger("uid");
        Integer identification = data.getInteger("identification");

        if (uid == 0) {
            //新用户添加权限，调用新用户添加权限的借口
            User user = (User) redisTemplate.opsForValue().get(identification.toString());
            userService.addUserAuth(param, token, user);
        } else {
            //说明是老用户修改权限，调用修改权限的接口
            userService.updateUserAuth(param, token);
        }

        return JSON.toJSONString(resultVO);
    }

    /**
     * 编辑用户
     * */
    @PostMapping("/editUser")
    public String editUser(User user) throws ErpException {
        log.info("【修改用户】user={}", user);
        resultVO.setData(JSON.toJSONString(userService.updateUser(user)));
        return JSON.toJSONString(resultVO);
    }

    /**
     * 修改用户密码
     * */
    @PostMapping("/updatePassword")
    public String updatePassword(String oldPassword, String newPassword,
                                 String token) throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(userService.updatePassword(oldPassword, newPassword, token))));
        return JSON.toJSONString(resultVO);
    }

    /**
     * 重置用户密码
     * @param token   用户认证标识
     * @param uid       用户id
     * @param password  重置后的密码
     * */
    @PostMapping("/initUser")
    public String initUser(String token, Integer uid, String password) throws ErpException {
        userService.initPassword(token, uid, password);
        return JSON.toJSONString(resultVO);
    }

    /**
     * 检验手机号是否存在
     * */
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

    /**
     * 根据token获取用户信息
     * */
    @PostMapping("/tokenGetUser")
    public String tokenGetUser(String token) throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(userService.tokenGetUser(token))));
        return JSON.toJSONString(resultVO);
    }

    /**
     * 通过用户id获取企业集合
     *
     * @param uid 用户id
     * @return 返回企业集合
     */
    @PostMapping("/getUserEnterprise")
    public String getUserEnterprise(Integer uid) throws ErpException {
        resultVO.setData(JSON.parseArray(JSON.toJSONString(userService.gerEnterprisesById(uid))));
        return JSON.toJSONString(resultVO);
    }

    /**
     * 检验密码是否正确
     * */
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
    public void header(String token, HttpServletResponse response) throws ErpException {
        userService.getHeader(token, response);
    }


    /**
     * 获取手机erp用户常用功能模块
     * */
    @PostMapping("/getUserFavorite")
    public ResultVO getUserFavoriteConfig(@RequestHeader String token) throws ErpException {
        resultVO.setData(userService.getUserFavoriteConfig(token));
        return resultVO;
    }


    /**
     * 设置手机erp用户常用功能模块
     * */
    @PostMapping("/setUserFavorite")
    public ResultVO setUserFavoriteConfig(@RequestHeader String token, String config) throws ErpException {
        userService.setUserFavoriteConfig(token, config);
        resultVO.setData("设置成功");
        return resultVO;
    }


    /**
     * 获取用户绑定司机
     * */
    @PostMapping({"/getBindDriver"})
    public ResultVO getBindDriver(@RequestHeader String token, String compid) throws ErpException {
        return ResultVO.create(this.userService.getBindDriver(token, compid));
    }


    /**
     * 给用户绑定司机
     *
     * @param uid    用户id
     * @param compid  企业id
     * @param driverCode  司机编号
     * */
    @PostMapping({"/bindDriver"})
    public ResultVO getBindDriver(Integer uid, String compid, String driverCode) throws ErpException {
        this.userService.bindDriver(uid, compid, driverCode);
        return ResultVO.create();
    }


    /**
     * 用于更改用户的超级管理员权限
     * @param uid    用户id
     * @param eadmin  1:超级管理员；  0：取消超级管理员
     */
    @RequestMapping("/updateUserStatus")
    public ResultVO updateUserAdminStatus(int uid, String eadmin) throws ErpException {
        userService.updateUserAdminStatus(uid, eadmin);
        return ResultVO.create();
    }


    /**
     * 查询所有企业所有用户
     * */
    @PostMapping("/selectAllUser")
    public String selectAllUser(){
        resultVO.setData(JSON.toJSONString(userService.selectAllUser()));
        return  JSON.toJSONString(resultVO);
    };

    @PostMapping("/userAll")
    public String userAll(Integer eid){
        resultVO.setData(JSON.toJSONString(userService.userAll(eid)));
        return  JSON.toJSONString(resultVO);
    };

}
