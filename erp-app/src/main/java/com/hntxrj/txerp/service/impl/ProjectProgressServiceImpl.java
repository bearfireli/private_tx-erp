package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.ProjectProgressService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.base.ProjectProgress;
import com.hntxrj.txerp.entity.base.QProjectProgress;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.core.util.EntityTools;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectProgressVO;
import com.hntxrj.txerp.repository.ProjectProgressRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class ProjectProgressServiceImpl implements ProjectProgressService {

    private final ProjectProgressRepository projectProgressRepository;

    private final UserService userService;

    private JPAQueryFactory queryFactory;

    @Autowired
    public ProjectProgressServiceImpl(EntityManager entityManager,
                                      ProjectProgressRepository projectProgressRepository,
                                      UserService userService) {
        this.projectProgressRepository = projectProgressRepository;
        this.userService = userService;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public ProjectProgress saveProjectProgress(ProjectProgress projectProgress) throws ErpException {

        if (StringUtils.isEmpty(projectProgress.getProjectName())) {
            throw new ErpException(ErrEumn.PROJECT_NAME_NULL);
        }
        if (projectProgress.getHeadUid() == null || projectProgress.getHeadUid() == 0) {
            throw new ErpException(ErrEumn.PROJECT_HEAD_MAN_CANNOT_NULL);
        }
        if (projectProgress.getCheckUid() == null || projectProgress.getCheckUid() == 0) {
            throw new ErpException(ErrEumn.PROJECT_CHECK_MAN_CANNOT_NULL);
        }

        EntityTools.setEntityDefaultValue(projectProgress);

        //初始化进度数组
        projectProgress.setSchedule("[]");

        try {
            projectProgress = projectProgressRepository.save(projectProgress);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.PROJECT_SAVE_ERROR);
        }


        return projectProgress;
    }

    @Override
    public PageVO<ProjectProgressVO> list(String projectName, long page, long pageSize) {


        QProjectProgress qProjectProgress = QProjectProgress.projectProgress;
        QUser qHeadUser = new QUser("qHeadUser");
        QUser qCheckUser = new QUser("qCheckUser");


        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(projectName)) {
            builder.and(qProjectProgress.projectName.like("%" + projectName + "%"));
        }

        JPAQuery<ProjectProgressVO> query = queryFactory.select(
                Projections.bean(
                        ProjectProgressVO.class,
                        qProjectProgress.ppid,
                        qProjectProgress.projectName,
                        qProjectProgress.headUid,
                        qHeadUser.username.as("headName"),
                        qProjectProgress.checkUid,
                        qCheckUser.username.as("checkName"),
                        qProjectProgress.planningBeginTime,
                        qProjectProgress.planningEndTime,
                        qProjectProgress.actualBeginTime,
                        qProjectProgress.actualEndTime,
                        qProjectProgress.workSummary,
                        qProjectProgress.fruit,
                        qProjectProgress.projectContent,
                        qProjectProgress.schedule,
                        qProjectProgress.status
                )
        ).from(qProjectProgress)
                .leftJoin(qCheckUser).on(qCheckUser.uid.eq(qProjectProgress.checkUid))
                .leftJoin(qHeadUser).on(qHeadUser.uid.eq(qProjectProgress.headUid))
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<ProjectProgressVO> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public ProjectProgressVO info(Integer ppId) {

        QProjectProgress qProjectProgress = QProjectProgress.projectProgress;
        QUser qHeadUser = new QUser("qHeadUser");
        QUser qCheckUser = new QUser("qCheckUser");


        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProjectProgress.ppid.eq(ppId));
        return queryFactory.select(
                Projections.bean(
                        ProjectProgressVO.class,
                        qProjectProgress.ppid,
                        qProjectProgress.projectName,
                        qProjectProgress.headUid,
                        qHeadUser.username.as("headName"),
                        qProjectProgress.checkUid,
                        qCheckUser.username.as("checkName"),
                        qProjectProgress.planningBeginTime,
                        qProjectProgress.planningEndTime,
                        qProjectProgress.actualBeginTime,
                        qProjectProgress.actualEndTime,
                        qProjectProgress.workSummary,
                        qProjectProgress.fruit,
                        qProjectProgress.projectContent,
                        qProjectProgress.schedule,
                        qProjectProgress.status
                )
        ).from(qProjectProgress)
                .leftJoin(qCheckUser).on(qCheckUser.uid.eq(qProjectProgress.checkUid))
                .leftJoin(qHeadUser).on(qHeadUser.uid.eq(qProjectProgress.headUid))
                .where(builder)
                .fetchFirst();
    }

    @Override
    public void start(Integer ppId, String token) throws ErpException {
        updateProjectProgressStatus(ppId, token, 1);


    }

    @Override
    public void finish(Integer ppId, String token) throws ErpException {
        updateProjectProgressStatus(ppId, token, 2);

    }


    @Override
    public void check(Integer ppId, String token) throws ErpException {
        updateProjectProgressStatus(ppId, token, 3);


    }

    @Override
    public void addSchedule(Integer ppId, String token, String msg) throws ErpException {
        ProjectProgress projectProgress = projectProgressRepository.findById(ppId)
                .orElseThrow(() -> new ErpException(ErrEumn.PROJECT_NOT_FOUND));
        User user = userService.tokenGetUser(token);
        String schedule = projectProgress.getSchedule();
        JSONArray scheduleArray = JSONArray.parseArray(schedule);
        JSONObject scheduleObj = new JSONObject();
        scheduleObj.put("uid", user.getUid());
        scheduleObj.put("userName", user.getUsername());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        scheduleObj.put("createTime", format.format(new Date()));
        scheduleObj.put("msg", msg);
        scheduleArray.add(scheduleObj);
        projectProgress.setSchedule(scheduleArray.toJSONString());
        projectProgressRepository.save(projectProgress);
    }


    /**
     * 修改项目状态
     * 只有项目负责人和项目验收人可以start项目   1
     * 只有项目负责人可以提交验收请求           2
     * 只有项目验收人可以对项目验收             3
     *
     * @param ppId   项目id
     * @param token  修改状态用户token，验证权限
     * @param status 状态
     * @throws ErpException erp exception
     */
    private void updateProjectProgressStatus(Integer ppId, String token, Integer status) throws ErpException {
        ProjectProgress projectProgress = projectProgressRepository.findById(ppId)
                .orElseThrow(() -> new ErpException(ErrEumn.PROJECT_NOT_FOUND));
        User user = userService.tokenGetUser(token);
        if (status == 1) {
            if (!(user.getUid().equals(projectProgress.getCheckUid()) ||
                    user.getUid().equals(projectProgress.getHeadUid()))) {
                throw new ErpException(ErrEumn.ONLY_CHECK_MAN_OR_HEAD_MAN_CAN_START_PROJECT);
            }
            // 项目开始修改项目实际开始时间
            projectProgress.setActualBeginTime(new Date());
        }
        if (status == 2) {
            if (!user.getUid().equals(projectProgress.getHeadUid())) {
                throw new ErpException(ErrEumn.ONLY_HEAD_MAN_CAN_FINSH_PROJECT);
            }
        }
        if (status == 3) {
            if (!user.getUid().equals(projectProgress.getCheckUid())) {
                throw new ErpException(ErrEumn.ONLY_CHECK_MAN_CAN_CHECK_PROJECT);
            }

            // 项目完成修改项目实际完成时间
            projectProgress.setActualEndTime(new Date());
        }
        projectProgress.setStatus(status);
        projectProgressRepository.save(projectProgress);
    }
}
