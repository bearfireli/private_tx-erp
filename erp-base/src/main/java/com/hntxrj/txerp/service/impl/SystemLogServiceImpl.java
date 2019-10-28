package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.SystemLogService;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.system.QSystemLog;
import com.hntxrj.txerp.entity.system.SystemLog;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.repository.SystemLogRepository;
import com.hntxrj.txerp.vo.PageVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;


@Service
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;
    private JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SystemLogServiceImpl(SystemLogRepository systemLogRepository,
                                EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.systemLogRepository = systemLogRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void system(String action, String uri, Integer enterprise) throws ErpException {
        SystemLog systemLog = new SystemLog();
        systemLog.setSlId("s" + new Date().getTime());
        systemLog.setAction(action);
        systemLog.setUri(uri);
        systemLog.setEnterprise(enterprise);
        systemLog.setUid(0);
        systemLog.setType(SystemLog.LOG_TYPE_SYSTEM);
        try {
            systemLogRepository.save(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_SYSTEM_LOG_ERROR);
        }
    }

    @Override
    public void operate(Integer uid, String action, String uri, Integer enterprise) throws ErpException {
        SystemLog systemLog = new SystemLog();
        systemLog.setSlId("o" + new Date().getTime());
        systemLog.setAction(action);
        systemLog.setUri(uri);
        systemLog.setEnterprise(enterprise);
        systemLog.setUid(uid);
        systemLog.setType(SystemLog.LOG_OPERATE);
        try {
            systemLogRepository.save(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_SYSTEM_LOG_ERROR);
        }
    }

    @Override
    public void select(Integer uid, String action, String uri, Integer enterprise) throws ErpException {
        SystemLog systemLog = new SystemLog();
        systemLog.setSlId("ss" + new Date().getTime());
        systemLog.setAction(action);
        systemLog.setUri(uri);
        systemLog.setEnterprise(enterprise);
        systemLog.setUid(uid);
        systemLog.setType(SystemLog.LOG_SELECT);
        try {
            systemLogRepository.save(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_SYSTEM_LOG_ERROR);
        }
    }


    @Override
    public PageVO<SystemLog> list(Integer uid, Integer enterprise, String uri, long page, long pageSize) {
        QSystemLog qSystemLog = new QSystemLog("systemlog");
        QUser qUser = new QUser("user");
        QEnterprise qEnterprise = QEnterprise.enterprise;
        BooleanBuilder builder = new BooleanBuilder();
        if (uid != null && uid != 0) {
            builder.and(qSystemLog.uid.eq(uid));
        }
        if (enterprise != null && enterprise != 0) {
            builder.and(qSystemLog.enterprise.eq(enterprise));
        }
        if (StringUtils.isEmpty(uri)) {
            builder.and(qSystemLog.uri.eq(uri));
        }
        JPAQuery<SystemLog> SystemLogVoJPAQuery = queryFactory.select(
                Projections.bean(
                        SystemLog.class,
                        qSystemLog.slId,
                        qSystemLog.createTime,
                        qSystemLog.action,
                        qSystemLog.uri,
                        qSystemLog.type,
                        qUser.uid,
                        qEnterprise.eid
                )
        ).from(qSystemLog)
                .leftJoin(qUser).on(qUser.uid.eq(qSystemLog.uid))
                .leftJoin(qEnterprise).on(qEnterprise.eid.eq(qSystemLog.enterprise))
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);
        PageVO<SystemLog> pageVO = new PageVO<>();
        pageVO.init(SystemLogVoJPAQuery, pageSize);
        return pageVO;
    }

    @Override
    public void cleanAllLog(Integer enterprise) {
        jdbcTemplate.execute("delete from system_log where enterprise='" + enterprise + "'");

    }

    @Override
    public void cleanAllLogByType(Integer enterprise, Integer type) {
        String sql = "delete from system_log where enterprise="+enterprise+" and type="+type;
        jdbcTemplate.execute(sql);
    }
}
