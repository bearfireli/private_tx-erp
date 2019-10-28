package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.EnterpriseAfterSaleService;
import com.hntxrj.txerp.service.ProjectLifespanService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QEnterpriseAfterSale;
import com.hntxrj.txerp.repository.EnterpriseAfterSaleRepository;
import com.hntxrj.txerp.vo.EnterpriseAfterSaleVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.QProject;
import com.hntxrj.txerp.entity.base.QProjectLifespan;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.base.EnterpriseAfterSale;
import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Slf4j
public class EnterpriseAfterSaleServiceImpl implements EnterpriseAfterSaleService {

    private final EnterpriseAfterSaleRepository enterpriseAfterSaleRepository;
    private final UserService userService;
    private JPAQueryFactory queryFactory;
    private final ProjectLifespanService projectLifespanService;

    @Autowired
    public EnterpriseAfterSaleServiceImpl(EnterpriseAfterSaleRepository enterpriseAfterSaleRepository,
                                          UserService userService,
                                          EntityManager entityManager,
                                          ProjectLifespanService projectLifespanService) {
        this.enterpriseAfterSaleRepository = enterpriseAfterSaleRepository;
        this.userService = userService;
        queryFactory = new JPAQueryFactory(entityManager);
        this.projectLifespanService = projectLifespanService;
    }

    @Override
    public PageVO<EnterpriseAfterSaleVO> list(String token, Integer enterprise,
                                              long page, long pageSize) throws ErpException {

        BooleanBuilder builder = new BooleanBuilder();
        QEnterpriseAfterSale qEnterpriseAfterSale = QEnterpriseAfterSale.enterpriseAfterSale;
        QEnterprise qEnterprise = QEnterprise.enterprise;
        QUser qUser = QUser.user;
        QProject qProject = QProject.project;
        QProjectLifespan qProjectLifespan = QProjectLifespan.projectLifespan;
        if (enterprise != null && enterprise != 0) {
            builder.and(qEnterpriseAfterSale.eid.eq(enterprise));
        }

        JPAQuery<EnterpriseAfterSaleVO> enterpriseAfterSaleJPAQuery =
                queryFactory.select(
                        Projections.bean(
                                EnterpriseAfterSaleVO.class,
                                qEnterpriseAfterSale.asid,
                                qEnterpriseAfterSale.eid,
                                qEnterprise.epShortName.as("enterpriseName"),
                                qEnterpriseAfterSale.afContext,
                                qEnterpriseAfterSale.pid,
                                qProject.pName.as("projectName"),
                                qProjectLifespan.startTime.as("singDate"),
                                qProjectLifespan.expireTime,
                                qEnterpriseAfterSale.lineNumber,
                                qEnterpriseAfterSale.contractNumber,
                                qEnterpriseAfterSale.headUid,
                                qUser.username.as("headName"),
                                qUser.phone.as("headPhone"),
                                qEnterpriseAfterSale.remark
                        )
                ).from(qEnterpriseAfterSale)
                        .leftJoin(qEnterprise).on(qEnterpriseAfterSale.eid.eq(qEnterprise.eid))
                        .leftJoin(qProject).on(qProject.pid.eq(qEnterpriseAfterSale.pid))
                        .leftJoin(qUser).on(qUser.uid.eq(qEnterpriseAfterSale.headUid))
                        .leftJoin(qProjectLifespan)
                        .on(qProjectLifespan.enterpriseId.eq(qEnterpriseAfterSale.eid)
                                .and(qProjectLifespan.pid.eq(qEnterpriseAfterSale.pid)))
                        .where(builder)
                        .offset((page - 1) * pageSize)
                        .limit(pageSize);

        PageVO<EnterpriseAfterSaleVO> pageVO = new PageVO<>();
        pageVO.init(enterpriseAfterSaleJPAQuery, pageSize);

        // user not in supper cannot see contract number
        Integer authGroupId = userService.getUserAuthGroupByEnterprise(1, token);
        List<EnterpriseAfterSaleVO> enterpriseAfterSaleList = pageVO.getArr();

        if (authGroupId != 1) {
            enterpriseAfterSaleList.forEach(enterpriseAfterSale -> {
                enterpriseAfterSale.setContractNumber(null);
            });
        }
        pageVO.setArr(enterpriseAfterSaleList);

        return pageVO;

    }

    @Override
    @Transactional
    public EnterpriseAfterSale save(EnterpriseAfterSale enterpriseAfterSale,
                                    String token,
                                    ProjectLifespan projectLifespan) throws ErpException {

        QEnterpriseAfterSale qEnterpriseAfterSale = QEnterpriseAfterSale.enterpriseAfterSale;

        EnterpriseAfterSale oldEnterpriseAfterSale = queryFactory.selectFrom(qEnterpriseAfterSale)
                .where(qEnterpriseAfterSale.eid.eq(enterpriseAfterSale.getEid()))
                .where(qEnterpriseAfterSale.pid.eq(enterpriseAfterSale.getPid()))
                .fetchFirst();
        if (oldEnterpriseAfterSale != null) {
            enterpriseAfterSale.setAsid(oldEnterpriseAfterSale.getAsid());
        }
        try {
            projectLifespanService.saveProjectLifespan(token, projectLifespan);
            return enterpriseAfterSaleRepository.save(enterpriseAfterSale);
        } catch (Exception e) {
            log.error("【添加企业售后信息失败】errMsg={}", e.getMessage());
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_AFTER_SALE_ERROR);
        }
    }
}
