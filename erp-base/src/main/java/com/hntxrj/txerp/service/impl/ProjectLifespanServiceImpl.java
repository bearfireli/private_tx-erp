package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.ProjectLifespanService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.repository.ProjectLifespanRepository;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectLifespanVO;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.QProject;
import com.hntxrj.txerp.entity.base.QProjectLifespan;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.hntxrj.txerp.entity.base.ProjectLifespanKeys;
import com.hntxrj.txerp.entity.base.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * (ProjectLifespan)表服务实现类
 *
 * @author zzlhr
 * @since 2018-10-15 09:35:24
 */
@Service
public class ProjectLifespanServiceImpl extends BaseServiceImpl implements ProjectLifespanService {

    private final ProjectLifespanRepository projectLifespanRepository;

    private final UserService userService;
    private JPAQueryFactory queryFactory;

    @Autowired
    public ProjectLifespanServiceImpl(EntityManager entityManager,
                                      ProjectLifespanRepository projectLifespanRepository,
                                      UserService userService) {
        super(entityManager);
        this.projectLifespanRepository = projectLifespanRepository;
        this.userService = userService;
        queryFactory = getQueryFactory();
    }


    @Override
    public Date getExpireTime(Integer pid, String token, Integer enterprise) throws ErpException {
        User user = userService.tokenGetUser(token);
        QProjectLifespan qProjectLifespan = QProjectLifespan.projectLifespan;
        // TODO: 验证用户是否属于该企业
        ProjectLifespan projectLifespan = queryFactory.selectFrom(qProjectLifespan)
                .where(qProjectLifespan.pid.eq(pid)
                        .and(qProjectLifespan.enterpriseId.eq(enterprise)))
                .fetchOne();
        if (projectLifespan == null) {
            throw new ErpException(ErrEumn.ENTERPRISE_NOT_OPEN_PROJECT);
        }
        return projectLifespan.getExpireTime();
    }

    @Override
    public ProjectLifespanVO saveProjectLifespan(String token, ProjectLifespan projectLifespan)
            throws ErpException {
        User user = userService.tokenGetUser(token);
        projectLifespan.setUpdateUser(user.getUid());
        ProjectLifespan projectLifespan1
                = projectLifespanRepository.save(projectLifespan);

        Integer pid = projectLifespan1.getPid();
        Integer enterprise = projectLifespan.getEnterpriseId();

        QProjectLifespan qProjectLifespan = QProjectLifespan.projectLifespan;
        QUser qUser = QUser.user;
        QProject qProject = QProject.project;
        QEnterprise qEnterprise = QEnterprise.enterprise;

        return queryFactory.select(
                Projections.bean(
                        ProjectLifespanVO.class,
                        qProjectLifespan.pid,
                        qProject.pName.as("projectName"),
                        qProjectLifespan.enterpriseId,
                        qEnterprise.epName.as("enterpriseName"),
                        qProjectLifespan.createTime,
                        qProjectLifespan.expireTime,
                        qProjectLifespan.startTime,
                        qProjectLifespan.updateTime,
                        qProjectLifespan.updateUser,
                        qUser.username.as("updateUserName")
                )
        ).from(qProjectLifespan)
                .leftJoin(qProject).on(qProjectLifespan.pid.eq(qProject.pid))
                .leftJoin(qUser).on(qProjectLifespan.updateUser.eq(qUser.uid))
                .leftJoin(qEnterprise).on(qProjectLifespan.enterpriseId.eq(qEnterprise.eid))
                .where(qProjectLifespan.pid.eq(pid))
                .where(qProjectLifespan.enterpriseId.eq(enterprise))
                .fetchOne();
    }


    @Override
    public void delProjectLifespan(String token, Integer enterpriseId, Integer pid) throws ErpException {
        User user = userService.tokenGetUser(token);
        ProjectLifespanKeys projectLifespanKeys = new ProjectLifespanKeys();
        projectLifespanKeys.setPid(pid);
        projectLifespanKeys.setEnterpriseId(enterpriseId);
        projectLifespanRepository.deleteById(projectLifespanKeys);
    }

    @Override
    public PageVO<ProjectLifespanVO> list(String token, Integer enterpriseId,
                                          long page, long pageSize) throws ErpException {
        QProjectLifespan qProjectLifespan = QProjectLifespan.projectLifespan;
        User user = userService.tokenGetUser(token);
        QUser qUser = QUser.user;
        QProject qProject = QProject.project;
        QEnterprise qEnterprise = QEnterprise.enterprise;
        BooleanBuilder builder = new BooleanBuilder();
        // 0查全部
        if (!userService.userIsSupperAdmin(token)) {
            builder.and(qProjectLifespan.enterpriseId.in(userService.getEnterpriseIdsByToken(token)));
        }

        if (userService.userIsSupperAdmin(token) && enterpriseId != 0) {
            builder.and(qProjectLifespan.enterpriseId.eq(enterpriseId));
        }

        JPAQuery<ProjectLifespanVO> projectLifespanVOJPAQuery =
                queryFactory.select(
                        Projections.bean(
                                ProjectLifespanVO.class,
                                qProjectLifespan.pid,
                                qProject.pName.as("projectName"),
                                qProjectLifespan.enterpriseId,
                                qEnterprise.epName.as("enterpriseName"),
                                qProjectLifespan.createTime,
                                qProjectLifespan.expireTime,
                                qProjectLifespan.startTime,
                                qProjectLifespan.updateTime,
                                qProjectLifespan.updateUser,
                                qUser.username.as("updateUserName")
                        )
                ).from(qProjectLifespan)
                        .leftJoin(qProject).on(qProjectLifespan.pid.eq(qProject.pid))
                        .leftJoin(qUser).on(qProjectLifespan.updateUser.eq(qUser.uid))
                        .leftJoin(qEnterprise).on(qProjectLifespan.enterpriseId.eq(qEnterprise.eid))
                        .where(builder)
                        .offset((page - 1) * pageSize)
                        .limit(pageSize);

        PageVO<ProjectLifespanVO> pageVO = new PageVO<>();
        pageVO.init(projectLifespanVOJPAQuery, pageSize);
        return pageVO;
    }
}