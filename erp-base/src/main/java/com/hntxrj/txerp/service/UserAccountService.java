package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.UserAccount;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.UserVO;

import java.util.List;

/**
 * (UserAccount)表服务接口
 *
 * @author lhr
 * @since 2018-09-18 15:22:47
 */
public interface UserAccountService {

    /**
     * 绑定三方账号
     *
     * @param token  用户token
     * @param acType 绑定帐号类型
     * @param value  绑定值
     */
    void bindAccount(String token, String authCode, String authCodeId, String acType, String value) throws ErpException;

    void bindAccount(String token, String acType, String value) throws ErpException;

    /**
     * 取消绑定第三方账号
     *
     * @param token  用户令牌
     * @param acType 绑定帐号类型
     */
    void unbindAccount(String token, String acType) throws ErpException;


    void unbind(Integer uid, String acType) throws ErpException;

    UserVO userOpenIdGetUser(String type, String openId, String ip,String loginUa) throws ErpException;


    List<String> binds(String token) throws ErpException;


    List<UserAccount> getUserAccounts(Integer uid);
}