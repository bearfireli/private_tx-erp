package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.enums.UserStatusEnums;
import com.hntxrj.txerp.mapper.UserMapper;
import com.hntxrj.txerp.repository.*;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.EncryptUtil;
import com.hntxrj.txerp.core.util.EntityTools;
import com.hntxrj.txerp.core.util.IpUtil;
import com.hntxrj.txerp.core.util.TimeUtil;
import com.hntxrj.txerp.util.PageInfoUtil;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QUserAccount;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.UserVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;


@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EnterpriseRepository enterpriseRepository;

    private final AuthGroupRepository authGroupRepository;

    private final UserLoginRepository userLoginRepository;

    private final UserAccountRepository userAccountRepository;
    private final UserAuthRepository userAuthRepository;
    private final UserBindDriverRepository userBindDriverRepository;

    private final UserMapper userMapper;

    @Value("${app.user.headerPath}")
    private String headerUploadPath;

    @Value("${app.host}")
    private String url;

    // private final AmqpTemplate rabbitTemplate;
    // JPA查询工厂
    private JPAQueryFactory queryFactory;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EnterpriseRepository enterpriseRepository,
                           AuthGroupRepository authGroupRepository,
                           UserLoginRepository userLoginRepository,
                           EntityManager entityManager, UserAccountRepository userAccountRepository,
                           UserAuthRepository userAuthRepository, UserBindDriverRepository userBindDriverRepository, UserMapper userMapper) {
        super(entityManager);
        this.userRepository = userRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.authGroupRepository = authGroupRepository;
        this.userLoginRepository = userLoginRepository;
        this.userAccountRepository = userAccountRepository;
        this.userAuthRepository = userAuthRepository;
        this.userBindDriverRepository = userBindDriverRepository;
        this.userMapper = userMapper;
        this.queryFactory = getQueryFactory();
    }


    @Override
    public UserVO login(String phoneNumber, String password,
                        HttpServletRequest request) throws ErpException {

        User user = userRepository.findByPhone(phoneNumber);

        if (user == null || !user.getPassword().equals(EncryptUtil.encryptPassword(password))) {
            throw new ErpException(ErrEumn.LOGIN_ERR);
        }
        if (user.getStatus() != 0) {
            throw new ErpException(ErrEumn.USER_CANNOT_LOGIN);
        }
        if (request.getHeader("pid").equals("2")) {
            // 手机erp实现微信绑定后其他方法不能登录
            QUserAccount qUserAccount = QUserAccount.userAccount;

            long count = queryFactory.selectFrom(qUserAccount)
                    .where(qUserAccount.uid.eq(user.getUid()))
                    .where(qUserAccount.acValue.isNotNull())
                    .where(qUserAccount.acType.eq("wechat"))
                    .fetchCount();
            if (count != 0) {
                throw new ErpException(ErrEumn.THE_USER_ALREADY_BOUND_THIS_TYPE_ACCOUNT);
            }

        }

        if (request.getHeader("pid").equals("1")) {
            // 手机erp实现微信绑定后其他方法不能登录
            QUserAccount qUserAccount = QUserAccount.userAccount;
            long count = queryFactory.selectFrom(qUserAccount)
                    .where(qUserAccount.uid.eq(user.getUid()))
                    .where(qUserAccount.acValue.isNotNull())
                    .where(qUserAccount.acType.eq("spter_weixin_x"))
                    .fetchCount();
            if (count != 0) {
                throw new ErpException(ErrEumn.THE_USER_ALREADY_BOUND_THIS_TYPE_ACCOUNT);
            }
        }

        UserLogin userLogin = createUserLogin(user.getUid(), IpUtil.getIp(request));

        UserVO userVO = userToUserVO(user, true);
        QUserAuth qUserAuth = QUserAuth.userAuth;
        List<UserAuth> userAuths = queryFactory.selectFrom(qUserAuth).where(qUserAuth.user.uid.eq(userVO.getUid())).fetch();

        //用userauthvos代替userauths进行数据传递
        List<UserAuthVO> userAuthVOS = replaceUserAuth(userAuths);


        QAuthValue qAuthValue = QAuthValue.authValue;
        QMenu qMenu = QMenu.menu;
        for (UserAuthVO userAuthVO : userAuthVOS) {
            List<Menu> menuList = queryFactory.selectFrom(qMenu).rightJoin(qAuthValue).on(qAuthValue.menuId.eq(qMenu.mid)).where(qAuthValue.groupId.eq(userAuthVO.getAuthGroup().getAgid()).and(qMenu.menuLevel.eq(3)).and(qAuthValue.value.eq(1))).fetch();
            userAuthVO.setMenuVOS(menuList);
        }

        userVO.setUserAuths(userAuthVOS);
        userVO.setToken(userLogin.getUserToken());
        log.info("【登录对象】userVO={}", userVO);
        return userVO;

    }

    @Override
    public UserVO login(String value, String type, String loginIp) throws ErpException {
        log.info("【三方登录】value={}, type={}, ip={}", value, type, loginIp);
        if (value == null || type == null) {
            log.error("【空参数异常】value={}, type={}", value, type);
            throw new ErpException(ErrEumn.OPENID_TYPE_CANNOT_NULL);
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.
                    findByAcTypeAndAcValue(type, value);
        } catch (org.springframework.dao.IncorrectResultSizeDataAccessException e) {
            throw new ErpException(ErrEumn.THIS_WECHAT_BIND_ACCOUNT_TOO_MANY);

        }

        log.info("[userAccount] uaccount={}", userAccount);
        if (userAccount == null) {
            throw new ErpException(ErrEumn.NOT_BIND_ACCOUNT);
        }
        UserLogin userLogin = createUserLogin(userAccount.getUid(), loginIp);
        UserVO userVO = findUserById(userAccount.getUid(), true);
        // 判断是否被禁止登录
        if (userVO.getStatus() != 0) {
            throw new ErpException(ErrEumn.USER_CANNOT_LOGIN);
        }
        QUserAuth qUserAuth = QUserAuth.userAuth;
        List<UserAuth> userAuths = queryFactory.selectFrom(qUserAuth).where(qUserAuth.user.uid.eq(userVO.getUid())).fetch();
        //用userAuthVOS代替userAuths向前台传递数据
        List<UserAuthVO> userAuthVOS = replaceUserAuth(userAuths);
        userVO.setUserAuths(userAuthVOS);
        userVO.setToken(userLogin.getUserToken());
        log.info("【登录对象】userVO={}", userVO);
        return userVO;
    }

    /**
     * 创建一个登录记录，生成一个token
     *
     * @param userId  用户id
     * @param loginIp 登录ip
     * @return 登录记录
     * @author haoran liu
     */
    @Override
    public UserLogin createUserLogin(Integer userId, String loginIp) throws ErpException {
        UserLogin userLogin = new UserLogin();
        userLogin.setId(EncryptUtil.encryptPassword(UUID.randomUUID().toString()));
        userLogin.setLoginIp(loginIp);
        userLogin.setUserToken(
                EncryptUtil.encryptPassword(
                        UUID.randomUUID()
                                .toString()));
        userLogin.setUserId(userId);

        userLogin.setExpireTime(new Timestamp(
                TimeUtil.addDay(new Date(), 30).getTime()
        ));

        userLogin = userLoginRepository.save(userLogin);

//        User user = findById(userId);
//        redisUtil.redisSetKey(RedisDataTypeEnums.TOKEN + userLogin.getUserToken(), user);
        return userLogin;
    }

    @Override
    public List<Enterprise> gerEnterprisesById(Integer uid) throws ErpException {

        if (uid == null) {
            throw new ErpException(ErrEumn.ADD_USER_UID_IS_NULL);
        }

        QEnterprise qEnterprise = QEnterprise.enterprise;
        QUserAuth qUserAuth = QUserAuth.userAuth;

        return queryFactory.select(qEnterprise)
                .from(qUserAuth)
                .leftJoin(qEnterprise).on(qUserAuth.enterprise.eid.eq(qEnterprise.eid))
                .where(qUserAuth.user.uid.eq(uid))
                .fetch();
    }

    @Override
    public List<Enterprise> getEnterprisesByToken(String token) throws ErpException {
        User user = tokenGetUser(token);
        return gerEnterprisesById(user.getUid());
    }

    @Override
    public List<Integer> getEnterpriseIdsByToken(String token) throws ErpException {
        List<Enterprise> enterprises = getEnterprisesByToken(token);
        List<Integer> enterpriseIds = new ArrayList<>();
        for (Enterprise enterprise : enterprises) {
            enterpriseIds.add(enterprise.getEid());
        }
        return enterpriseIds;
    }


    @Override
    public Integer getUserAuthGroupByEnterprise(Integer enterprise, String token) throws ErpException {
        User user = tokenGetUser(token);
        QUserAuth qUserAuth = QUserAuth.userAuth;
        UserAuth userAuth = queryFactory.selectFrom(qUserAuth)
                .where(qUserAuth.enterprise.eid.eq(enterprise))
                .where(qUserAuth.user.uid.eq(user.getUid()))
                .fetchOne();
        if (userAuth == null) {
            throw new ErpException(ErrEumn.USER_NOT_IN_ENTERPRISE);
        }
        return userAuth.getAuthGroup().getAgid();
    }

    @Override
    public boolean userIsSupperAdmin(Integer uid) {
        QUserAuth qUserAuth = QUserAuth.userAuth;
        List<UserAuth> userAuths = queryFactory.selectFrom(qUserAuth)
                .where(qUserAuth.user.uid.eq(uid))
                .fetch();
        log.debug("【权限组】userauth={}", userAuths);
        Integer SUPPER_ADMIN_GROUP_ID = 1;
        for (UserAuth userAuth : userAuths) {
            if (userAuth.getAuthGroup().getAgid().equals(SUPPER_ADMIN_GROUP_ID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean userIsSupperAdmin(String token) throws ErpException {
        User user = tokenGetUser(token);
        return userIsSupperAdmin(user.getUid());
    }

    @Override
    public void updateUserAuth(String params, String token) throws ErpException {
        JSONObject data = JSONObject.parseObject(params);

        Integer uid = data.getInteger("uid");
        JSONArray userAuthArray = data.getJSONArray("arr");
        User addUser = tokenGetUser(token);

        //给用户绑定权限
        setUserAuth(uid, userAuthArray, addUser);


    }

    @Override
    public void addUserAuth(String params, String token, User user) throws ErpException {
        JSONObject data = JSONObject.parseObject(params);

        //将新添加的用户存入数据库
        try {
            User saveUser = userRepository.save(user);
            data.put("uid", saveUser.getUid());
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_USER_ERR);
        }

        Integer uid = data.getInteger("uid");
        JSONArray userAuthArray = data.getJSONArray("arr");
        User addUser = tokenGetUser(token);

        //将新添加的用户和权限关联起来。
        setUserAuth(uid, userAuthArray, addUser);
    }


    @Override
    public void checkPassword(String token, String password) throws ErpException {
        User user = tokenGetUser(token);
        if (!user.getPassword().equals(EncryptUtil.encryptPassword(password))) {
            throw new ErpException(ErrEumn.AUTH_PASSWORD_FAIL);
        }
    }

    @Override
    public User setHeader(MultipartFile file, String token) throws ErpException {
        User user = tokenGetUser(token);

        log.info("【文件上传路径】path={}", headerUploadPath);
        String[] fileNameSplits = file.getOriginalFilename().split("\\.");
        String reName = UUID.randomUUID().toString() +
                (fileNameSplits.length != 0 ? "." + fileNameSplits[fileNameSplits.length - 1] : "");
        String filePath = headerUploadPath + reName;
        user.setHeader(reName);
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filePath)));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("【上传文件失败】errorMsg={}", e.getMessage());
            throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
        }
        userRepository.save(user);
        return userRepository.findById(user.getUid()).orElseThrow(() ->
                new ErpException(ErrEumn.USER_NO_EXIT));
    }


    @Override
    public void getHeader(String token, HttpServletResponse response) throws ErpException {
        User user = tokenGetUser(token);
        String fileName = "default.png";
        if (user.getHeader() != null && !user.getHeader().equals("")) {
            fileName = user.getHeader();
        }
        File file = new File(headerUploadPath + fileName);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        try {
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copy(new FileInputStream(file), outputStream);
        } catch (Exception e) {
            log.error("【上传头像失败】errorMsg={}", e.getMessage());
            throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
        }
    }


    @Override
    public String getUserFavoriteConfig(String token) throws ErpException {

        User user = tokenGetUser(token);

        return user.getUserFavoriteConfig();
    }

    @Override
    public void setUserFavoriteConfig(String token, String config) throws ErpException {

        User user = tokenGetUser(token);

        user.setUserFavoriteConfig(config);

        updateUserNotEncrypt(user);

    }

    @Override
    public void loginOut(String token) {
        UserLogin userLogin;
        if ((userLogin = userLoginRepository.findByUserToken(token)) != null) {
//            redisUtil.redisRemoveValue(RedisDataTypeEnums.TOKEN + token);
            userLoginRepository.delete(userLogin);
        }
    }

    @Override
    public UserVO tokenCanUse(String token) throws ErpException {
        log.debug("【tokenCanUse】token={}", token);
        UserLogin userLogin = userLoginRepository.findByUserToken(token);

        if (userLogin != null
                && userLogin.getExpireTime().getTime() > System.currentTimeMillis()) {
            User user = findById(userLogin.getUserId());
            // 判断用户状态是否禁止登录
            if (user.getStatus() != 0) {
                throw new ErpException(ErrEumn.USER_CANNOT_LOGIN);
            }
            UserVO userVO = userToUserVO(user, true);
            userVO.setToken(userLogin.getUserToken());
            return userVO;
        }
        log.error("【tokenCanUse throw EXPIRE_TOKEN】 token={}", userLogin);
        throw new ErpException(ErrEumn.EXPIRE_TOKEN);
    }

    @Override
    public PageVO<UserAuthVO> getUser(User user, String token, Integer enterpriseId, HttpServletRequest request,
                                      int page, int pageSize) throws ErpException {

        log.info("【user】user={}", user);

        PageHelper.startPage(page, pageSize);


        List<UserAuth> userAuths = userMapper.selectUserList(enterpriseId, user.getUsername(), user.getPhone(), user.getEmail());

        //用userAuthVOS代替userAuths进行数据传递
        List<UserAuthVO> userAuthVOS = replaceUserAuth(userAuths);

        List<UserListVO> userList = userMapper.getUserList(enterpriseId, user.getUsername(), user.getPhone(), user.getEmail());
        StringBuilder driverCodes = new StringBuilder();
        for (UserListVO userListVO : userList) {
            String driverCode = userListVO.getDriverCode();
            if (driverCode != null && !driverCode.equals("")) {
                driverCodes.append(driverCode).append(",");
            }
        }

        String baseUrl = "";
        baseUrl = url + "/driver/getDriverNames";
        Map<String, Object> map = new HashMap<>();
        map.put("driverCodes", driverCodes.toString());
        map.put("compid", enterpriseId);
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .other("token", token)
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(baseUrl)
                .map(map)
                .encoding("utf-8")
                .inenc("utf-8");
        //使用方式：
        String pass = "";
        try {
            String result = HttpClientUtil.post(config);
            JSONObject data1 = JSONObject.parseObject(result);
            if (data1 != null) {
                JSONObject data = data1.getJSONObject("data");

                for (UserAuthVO userAuthVO1 : userAuthVOS) {
                    for (UserListVO userListVO : userList) {
                        if ( userAuthVO1.getUser().getUid().equals(userListVO.getUid()) ) {
                            //把司机姓名赋值给userAuth
                            if (data.get(userListVO.getDriverCode()) != null) {
                                userAuthVO1.setDriverName( data.getString(userListVO.getDriverCode()));
                            } else {
                                userAuthVO1.setDriverName("");
                            }

                        }
                    }
                }
            }
        } catch (HttpProcessException e) {
            log.warn("请求spterp项目失败");
            e.printStackTrace();
        }

        //把userAuths的分页查询总数赋值给userAuthVO的pageInfo中
        PageInfo<UserAuthVO> pageInfo = new PageInfo<>(userAuthVOS);
        PageInfo<UserAuth> userAuthPageInfo = new PageInfo<>(userAuths);
        long total = userAuthPageInfo.getTotal();

        pageInfo.setTotal(total);
        PageInfoUtil<UserAuthVO> pageInfoUtil = new PageInfoUtil<>();

        return pageInfoUtil.init(pageInfo);

    }


    @Override
    public UserVO findUserById(Integer userId,
                               boolean showPhoneNumber) throws ErpException {

        if (userId == null) {
            throw new ErpException(ErrEumn.PARAM_IS_NULL);
        }
        if (userId <= 0) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }

        return userToUserVO(findById(userId), showPhoneNumber);

    }

    @Override
    public UserVO findUserdetails(Integer userId) throws ErpException {
        User user = findById(userId);
        List<User> users = new ArrayList<>();
        users.add(user);
        UserAccount userAccounts = userMapper.type(userId);
        List<UserVO> userVOS = userToUserVO(users, true);
        UserVO userVO = userVOS.get(0);
        List<AuthGroupVO> authGroups = userMapper.findById(userId);
        StringBuilder epShortNamelist = new StringBuilder();
        StringBuilder agnamelist = new StringBuilder();
        for (AuthGroupVO authGroupVO : authGroups) {
            if (epShortNamelist.length() == 0) {
                epShortNamelist = new StringBuilder(authGroupVO.getEpShortName());
            } else {
                epShortNamelist.append(",").append(authGroupVO.getEpShortName());
            }
            if (agnamelist.length() == 0) {
                agnamelist = new StringBuilder(authGroupVO.getAgName());
            } else {
                agnamelist.append(",").append(authGroupVO.getAgName());
            }
        }
        userVO.setEpShortNamelist(epShortNamelist.toString());
        userVO.setAgnamelist(agnamelist.toString());
        if (userAccounts != null) {
            userVO.setActype(userAccounts.getAcType());
        }
        return userVO;
    }

    @Override
    public User findById(Integer userId) throws ErpException {
        // 加入缓存机制
//        User user = findByUserByIdRedis(userId);
//        if (user == null) {
        Optional<User> userOption = userRepository.findById(userId);
        User user = userOption.orElseThrow(
                () -> new ErpException(ErrEumn.USER_NO_EXIT));
//        cacheUser(user);
//        }
        return user;
    }

    @Override
    public User addUser(User user) throws ErpException {
        if (user == null) {
            throw new ErpException(ErrEumn.ADD_USER_IS_NULL);
        }
        if (user.getUsername() == null) {
            throw new ErpException(ErrEumn.ADD_USER_USERNAME_IS_NULL);
        }
        if (user.getPassword() == null) {
            throw new ErpException(ErrEumn.ADD_USER_PASSWORD_IS_NULL);
        }
        if (user.getPhone() == null) {
            throw new ErpException(ErrEumn.ADD_USER_PHONE_IS_NULL);
        }
        if (user.getStatus() == null) {
            throw new ErpException(ErrEumn.ADD_USER_STATUS_IS_NULL);
        }
        if (user.getBindSaleManName() == null) {
            user.setBindSaleManName("");
        }
        if (user.getHeader() == null) {
            user.setHeader("");
        }
        if (user.getEmail() == null) {
            user.setEmail("");
        }
        // TODO: 设置权限组
//        if (user.getAuthGroup() == null) {
//            user.setAuthGroup(0);
//        }

        phoneIsExist(user.getPhone());
        user.setPassword(
                EncryptUtil.encryptPassword(user.getPassword()));

        EntityTools.setEntityDefaultValue(user);

        return user;
        /*try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_USER_ERR);
        }*/

    }

    @Override
    public UserVO updateUser(User user) throws ErpException {
        if (user == null) {
            throw new ErpException(ErrEumn.ADD_USER_IS_NULL);
        }
        if (user.getUid() == null || user.getUid() == 0) {
            throw new ErpException(ErrEumn.ADD_USER_UID_IS_NULL);
        }
        User oldUser = findById(user.getUid());
        if (user.getUsername() != null) {
            oldUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            // 修改密码删除所有令牌
            deleteUserToken(user.getUid());
            oldUser.setPassword(EncryptUtil.encryptPassword(user.getPassword()));
        }
        if (user.getPhone() != null) {
            oldUser.setPhone(user.getPhone());
        }
        if (user.getHeader() != null) {
            oldUser.setHeader(user.getHeader());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getUid() <= 0) {
            //当修改用户或找不到该用户的id时
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        if (user.getErpType() != null) {
            oldUser.setErpType(user.getErpType());
        }
        if (user.getBindSaleManName() != null) {
            oldUser.setBindSaleManName(user.getBindSaleManName());
        }

        oldUser.setStatus(user.getStatus());


        try {
            userRepository.save(oldUser);
//            cacheUser(oldUser);
            return userToUserVO(oldUser, true);
        } catch (Exception e) {
            // 处理入库失败情况
            throw new ErpException(ErrEumn.UPDATE_USER_ERR);
        }
    }


    /**
     * 修改用户，该方法不会修改密码
     *
     * @param user 返回以后
     * @return
     * @throws ErpException 异常
     */
    private UserVO updateUserNotEncrypt(User user) throws ErpException {
        if (user == null) {
            throw new ErpException(ErrEumn.ADD_USER_IS_NULL);
        }
        if (user.getUid() == null || user.getUid() == 0) {
            throw new ErpException(ErrEumn.ADD_USER_UID_IS_NULL);
        }
        User oldUser = findById(user.getUid());
        if (user.getUsername() != null) {
            oldUser.setUsername(user.getUsername());
        }
        if (user.getPhone() != null) {
            oldUser.setPhone(user.getPhone());
        }
        if (user.getHeader() != null) {
            oldUser.setHeader(user.getHeader());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getUid() <= 0) {
            //当修改用户或找不到该用户的id时
            throw new ErpException(ErrEumn.UPDATE_USER_PARAMS_ERR);
        }
        oldUser.setStatus(user.getStatus());

        try {
            userRepository.save(oldUser);
//            cacheUser(oldUser);
            return userToUserVO(oldUser, true);
        } catch (Exception e) {
            // 处理入库失败情况
            throw new ErpException(ErrEumn.UPDATE_USER_ERR);
        }
    }

    @Override
    public UserVO updatePassword(String oldPassword, String newPassword,
                                 String token) throws ErpException {

        User user = tokenGetUser(token);
        log.info("[修改密码]user={}", user);
        if (user == null) {
            log.error("updatePassword throw EXPIRE_TOKEN");

            throw new ErpException(ErrEumn.EXPIRE_TOKEN);
        }
        if ("".equals(oldPassword) || "".equals(newPassword)) {
            throw new ErpException(ErrEumn.ADD_USER_PASSWORD_IS_NULL);
        }
        if (!EncryptUtil.encryptPassword(oldPassword).equals(user.getPassword())) {
            throw new ErpException(ErrEumn.AUTH_PASSWORD_FAIL);
        }

        user.setPassword(EncryptUtil.encryptPassword(newPassword));

        // 修改密码删除所有历史token
        deleteUserToken(user.getUid());
        // 清redis缓存
//        redisUtil.redisRemoveValue(RedisDataTypeEnums.TOKEN + token);

        return userToUserVO(userRepository.save(user), true);
    }

    @Override
    public void initPassword(String token, Integer uid, String password) throws ErpException {
        if (!userIsSupperAdmin(token)) {
            throw new ErpException(ErrEumn.ONLY_ADMINISTRATOR_CAN_DO_IT);
        }
        User user = findById(uid);
        user.setPassword(EncryptUtil.encryptPassword(password));
        // 修改密码删除所有历史token
        deleteUserToken(uid);
        userRepository.save(user);
    }

    @Override
    public UserVO delUser(User user) throws ErpException {
        // 伪删除
        if (user == null || user.getUid() <= 0) {
            throw new ErpException(ErrEumn.DELECT_USER_ERR);
        }
        user = findById(user.getUid());

        user.setStatus(UserStatusEnums.NOUSE.getCode());

        try {
            return userToUserVO(userRepository.save(user),
                    true);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.DELECT_USER_ERR);
        }

    }

    @Override
    public void deleteUserToken(Integer userId) {
        List<UserLogin> userLogins = userLoginRepository.findAllByUserId(userId);
//        userLogins.forEach(userLogin -> {
//            redisUtil.redisRemoveValue(RedisDataTypeEnums.TOKEN + userLogin.getUserToken());
//        });
        userLoginRepository.deleteAllByUserId(userId);
    }

    @Override
    public User tokenGetUser(String token) throws ErpException {
        log.debug("【tokenGetUser】token={}", token);
        // 缓存获取
//        User cacheUser = redisUtil.redisGetValue(RedisDataTypeEnums.TOKEN + token);

//        if (cacheUser != null) {
//            return cacheUser;
//        }

        // 未命中查询
        UserLogin userLogin =
                userLoginRepository.findByUserToken(token);
        if (userLogin == null) {
            log.error("tokenGetUser throw EXPIRE_TOKEN");
            throw new ErpException(ErrEumn.EXPIRE_TOKEN);
        }
        //        redisUtil.redisSetKey(RedisDataTypeEnums.TOKEN + token, user);
        return userRepository
                .findById(userLogin.getUserId()).orElseThrow(
                        () -> new ErpException(ErrEumn.USER_NO_EXIT));
    }


    /**
     * User对象转uservo对象
     *
     * @param userList        user集合
     * @param showPhoneNumber 是否显示手机号:true为显示
     * @return userVO集合
     */
    private List<UserVO> userToUserVO(List<User> userList, boolean showPhoneNumber) {
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOS.add(userVO);
        }
        // 当不显示手机号时隐藏中间几位手机号
        if (!showPhoneNumber) {
            userList.forEach(item ->
                    item.setPhone(item.getPhone()
                            .replaceAll("(\\d{3})\\d{4}(\\d{4})",
                                    "$1****$2")));
        }
        return userVOS;
    }


    /**
     * 同上处理单个
     *
     * @param user            单个用户
     * @param showPhoneNumber 是否显示手机号：true为显示
     * @return 单个用户的uservo
     * @throws ErpException
     */
    private UserVO userToUserVO(User user, boolean showPhoneNumber) throws ErpException {
        if (user == null) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        List<User> users = new ArrayList<>();
        users.add(user);
        List<UserVO> userVOS = userToUserVO(users, showPhoneNumber);
        UserVO userVo = userVOS.get(0);
        List<UserAuth> userAuths = userAuthRepository.findAllByUser(user);

        List<UserAuthVO> userAuthVOS = replaceUserAuth(userAuths);

        QAuthValue qAuthValue = QAuthValue.authValue;
        QMenu qMenu = QMenu.menu;
        //根据用户权限获取用户所拥有的权限菜单集合
        for (UserAuthVO userAuthVO : userAuthVOS) {
            List<Menu> menuList = queryFactory.selectFrom(qMenu).rightJoin(qAuthValue).on(qAuthValue.menuId.eq(qMenu.mid)).where(qAuthValue.groupId.eq(userAuthVO.getAuthGroup().getAgid()).and(qMenu.menuLevel.eq(3)).and(qAuthValue.value.eq(1))).fetch();
            userAuthVO.setMenuVOS(menuList);
        }
        userVo.setUserAuths(userAuthVOS);
        for (UserAuthVO userAuth : userAuthVOS) {
            if (userVo.getEnterprises() == null) {
                userVo.setEnterprises(new ArrayList<>());
            }
            userVo.getEnterprises().add(userAuth.getEnterprise());
        }
        return userVo;
    }


    /**
     * 获取一批用户的所有包含企业和权限组。
     *
     * @param enterpriseIds 存放企业id的集合
     * @param authGroupIds  存放权限组id的集合
     */
    private void getEnterpriseIds(
            List<User> users,
            List<Integer> enterpriseIds,
            List<Integer> authGroupIds) {
        // TODO: 用redis缓存使用，单企业查询，放弃该方法

        List<Integer> uids = new ArrayList<>();
        users.forEach(user -> {
            uids.add(user.getUid());
        });

        QUserAuth qUserAuth = QUserAuth.userAuth;
        List<UserAuth> userAuths = queryFactory.selectFrom(qUserAuth)
                .where(qUserAuth.user.uid.in(uids))
                .fetch();

        userAuths.forEach(userAuth -> {
            enterpriseIds.add(userAuth.getEnterprise().getEid());
            authGroupIds.add(userAuth.getAuthGroup().getAgid());
        });

    }


    /**
     * 判断手机号是否存在
     *
     * @param phone 手机号
     * @throws ErpException 如果存在抛出异常
     */
    @Override
    public void phoneIsExist(String phone) throws ErpException {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            throw new ErpException(ErrEumn.ADD_USER_PHONE_EXIST);
        }
    }

    @Override
    public List<User> getUsers(Integer[] uids) {
        List<User> users = userRepository.findAllById(Arrays.asList(uids));
//        cacheUsers(users); // 缓存用户
        return users;
    }


    /**
     * 手机号是否存在，排除自己
     *
     * @param phone  手机号
     * @param userId 排除的用户id
     * @throws ErpException
     */
    public void phoneIsExist(String phone, Integer userId) throws ErpException {
        User user = userRepository.findByPhone(phone);
        if (user != null && !userId.equals(user.getUid())) {
            throw new ErpException(ErrEumn.ADD_USER_PHONE_EXIST);
        }
    }

    /*
     * 修改用户的权限状态码eadmin
     * */
    @Override
    public void updateUserAdminStatus(Integer userId, String eadmin) throws ErpException {
        if ("0".equals(eadmin)) {
            //此用户需要添加权限
            userMapper.addUserStatus(userId);
        } else if ("1".equals(eadmin)) {
            //此用户需要取消权限
            userMapper.deleteUserStatus(userId);
        }
    }

    //此方法用于绑定用户权限
    public void setUserAuth(Integer uid, JSONArray userAuthArray, User addUser) throws ErpException {
        // 获取添加人的所有企业和权限信息验证是否可以添加该企业用户
        QUserAuth qUserAuth = QUserAuth.userAuth;
        List<UserAuth> addUserAuth = queryFactory.selectFrom(qUserAuth)
                .where(qUserAuth.user.uid.eq(addUser.getUid())).fetch();
        List<UserAuth> userAuths = new ArrayList<>();
        userAuthArray.forEach(userArrayObj -> {
            JSONObject userAuthObj = JSONObject.parseObject(userArrayObj.toString());
            UserAuth userAuth = new UserAuth();
            Enterprise enterprise = new Enterprise();
            enterprise.setEid(userAuthObj.getInteger("eid"));

            AuthGroup authGroup = new AuthGroup();
            authGroup.setAgid(userAuthObj.getInteger("agid"));

            User user = new User();
            user.setUid(uid);

            userAuth.setEnterprise(enterprise);
            userAuth.setAuthGroup(authGroup);
            userAuth.setUser(user);
            userAuth.setUaid(userAuthObj.getInteger("uaid"));


            userAuth.setCreateUser(addUser.getUid());
            userAuth.setUpdateUser(addUser.getUid());
            addUserAuth.forEach(aua -> {
                if ((uid.equals(userAuth.getUser().getUid()) &&
                        aua.getEnterprise().getEid().equals(userAuth.getEnterprise().getEid()))
                        || userIsSupperAdmin(addUser.getUid())) {
                    // 超级管理员或者添加用户有该企业权限
                    // 过滤跟要修改以后id（uid）不同的数据
                    userAuths.add(userAuth);
                }
            });
        });
        try {
            userAuthRepository.saveAll(userAuths);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.EDIT_AUTH_GROUP_ERROR);
        }
    }


    public UserBindDriver getBindDriver(String token, String compid) throws ErpException {
        User user = tokenGetUser(token);
        return this.userBindDriverRepository.findUserBindDriverByUidAndCompid(user.getUid(), compid);
    }


    public void bindDriver(Integer uid, String compid, String driverCode) throws ErpException {
        UserBindDriver userBindDriver = new UserBindDriver();
        userBindDriver.setCompid(compid);
        userBindDriver.setDriverCode(driverCode);
        userBindDriver.setUid(uid);
        userBindDriver.setCreateTime(new Date());


        UserBindDriver oldBindDriver = this.userBindDriverRepository.findUserBindDriverByUidAndCompid(uid, compid);
        if (oldBindDriver != null) {
            this.userBindDriverRepository.delete(oldBindDriver);
        }
        oldBindDriver = this.userBindDriverRepository.findUserBindDriverByDriverCodeAndCompid(driverCode, compid);
        if (oldBindDriver != null) {
            this.userBindDriverRepository.delete(oldBindDriver);
        }
        this.userBindDriverRepository.save(userBindDriver);
    }

    //把userAuths赋值给userAuthVO进行数值传递
    private List<UserAuthVO> replaceUserAuth(List<UserAuth> userAuths) {
        List<UserAuthVO> userAuthVOS = new ArrayList<>();
        for (UserAuth userAuth : userAuths) {
            UserAuthVO userAuthVO = new UserAuthVO();
            BeanUtils.copyProperties(userAuth, userAuthVO);
            userAuthVOS.add(userAuthVO);

        }
        return userAuthVOS;
    }

/*
* 获取该用户的权限组
* */
    @Override
    public Integer getAuthGroupByUserAndCompid(Integer uid, Integer enterprise) {
       return userMapper.getAuthGroupByUserAndCompid(uid, enterprise);
    }

    /*
    * 判断此权限组是否包含此方法
    * */
    @Override
    public Integer judgementAuth(Integer authGroupID, String methodName) {
        return userMapper.judgementAuth(authGroupID, methodName);
    }


    /**
     * 从auth_value_new表中查询出该权限组的所有信息
     * */
    @Override
    public List<AuthValue> getAuthValue(Integer groupId,Integer pid) {

        return userMapper.getAuthValueByGroupId(groupId,pid);
    }

    @Override
    public List<AuthValueOld> getAuthValueOld(Integer groupId, Integer pid) {
        return userMapper.getAuthValueOld(groupId,pid);
    }

    @Override
    public List<User> selectAllUser() {

        return userMapper.selectAllUser();
    }

    /*根据eid 查询企业用户*/
    @Override
    public List<User> userAll(Integer eid) {

        return userMapper.userAll(eid);
    }





    /*================================================================================================================*/
    /*=======================================================redis do=================================================*/
    /*================================================================================================================*/


//    private User findByUserByIdRedis(Integer uid) {
//        return redisUtil.redisGetValue(RedisDataTypeEnums.USER.getValue() + uid);
//    }
//
//    private void cacheUser(User user) {
//        redisUtil.redisSetKey(RedisDataTypeEnums.USER.getValue() + user.getUid(), user);
//    }


//    private void cacheUsers(List<User> users) {
//        users.forEach(this::cacheUser);
//    }


}
