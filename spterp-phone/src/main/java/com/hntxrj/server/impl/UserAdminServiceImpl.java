package com.hntxrj.server.impl;

import com.hntxrj.dao.UserAdminDao;
import com.hntxrj.entity.UserAdmin;
import com.hntxrj.server.UserAdminService;
import com.hntxrj.util.txutil.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

/**
 * 管理员操作
 * @author lhr
 * @create 2018/1/22
 */
@Service
@Scope("prototype")
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    private final UserAdminDao userAdminDao;

    @Autowired
    public UserAdminServiceImpl(UserAdminDao userAdminDao) {
        this.userAdminDao = userAdminDao;
    }


    @Override
    public UserAdmin login(String name, String password, String ip) throws SQLException {
        UserAdmin userAdmin = userAdminDao.findById(name).orElseThrow(SQLException::new);
        log.info("【userAdmin】 userAdmin={}", userAdmin);
        if(userAdmin == null){
            return null;
        }

        if (!userAdmin.getAdminPassword().equals(Code.saltEncoderMd5(password))){
            // 用户名或密码错误
            return null;
        }
        // 登录成功
        userAdmin.setAdminToken(
                Code.saltEncoderMd5(UUID.randomUUID().toString()));

        userAdmin.setAdminLastLoginTime(new Date(System.currentTimeMillis()));

        userAdmin.setAdminLoginIp(ip);

        return userAdminDao.save(userAdmin);

    }

    @Override
    public UserAdmin addAdmin(String name, String password,
                              String compid, Integer status) {
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setAdminName(name);
        userAdmin.setAdminPassword(Code.saltEncoderMd5(password));
        userAdmin.setAdminCompid(compid);
        userAdmin.setAdminStatus(status);
        System.out.println(userAdmin);
        return userAdminDao.save(userAdmin);
    }

    @Override
    public Boolean isLogin(String name, String ip, String token) throws SQLException {

        UserAdmin userAdmin = userAdminDao.findById(name).orElseThrow(SQLException::new);

        if (userAdmin == null){
            return false;
        }

        //todo 未验证ip
        return userAdmin.getAdminToken().equals(token);
    }

    @Override
    public UserAdmin updateAdminPassword(String updateAdminName,
                                         String adminName, String oldPassword,
                                         String newPassword)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {

        UserAdmin updateAdmin = userAdminDao.findById(updateAdminName).orElseThrow(SQLException::new);

        if (updateAdminName.equals(adminName)){
            // 自行进行修改
            if (updateAdmin.getAdminPassword().equals(Code.saltEncoderMd5(oldPassword))){
                // 老密码正确
                updateAdmin.setAdminPassword(Code.saltEncoderMd5(newPassword));
                return userAdminDao.save(updateAdmin);
            }else {
                // 老密码错误.
                return null;
            }
        }else {
            // 平台管理员协助修改
            UserAdmin admin = userAdminDao.findById(adminName).orElseThrow(SQLException::new);;
            if (isSuperAdmin(admin)){
                updateAdmin.setAdminPassword(Code.saltEncoderMd5(newPassword));
                return userAdminDao.save(updateAdmin);
            }else {
                return null;
            }
        }
    }

    @Override
    public Boolean updateAdminStatus(String updateAdminName) throws SQLException {
        UserAdmin admin = userAdminDao.findById(updateAdminName).orElseThrow(SQLException::new);;

        // 判断是否是超管，超管无法被禁用
        if("00".equals(admin.getAdminCompid())){
            return false;
        }

        if (admin.getAdminStatus().equals(UserAdmin.STATUS_USE)){
            // 如果停用删除token
            admin.setAdminToken(Code.saltEncoderMd5(UUID.randomUUID().toString()));
            admin.setAdminStatus(UserAdmin.STATUS_NOT_USE);
        }else {
            admin.setAdminStatus(UserAdmin.STATUS_USE);
        }
        userAdminDao.save(admin);
        return true;
    }

    @Override
    public Page<UserAdmin> findUserAdmin(String compid, Pageable pageable) {
        if("00".equals(compid) || compid == null){
            return userAdminDao.findAll(pageable);
        }else {
            return userAdminDao.findByAdminCompid(compid, pageable);
        }
    }

    @Override
    public UserAdmin findUserAdmin(String adminName) throws SQLException {
        return userAdminDao.findById(adminName).orElseThrow(SQLException::new);
    }

    @Override
    public UserAdmin addUserAdmin(UserAdmin userAdmin) {
        return userAdminDao.save(userAdmin);
    }

    @Override
    public UserAdmin updateAdmin(UserAdmin userAdmin) {
        return userAdminDao.save(userAdmin);
    }


    /**
     * 是否为超管
     * @return  true，超管
     */
    private Boolean isSuperAdmin(UserAdmin userAdmin){
        return "00".equals(userAdmin.getAdminCompid());
    }
}
