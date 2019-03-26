package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.AuthCodeService;
import com.hntxrj.txerp.service.UserAccountService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.UserVO;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.QUserAccount;
import com.hntxrj.txerp.repository.UserAccountRepository;
import com.hntxrj.txerp.entity.base.AuthCode;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAccount;
import com.hntxrj.txerp.entity.base.UserLogin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * (UserAccount)表服务实现类
 *
 * @author lhr
 * @since 2018-09-18 15:22:47
 */

@Service
public class UserAccountServiceImpl extends BaseServiceImpl implements UserAccountService {

    private JPAQueryFactory queryFactory;

    private final UserService userService;

    private final UserAccountRepository userAccountRepository;

    private final AuthCodeService authCodeService;

    public UserAccountServiceImpl(EntityManager entityManager, UserService userService,
                                  UserAccountRepository userAccountRepository, AuthCodeService authCodeService) {
        super(entityManager);
        this.userService = userService;
        this.userAccountRepository = userAccountRepository;
        this.authCodeService = authCodeService;
        this.queryFactory = getQueryFactory();
    }


    @Override
    public void bindAccount(String token, String authCode, String authCodeId, String acType, String value) throws ErpException {

        User user = userService.tokenGetUser(token);

        if (!authCodeService.pass(user.getPhone(), authCode, AuthCode.AU_TYPE_BING, authCodeId)) {
            throw new ErpException(ErrEumn.AUTH_CODE_ERROR);
        }

        UserAccount userAccount = userAccountRepository.
                findByAcTypeAndAcValue(acType, value);
        if (userAccount != null) {
            throw new ErpException(ErrEumn.THIS_OPENID_USED);
        }

        QUserAccount qUserAccount = QUserAccount.userAccount;
        if (queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(user.getUid()))
                .where(qUserAccount.acType.eq(acType))
                .fetchCount() > 0) {
            throw new ErpException(ErrEumn.THE_USER_ALREADY_BOUND_THIS_TYPE_ACCOUNT);
        }

        userService.deleteUserToken(user.getUid());

        userAccount = new UserAccount();
        userAccount.setUid(user.getUid());
        userAccount.setAcType(acType);
        userAccount.setAcValue(value);
        userAccountRepository.save(userAccount);
    }

    @Override
    public void bindAccount(String token, String acType, String value) throws ErpException {
        if (StringUtils.isEmpty(value)) {
            throw new ErpException(ErrEumn.BIND_VALUE_NULL);
        }
        User user = userService.tokenGetUser(token);
        UserAccount userAccount = userAccountRepository.
                findByAcTypeAndAcValue(acType, value);
        if (userAccount != null) {
            throw new ErpException(ErrEumn.THIS_OPENID_USED);
        }

        QUserAccount qUserAccount = QUserAccount.userAccount;
        if (queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(user.getUid()))
                .where(qUserAccount.acType.eq(acType))
                .fetchCount() > 0) {
            throw new ErpException(ErrEumn.THE_USER_ALREADY_BOUND_THIS_TYPE_ACCOUNT);
        }

        userService.deleteUserToken(user.getUid());

        userAccount = new UserAccount();
        userAccount.setUid(user.getUid());
        userAccount.setAcType(acType);
        userAccount.setAcValue(value);
        userAccountRepository.save(userAccount);
    }

    @Override
    public void unbindAccount(String token, String acType) throws ErpException {
        User user = userService.tokenGetUser(token);

        QUserAccount qUserAccount = QUserAccount.userAccount;
        UserAccount userAccount = queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(user.getUid())
                        .and(qUserAccount.acType.eq(acType)))
                .fetchOne();
        if (userAccount != null) {
            userAccountRepository.delete(userAccount);
        }
    }

    @Override
    public void unbind(Integer uid, String acType) throws ErpException {

        QUserAccount qUserAccount = QUserAccount.userAccount;
        UserAccount userAccount = queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(uid)
                        .and(qUserAccount.acType.eq(acType)))
                .fetchOne();
        if (userAccount != null) {
            userAccountRepository.delete(userAccount);
        }
    }

    @Override
    public UserVO userOpenIdGetUser(String type, String openId, String ip) throws ErpException {

        QUserAccount qUserAccount = QUserAccount.userAccount;
        UserAccount userAccount = queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.acType.eq(type)
                        .and(qUserAccount.acValue.eq(openId)))
                .fetchOne();
        if (userAccount == null) {
            throw new ErpException(ErrEumn.NOT_BIND_ACCOUNT);
        }

        UserVO userVO = userService.findUserById(userAccount.getUid(), true);

        UserLogin userLogin = userService.createUserLogin(userVO.getUid(), ip);
        userVO.setToken(userLogin.getUserToken());

        return userVO;
    }

    @Override
    public List<String> binds(String token) throws ErpException {
        QUserAccount qUserAccount = QUserAccount.userAccount;

        User user = userService.tokenGetUser(token);
        List<UserAccount> userAccounts = queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(user.getUid()))
                .where(qUserAccount.acValue.isNotNull())
                .fetch();


        List<String> result = new ArrayList<>();
        userAccounts.forEach(userAccount -> {
            result.add(userAccount.getAcType());
        });
        return result;
    }

    @Override
    public List<UserAccount> getUserAccounts(Integer uid) {
        QUserAccount qUserAccount = new QUserAccount("ua");
        return queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.uid.eq(uid)).fetch();
    }
}