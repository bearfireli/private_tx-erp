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

    void addUser(String userName, String passWord,String buildName) throws ErpException;
}
