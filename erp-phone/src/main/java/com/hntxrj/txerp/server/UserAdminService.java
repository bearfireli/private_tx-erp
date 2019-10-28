package com.hntxrj.txerp.server;

import com.hntxrj.txerp.entity.UserAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 管理员操作
 * @author lhr
 * @create 2018/1/22
 */
public interface UserAdminService {


    /**
     * 管理员登录
     * @param name      管理员姓名
     * @param password  管理员密码
     * @return
     */
    UserAdmin login(String name, String password, String ip) throws SQLException;


    /**
     * 添加企业管理员
     * @param name      管理员姓名
     * @param password  管理员密码
     * @param compid    管理员所属企业
     * @param status    管理员状态
     * @return          管理员数据
     */
    UserAdmin addAdmin(String name, String password, String compid, Integer status);


    /**
     * 是否登录
     * @param name      用户名
     * @param ip        客户端ip
     * @param token     令牌
     * @return          是否登录
     */
    Boolean isLogin(String name, String ip, String token) throws SQLException;


    /**
     * 更新密码，分为自行修改和普通管理员进行修改
     * 自行修改需要oldPassword,
     * 管理员修改需要管理员的adminName和token
     * @param updateAdminName    欲被修改的管理员名称
     * @param adminName          进行修改操作的管理员名称
     * @param oldPassword        旧的密码
     * @param newPassword        新的密码
     * @return                   如果修改成功返回修改成功的对象，如果不成功返回null
     */
    UserAdmin updateAdminPassword(String updateAdminName, String adminName,
                                  String oldPassword, String newPassword)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException;


    /**
     * 修改管理员状态，取反
     * @param updateAdminName   欲修改的管理员名称
     * @return
     */
    Boolean updateAdminStatus(String updateAdminName) throws SQLException;

    Page<UserAdmin> findUserAdmin(String compid, Pageable pageable);

    UserAdmin findUserAdmin(String adminName) throws SQLException;


    UserAdmin addUserAdmin(UserAdmin userAdmin);


    UserAdmin updateAdmin(UserAdmin userAdmin);

}
