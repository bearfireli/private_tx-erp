package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.EnterpriseAfterSaleLogService;
import com.hntxrj.txerp.service.UserAccountService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.util.DingUtil;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QEnterpriseAfterSaleLog;
import com.hntxrj.txerp.entity.base.QProject;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.util.EntityTools;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.EnterpriseAfterSaleLogVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.repository.EnterpriseAfterSaleLogRepository;
import com.hntxrj.txerp.entity.base.EnterpriseAfterSaleLog;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAccount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j

@Service
public class EnterpriseAfterSaleLogServiceImpl implements EnterpriseAfterSaleLogService {

    private final UserAccountService userAccountService;
    private final EnterpriseAfterSaleLogRepository enterpriseAfterSaleLogRepository;
    private JPAQueryFactory queryFactory;
    private final UserService userService;

    @Autowired
    public EnterpriseAfterSaleLogServiceImpl(UserAccountService userAccountService,
                                             EnterpriseAfterSaleLogRepository enterpriseAfterSaleLogRepository,
                                             EntityManager entityManager, UserService userService) {
        this.userAccountService = userAccountService;
        this.enterpriseAfterSaleLogRepository = enterpriseAfterSaleLogRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.userService = userService;
    }

    @Override
    public PageVO<EnterpriseAfterSaleLogVO> enterpriseAfterSaleLogList(Integer enterprise, long page, long pageSize) {
        QEnterpriseAfterSaleLog qEnterpriseAfterSaleLog = QEnterpriseAfterSaleLog.enterpriseAfterSaleLog;
        QUser qdeclarerUser = new QUser("qdeclarerUser");
        QUser qProcessingUser = new QUser("qProcessingUser");
        QEnterprise qEnterprise = QEnterprise.enterprise;
        QProject qProject = QProject.project;
        BooleanBuilder builder = new BooleanBuilder();
        if (enterprise != null && enterprise != 0) {
            builder.and(qEnterpriseAfterSaleLog.enterprise.eq(enterprise));
        }

        JPAQuery<EnterpriseAfterSaleLogVO> enterpriseAfterSaleLogVOJPAQuery = queryFactory.select(
                Projections.bean(
                        EnterpriseAfterSaleLogVO.class,
                        qEnterpriseAfterSaleLog.easlid,
                        qEnterpriseAfterSaleLog.logTitle,
                        qEnterpriseAfterSaleLog.declarerUid,
                        qdeclarerUser.username.as("declarerName"),
                        qEnterpriseAfterSaleLog.declarationTime,
                        qEnterpriseAfterSaleLog.grade,
                        qEnterpriseAfterSaleLog.linkMan,
                        qEnterpriseAfterSaleLog.linkTel,
                        qEnterpriseAfterSaleLog.problem,
                        qEnterpriseAfterSaleLog.processingUid,
                        qProcessingUser.username.as("processingUserName"),
                        qEnterpriseAfterSaleLog.processingType,
                        qEnterpriseAfterSaleLog.beginTime,
                        qEnterpriseAfterSaleLog.estimatedStartTime,
                        qEnterpriseAfterSaleLog.estimatedOverTime,
                        qEnterpriseAfterSaleLog.endTime,
                        qEnterpriseAfterSaleLog.sumup,
                        qEnterpriseAfterSaleLog.files,
                        qEnterpriseAfterSaleLog.status,
                        qEnterpriseAfterSaleLog.enterprise,
                        qEnterprise.epShortName.as("enterpriseName"),
                        qEnterpriseAfterSaleLog.project,
                        qProject.pName.as("projectName")
                )
        ).from(qEnterpriseAfterSaleLog)
                .leftJoin(qEnterprise).on(qEnterprise.eid.eq(qEnterpriseAfterSaleLog.enterprise))
                .leftJoin(qdeclarerUser).on(qEnterpriseAfterSaleLog.declarerUid.eq(qdeclarerUser.uid))
                .leftJoin(qProcessingUser).on(qProcessingUser.uid.eq(qEnterpriseAfterSaleLog.processingUid))
                .leftJoin(qProject).on(qProject.pid.eq(qEnterpriseAfterSaleLog.project))
                .where(builder)
                .orderBy(qEnterpriseAfterSaleLog.status.asc())
                .offset(((page - 1) * pageSize))
                .limit(pageSize);

        PageVO<EnterpriseAfterSaleLogVO> pageVO = new PageVO<>();
        pageVO.init(enterpriseAfterSaleLogVOJPAQuery, pageSize);

        return pageVO;
    }

    @Override
    @Transactional
    public EnterpriseAfterSaleLog saveEnterpriseAfterSaleLog(
            EnterpriseAfterSaleLog enterpriseAfterSaleLog, String token)
            throws ErpException {
        User user = userService.tokenGetUser(token);
        EntityTools.setEntityDefaultValue(enterpriseAfterSaleLog);
        enterpriseAfterSaleLog.setDeclarerUid(user.getUid());
        enterpriseAfterSaleLog.setDeclarationTime(new Date());
        enterpriseAfterSaleLog.setFiles(new JSONArray().toJSONString());
        enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.save(enterpriseAfterSaleLog);
        try {
            log.info("【添加售后记录】enterpriseAfterSaleLog={}", enterpriseAfterSaleLog);
            List<UserAccount> processingAccount = userAccountService
                    .getUserAccounts(enterpriseAfterSaleLog.getProcessingUid());
            log.info("【处理人绑定三方信息】processingAccount={}", processingAccount);
            for (UserAccount account : processingAccount) {
                if (account.getAcType().equals("ding")) {
                    String recordId = sendBeginWorkrecord(
                            account.getAcValue(), enterpriseAfterSaleLog.getEaslid());
                    enterpriseAfterSaleLog.setRecordId(recordId);
                    // TODO: 入库存入代办id
                }
            }
            return enterpriseAfterSaleLog;
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ENTERPRISE_AFTER_SALE_LOG_ADD_ERROR);
        }
    }


    @Override
    public EnterpriseAfterSaleLogVO enterpriseAfterSaleLogInfo(Integer logId, String token) throws ErpException {
        QEnterpriseAfterSaleLog qEnterpriseAfterSaleLog = QEnterpriseAfterSaleLog.enterpriseAfterSaleLog;
        QUser qdeclarerUser = new QUser("qdeclarerUser");
        QUser qProcessingUser = new QUser("qProcessingUser");
        QEnterprise qEnterprise = QEnterprise.enterprise;
        QProject qProject = QProject.project;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEnterpriseAfterSaleLog.easlid.eq(logId));

        EnterpriseAfterSaleLogVO enterpriseAfterSaleLogVO = queryFactory.select(
                Projections.bean(
                        EnterpriseAfterSaleLogVO.class,
                        qEnterpriseAfterSaleLog.easlid,
                        qEnterpriseAfterSaleLog.logTitle,
                        qEnterpriseAfterSaleLog.declarerUid,
                        qdeclarerUser.username.as("declarerName"),
                        qEnterpriseAfterSaleLog.declarationTime,
                        qEnterpriseAfterSaleLog.grade,
                        qEnterpriseAfterSaleLog.linkMan,
                        qEnterpriseAfterSaleLog.linkTel,
                        qEnterpriseAfterSaleLog.problem,
                        qEnterpriseAfterSaleLog.processingUid,
                        qProcessingUser.username.as("processingUserName"),
                        qEnterpriseAfterSaleLog.processingType,
                        qEnterpriseAfterSaleLog.estimatedStartTime,
                        qEnterpriseAfterSaleLog.estimatedOverTime,
                        qEnterpriseAfterSaleLog.beginTime,
                        qEnterpriseAfterSaleLog.endTime,
                        qEnterpriseAfterSaleLog.sumup,
                        qEnterpriseAfterSaleLog.files,
                        qEnterpriseAfterSaleLog.status,
                        qEnterpriseAfterSaleLog.enterprise,
                        qEnterprise.epShortName.as("enterpriseName"),
                        qEnterpriseAfterSaleLog.project,
                        qProject.pName.as("projectName"),
                        qEnterpriseAfterSaleLog.recordId
                )
        ).from(qEnterpriseAfterSaleLog)
                .leftJoin(qEnterprise).on(qEnterprise.eid.eq(qEnterpriseAfterSaleLog.enterprise))
                .leftJoin(qdeclarerUser).on(qEnterpriseAfterSaleLog.declarerUid.eq(qdeclarerUser.uid))
                .leftJoin(qProcessingUser).on(qProcessingUser.uid.eq(qEnterpriseAfterSaleLog.processingUid))
                .leftJoin(qProject).on(qProject.pid.eq(qEnterpriseAfterSaleLog.project))
                .where(builder)
                .fetchFirst();


        User user = userService.tokenGetUser(token);

        // 当前以后是申办人并且状态为已完成,并且record id 部位空
        if (user.getUid().equals(enterpriseAfterSaleLogVO.getDeclarerUid()) &&
                enterpriseAfterSaleLogVO.getStatus() == 2 &&
                !"".equals(enterpriseAfterSaleLogVO.getRecordId())) {
            List<UserAccount> ProcessingUidList = userAccountService.getUserAccounts
                    (user.getUid());
            for (UserAccount proc : ProcessingUidList) {
                if (proc.getAcType().equals("ding")) {
                    DingUtil.overWorkrecord(proc.getAcValue(), enterpriseAfterSaleLogVO.getRecordId());
                }
            }
        }

        return enterpriseAfterSaleLogVO;
    }

    @Override
    public void uploadFile(JSONArray files, Integer logId) throws ErpException {
        EnterpriseAfterSaleLog enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.findById(logId)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_AFTER_SALE_LOG_NOT_FOUND));
        JSONArray oldFiles = JSONArray.parseArray(enterpriseAfterSaleLog.getFiles());
        for (Object fileObj : files) {
            oldFiles.add(JSONObject.parseObject(JSON.toJSONString(fileObj)));
        }
        enterpriseAfterSaleLog.setFiles(oldFiles.toJSONString());
        enterpriseAfterSaleLogRepository.save(enterpriseAfterSaleLog);
    }

    @Override
    public void acceptTask(String token, Integer logId) throws ErpException {
        EnterpriseAfterSaleLog enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.findById(logId)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_AFTER_SALE_LOG_NOT_FOUND));

        User user = userService.tokenGetUser(token);
        enterpriseAfterSaleLog.setBeginTime(new Date());
        enterpriseAfterSaleLog.setProcessingUid(user.getUid());
        enterpriseAfterSaleLog.setStatus(1);
        enterpriseAfterSaleLogRepository.save(enterpriseAfterSaleLog);
    }

    @Override
    @Transactional
    public EnterpriseAfterSaleLog overAfterSale(String token, String processingType,
                                                String sumup, Integer logId) throws ErpException {
        EnterpriseAfterSaleLog enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.findById(logId)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_AFTER_SALE_LOG_NOT_FOUND));
        enterpriseAfterSaleLog.setStatus(2);
        enterpriseAfterSaleLog.setEndTime(new Date());
        enterpriseAfterSaleLog.setProcessingType(processingType);
        enterpriseAfterSaleLog.setSumup(sumup);

        // 代办事项删除。
        List<UserAccount> processingUidList = userAccountService.getUserAccounts(
                enterpriseAfterSaleLog.getProcessingUid());
        for (UserAccount proc : processingUidList) {
            if (proc.getAcType().equals("ding")) {
                log.info("【结束处理人代办事项】recirdId={}", enterpriseAfterSaleLog.getRecordId());
                DingUtil.overWorkrecord(proc.getAcValue(), enterpriseAfterSaleLog.getRecordId());
            }
        }
        List<UserAccount> accountList = userAccountService.getUserAccounts(enterpriseAfterSaleLog.getDeclarerUid());
        for (UserAccount account : accountList) {
            if (account.getAcType().equals("ding")) {
                String recordId = sendOverWorkrecord(account.getAcValue(), enterpriseAfterSaleLog.getEaslid());
                // 将代办id(recordId)存入库里
                enterpriseAfterSaleLog.setRecordId(recordId);
            }
        }
        enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.save(enterpriseAfterSaleLog);
        // 通知创建人，完了售后了

        return enterpriseAfterSaleLog;
    }






   /* public EnterpriseAfterSaleLog deleteAfterSale(Integer recordId) {
        EnterpriseAfterSaleLog enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.findById(logId)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_AFTER_SALE_LOG_NOT_FOUND));
        enterpriseAfterSaleLog.setStatus(1);
        enterpriseAfterSaleLog = enterpriseAfterSaleLogRepository.save(enterpriseAfterSaleLog);
        return enterpriseAfterSaleLog;
    }*/


    private String sendBeginWorkrecord(String openId, Integer logId) throws ErpException {
        List<OapiWorkrecordAddRequest.FormItemVo> formItemVos = new ArrayList<>();
        OapiWorkrecordAddRequest.FormItemVo formItemVo = new OapiWorkrecordAddRequest.FormItemVo();
        formItemVo.setTitle("售后任务指派");
        formItemVo.setContent("您接到了一个任务售后指派，请及时查看！");
        formItemVos.add(formItemVo);
        return DingUtil.workrecordAdd(
                openId,
                "售后任务指派!",
                "http://oa.hntxrj.com/#/registrationDetails?id=" + logId,
                formItemVos
        );
    }

    /**
     * 发送售后完成通知
     *
     * @param openId 钉钉userId
     * @param logId  售后记录id
     * @return 钉钉内代办id
     */
    private String sendOverWorkrecord(String openId, Integer logId) throws ErpException {
        List<OapiWorkrecordAddRequest.FormItemVo> formItemVos = new ArrayList<>();
        OapiWorkrecordAddRequest.FormItemVo formItemVo = new OapiWorkrecordAddRequest.FormItemVo();
        formItemVo.setTitle("售后完成通知");
        formItemVo.setContent("您申报的一条售后记录已被处理，请及时查看！");
        formItemVos.add(formItemVo);
        return DingUtil.workrecordAdd(
                openId,
                "售后完成通知!",
                "http://oa.hntxrj.com/#/registrationDetails?id=" + logId,
                formItemVos
        );
    }
}
