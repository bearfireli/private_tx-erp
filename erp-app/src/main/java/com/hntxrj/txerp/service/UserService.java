package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAuth;
import com.hntxrj.txerp.entity.base.UserLogin;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     * @return 登录用户
     * @throws ErpException throw ERP EXCEPTION
     */
    UserVO login(String phoneNumber, String password,
                 HttpServletRequest request) throws ErpException;

    UserVO login(String value, String type, String loginIp) throws ErpException;

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


    /**
     * get user list ande select user list
     *
     * @param user you can use username, phone, email, enterpriseId query,
     *             and username,phone, email is "like query"
     * @param page page number, page size default 10
     * @return UserVO list and pagination data
     */
    PageVO<UserAuth> getUser(User user, String token, Integer enterpriseId, HttpServletRequest request,
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

    UserLogin createUserLogin(Integer userId, String loginIp) throws ErpException;


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
     * @throws ErpException
     */
    List<Enterprise> getEnterprisesByToken(String token) throws ErpException;

    /**
     * 通过token获取该用户的拥有的企业，以企业id集合的形式返回
     *
     * @param token 用户令牌
     * @return 企业id集合
     * @throws ErpException
     */
    List<Integer> getEnterpriseIdsByToken(String token) throws ErpException;

    /**
     * 获取用户再某企业的权限组
     *
     * @param enterprise 企业id
     * @param token      用户token
     * @return 权限组id
     * @throws ErpException
     */
    Integer getUserAuthGroupByEnterprise(Integer enterprise, String token) throws ErpException;

    boolean userIsSupperAdmin(Integer uid);

    boolean userIsSupperAdmin(String token) throws ErpException;

    void setUserAuth(String params, String token,User newUser) throws ErpException;


    void checkPassword(String token, String password) throws ErpException;


    User setHeader(MultipartFile file, String token) throws ErpException;

    void getHeader(String token, HttpServletResponse response) throws ErpException;


    String getUserFavoriteConfig(String token) throws ErpException;

    void setUserFavoriteConfig(String token, String config) throws ErpException;
    void updateUserAdminStatus(Integer userId,String eadmin) throws ErpException;

}
