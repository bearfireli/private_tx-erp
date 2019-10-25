package com.hntxrj.server;

import com.hntxrj.entity.BdBuildAccounts;
import com.hntxrj.exception.ErpException;
import com.hntxrj.vo.LoginClibationVO;

import java.util.List;
import java.util.Map;

public interface LoginConstructionService {
    /**
     *
     * @param userName 账户名
     * @param passWord 密码
     */
    LoginClibationVO getAccountPassword(String userName, String passWord,String tokens) throws ErpException;

    void addUser(String userName, String passWord) throws ErpException;
}
