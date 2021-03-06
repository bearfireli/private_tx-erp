package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.LoginConstructionMapper;
import com.hntxrj.txerp.server.LoginConstructionService;
import com.hntxrj.txerp.util.RedisUtil;
import com.hntxrj.txerp.util.txutil.Code;
import com.hntxrj.txerp.vo.BuildAccountsVO;
import com.hntxrj.txerp.vo.LoginClibationVO;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

//登录业务层
@Service
public class LoginConstructionImpl implements LoginConstructionService {

    private final LoginConstructionMapper loginConstructionMapper;
    private final RedisUtil<LoginClibationVO> redisUtil;

    public LoginConstructionImpl(LoginConstructionMapper loginConstructionMapper, RedisUtil<LoginClibationVO> redisUtil) {
        this.loginConstructionMapper = loginConstructionMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 校对账户密码
     *
     * @param userName 账户名
     * @param passWord 密码
     * @param tokens   登录缓存
     */
    @Override
    public LoginClibationVO getAccountPassword(String userName, String passWord, String tokens) throws ErpException {
        LoginClibationVO loginClibationVO;
        if (null != tokens) {
            loginClibationVO = redisUtil.redisGetValue(tokens);
            System.out.println("是不是空:" + loginClibationVO);
            if (loginClibationVO != null) {
                System.out.println("TOkens" + loginClibationVO.getSupplierToKen());
            }
        } else {
            passWord = getMd5Password(passWord);
            System.out.println("进来准备插件数据库!");
            loginClibationVO = loginConstructionMapper.getAccountPassword(userName, passWord);
            if (loginClibationVO != null) {
                System.out.println("返回来数据不为空，正在添加Token");
                loginClibationVO.setSupplierToKen(Code.saltEncoderMd5(UUID.randomUUID().toString()));
                String token = loginClibationVO.getSupplierToKen();
                redisUtil.redisSetKey(token, loginClibationVO);
            } else {
                System.out.println("返回来的数据是空，都为null");
                throw new ErpException(ErrEumn.USER_LOGIN_ERROR);
            }
        }
        return loginClibationVO;
    }

    @Override
    public void addUser(String userName, String passWord, String buildName) throws ErpException {
        BuildAccountsVO user = loginConstructionMapper.findByBuildId(userName);
        if (user != null) {
            throw new ErpException(ErrEumn.ADD_USER_PHONE_EXIST);
        }
        passWord = getMd5Password(passWord);
        try {
            loginConstructionMapper.save(userName, passWord, buildName);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_ERROR);
        }

    }

    @Override
    public void updatePassword(String buildId, String oldPassword, String newPassword) throws ErpException {
        BuildAccountsVO buildAccountsVO = loginConstructionMapper.findUser(buildId);
        if (buildAccountsVO == null) {
            //用户不存在
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        oldPassword = getMd5Password(oldPassword);
        if (!oldPassword.equals(buildAccountsVO.getPassword())) {
            //旧密码错误
            throw new ErpException(ErrEumn.OLD_PASSWORD_ERROR);
        }
        //修改密码
        newPassword = getMd5Password(newPassword);
        loginConstructionMapper.updatePassword(buildId, newPassword);
    }

    //加密规则
    private String getMd5Password(
            String password) {
        // 加密规则
        // 2. 执行加密10次
        for (int i = 0; i < 10; i++) {
            password = DigestUtils
                    .md5DigestAsHex(password.getBytes())
                    .toUpperCase();
        }
        return password;
    }


}
