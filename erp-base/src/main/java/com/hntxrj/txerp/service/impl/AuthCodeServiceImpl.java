package com.hntxrj.txerp.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.hntxrj.txerp.service.AuthCodeService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.AliMessage;
import com.hntxrj.txerp.entity.base.QAuthCode;
import com.hntxrj.txerp.repository.AuthCodeRepository;
import com.hntxrj.txerp.entity.base.AuthCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
public class AuthCodeServiceImpl extends BaseServiceImpl implements AuthCodeService {

    private final AuthCodeRepository authCodeRepository;
    private JPAQueryFactory queryFactory;

    public AuthCodeServiceImpl(AuthCodeRepository authCodeRepository, EntityManager entityManager) {
        super(entityManager);
        this.authCodeRepository = authCodeRepository;
        queryFactory = getQueryFactory();
    }

    @Override
    public String sendAuthCode(String phoneNumber, Integer type) throws ErpException, ClientException {
        AuthCode authCode = new AuthCode();
        authCode.setAuId(UUID.randomUUID().toString());
        authCode.setAuPhone(phoneNumber);
        authCode.setAuType(type);
        authCode.setAuValue(AliMessage.makeAuthCode(6));
        authCode.setDel(0);
        log.info("【authCode】 authCode={}", authCode);//打印

        String sendCode = AliMessage.sendMessage(phoneNumber, authCode.getAuValue(), AliMessage.TEMPCODE_1);//提示消息
        if ("OK".equals(sendCode)) {
            authCodeRepository.save(authCode);//成功则提交
            return authCode.getAuId();
        } else {
            throw new ErpException(ErrEumn.SEND_AUTH_CODE_ERROR.getCode(), sendCode);
        }//发送验证码失败

    }


    @Override
    public boolean pass(String phoneNumber, String code, Integer type, String id) {
        // need verification time
        QAuthCode qAuthCode = QAuthCode.authCode;
        long time = 20 * 60 * 1000;
        AuthCode authCode = queryFactory.selectFrom(qAuthCode)
                .where(qAuthCode.auId.eq(id))
                .where(qAuthCode.auType.eq(type))
                .where(qAuthCode.auPhone.eq(phoneNumber))
                .where(qAuthCode.auValue.eq(code))
                .where(qAuthCode.del.eq(0))//未删除
                .where(qAuthCode.createTime.between(new Timestamp(System.currentTimeMillis() - time),//System.currentTimeMillis转换类型
                        new Timestamp(System.currentTimeMillis())))// 20分钟内
                .fetchFirst();
        if (authCode == null) {
            return false;
        }
        authCode.setDel(1);
        authCodeRepository.save(authCode);
        return true;
    }
}
