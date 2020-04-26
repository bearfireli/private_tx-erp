package com.hntxrj.txerp.service;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.UserAuthVO;
import com.hntxrj.txerp.vo.UserLoginVO;
import com.hntxrj.txerp.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户服务
 *
 * @author lhr
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param request     HTTP SERVLET Request 对象
     * @param loginUa     不同项目登陆时的标识；
     *                    例如：手机erp项目登录时loginUa的值为:erpPhone;司机App登录时，loginUa的值是:erpDriver;
     * @return 登录用户
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO login(String phoneNumber, String password,
                 HttpServletRequest request, String loginUa, String version) throws ErpException;

    UserVO login(String value, String type, String loginIp, String loginUa) throws ErpException;

    /**
     * login out
     *
     * @param token 用户token
     */
    void loginOut(String token);

    /**
     * use token get user object
     *
     * @param token The token obtained by the user login
     * @return user data
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO tokenCanUse(String token) throws ErpException;

    UserVO tokenCheck(String token) throws ErpException;


    /**
     * get user list ande select user list
     *
     * @param user you can use username, phone, email, enterpriseId query,
     *             and username,phone, email is "like query"
     * @param page page number, page size default 10
     * @return UserVO list and pagination data
     */
    PageVO<UserAuthVO> getUser(User user, String token, Integer enterpriseId, HttpServletRequest request,
                               int page, int pageSize) throws ErpException;


    /**
     * select user by uid
     *
     * @param userId          user.uid
     * @param showPhoneNumber true show phone number,
     *                        false hide user phone number.
     * @return user object vo
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO findUserById(Integer userId, boolean showPhoneNumber) throws ErpException;

    UserVO findUserdetails(Integer userId) throws ErpException;

    /**
     * find by user uid, Return null when the user does not exist
     *
     * @param userId user.uid
     * @return user object or null
     */
    User findById(Integer userId) throws ErpException;

    /**
     * add one user
     *
     * @param user user object
     * @return added user
     * @throws ErpException throw ERP EXCEPTION
     */
    User addUser(User user) throws ErpException;

    /**
     * update user
     *
     * @param user user object
     * @return updated user
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO updateUser(User user) throws ErpException;


    /*修改用户密码*/
    UserVO updatePassword(String oldPassword, String newPassword,
                          String token) throws ErpException;

    /**
     * update user password with administrator
     *
     * @param token    administrator token
     * @param uid      user id
     * @param password new password
     * @throws ErpException not find user or is not administrator
     */
    void initPassword(String token, Integer uid, String password) throws ErpException;

    /**
     * delete user
     *
     * @param user user object,
     *             you can send this function one user only have uid object
     * @return deleted user
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO delUser(User user) throws ErpException;

    /**
     * 通过userid 删除该用户所有token
     *
     * @param userId 用户id
     */
    void deleteUserToken(Integer userId);

    User tokenGetUser(String token) throws ErpException;


    void phoneIsExist(String phone) throws ErpException;

    List<User> getUsers(Integer[] uids);

    UserLogin createUserLogin(Integer userId, String loginIp, String loginUa) throws ErpException;


    /**
     * 通过用户id获取企业集合
     *
     * @param uid 用户id
     * @return 返回企业集合
     */
    List<Enterprise> gerEnterprisesById(Integer uid) throws ErpException;

    /**
     * 通过token获取企业，以企业集合形式返回
     *
     * @param token 登录令牌
     * @return 企业集合
     */
    List<Enterprise> getEnterprisesByToken(String token) throws ErpException;

    /**
     * 通过token获取该用户的拥有的企业，以企业id集合的形式返回
     *
     * @param token 用户令牌
     * @return 企业id集合
     */
    List<Integer> getEnterpriseIdsByToken(String token) throws ErpException;

    /**
     * 获取用户再某企业的权限组
     *
     * @param enterprise 企业id
     * @param token      用户token
     * @return 权限组id
     */
    Integer getUserAuthGroupByEnterprise(Integer enterprise, String token) throws ErpException;

    /**
     * 根据用户id判断用户是否是超级管理员
     */
    boolean userIsSupperAdmin(Integer uid);

    /**
     * 根据token判断用户是否是超级管理员
     */
    boolean userIsSupperAdmin(String token) throws ErpException;

    /**
     * 老用户更改权限
     *
     * @param params 权限组json对象
     */
    void updateUserAuth(String params, String token) throws ErpException;

    /**
     * 新用户添加权限
     *
     * @param params 权限组json对象
     * @param user   用户对象
     */
    void addUserAuth(String params, String token, User user) throws ErpException;


    /**
     * 判断密码是否正确
     */
    void checkPassword(String token, String password) throws ErpException;


    /**
     * 上传头像
     */
    User setHeader(MultipartFile file, String token) throws ErpException;

    /**
     * 获取头像
     */
    void getHeader(String token, HttpServletResponse response) throws ErpException;


    /**
     * 获取手机erp用户常用模块
     */
    String getUserFavoriteConfig(String token) throws ErpException;

    /**
     * 设置手机erp用户常用模块
     */
    void setUserFavoriteConfig(String token, String config) throws ErpException;

    /**
     * 用于更改用户的超级管理员权限
     *
     * @param userId 用户id
     * @param eadmin 是否是超级管理员（1：是；   0：不是）
     */
    void updateUserAdminStatus(Integer userId, String eadmin) throws ErpException;

    /**
     * 获取用户绑定司机
     */
    UserBindDriver getBindDriver(String token, String compid) throws ErpException;

    /**
     * 给用户绑定司机
     *
     * @param uid        用户id
     * @param compid     企业id
     * @param driverCode 司机编号
     */
    void bindDriver(Integer uid, String compid, String driverCode) throws ErpException;

    /**
     * 通过用户和公司名称得到权限组
     */
    Integer getAuthGroupByUserAndCompid(Integer uid, Integer enterprise);

    /**
     * 判定权限组是否具有该方法
     */
    Integer judgementAuth(Integer authGroupID, String methodName);


    /**
     * 从auth_value_new表中查询出该权限组的所有信息
     */
    List<AuthValue> getAuthValue(Integer groupId, Integer pid);

    /**
     * 从auth_value表中查询出该权限组的所有信息
     *
     * @param groupId 权限组id
     * @param pid     项目代号
     */
    List<AuthValueOld> getAuthValueOld(Integer groupId, Integer pid);

    /**
     * 查询所有企业所有用户
     */
    List<User> selectAllUser(Integer compid, String userName);

    /**
     * 根据企业id查询用户
     */
    List<User> userAll(Integer eid);

    /**
     * 查询用户登录信息列表
     *
     * @param compid   企业id
     * @param userName 用户姓名
     * @param phoneNum 手机号
     * @param page     当前页码
     * @param pageSize 每页大小
     */
    PageVO<UserLoginVO> userLoginList(String compid, String userName, String phoneNum, Integer page, Integer pageSize);

    /**
     * 判断用户是否有超级管理员的权限
     *
     * @param token 用户令牌
     * @param data  用户设置的权限组对象
     */
    void checkSuperAuth(String token, JSONObject data) throws ErpException;
}
