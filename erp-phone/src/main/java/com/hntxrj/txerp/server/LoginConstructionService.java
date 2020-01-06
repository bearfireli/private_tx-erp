package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.LoginClibationVO;

public interface LoginConstructionService {
    /**
     *
     * @param userName 账户名
     * @param passWord 密码
     */
    LoginClibationVO getAccountPassword(String userName, String passWord,String tokens) throws ErpException;

    /**
     * 增加用户
     * @param userName 账户名
     * @param passWord 用户密码
     * @param buildName 施工方名称
     * */
    void addUser(String userName, String passWord,String buildName) throws ErpException;

    /**
     * 修改密码
     *
     * @param buildId 施工方id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * */
    void updatePassword(String buildId, String oldPassword, String newPassword) throws ErpException;
}
