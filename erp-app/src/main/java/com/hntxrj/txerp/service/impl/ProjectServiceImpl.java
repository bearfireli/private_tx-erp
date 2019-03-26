package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.ProjectService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.base.Project;
import com.hntxrj.txerp.entity.base.QProject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.ProjectDropDownVO;
import com.hntxrj.txerp.repository.ProjectRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {

    private JPAQueryFactory queryFactory;

    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              EntityManager entityManager, UserService userService) {
        super(entityManager);
        this.userService = userService;
        queryFactory = getQueryFactory();
        this.projectRepository = projectRepository;
    }


    @Override
    public List<ProjectDropDownVO> getProjectDropDown(String projectName) {

        QProject qProject = QProject.project;

        BooleanBuilder builder = new BooleanBuilder();

        if (projectName != null && !"".equals(projectName)) {
            builder.and(qProject.pName.like("%" + projectName + "%"));
        }

        builder.and(qProject.pStatus.eq(0));
        builder.and(qProject.pDelete.eq(0));


        JPAQuery<Project> projectJPAQuery =
                queryFactory.selectFrom(qProject)
                        .where(builder);

        List<Project> projects = projectJPAQuery.fetch();

        List<ProjectDropDownVO> projectDropDownVOS = new ArrayList<>();

        projects.forEach(project -> {
            ProjectDropDownVO projectDropDownVO = new ProjectDropDownVO();
            BeanUtils.copyProperties(project, projectDropDownVO);
            projectDropDownVOS.add(projectDropDownVO);
        });


        return projectDropDownVOS;
    }

    @Override
    public List<ProjectDropDownVO> getProjectDropDown() {
        return getProjectDropDown(null);
    }

    @Override
    public PageVO<Project> getProjectList(String token, String projectName, Integer status,
                                          long page, long pageSize) throws ErpException {


        QProject qProject = QProject.project;

        BooleanBuilder builder = new BooleanBuilder();

        if (projectName != null) {
            builder.and(qProject.pName.like("%" + projectName + "%"));
        }
        if (status != null) {
            builder.and(qProject.pStatus.eq(status));
        }
        builder.and(qProject.pDelete.eq(0));
        JPAQuery<Project> projectJPAQuery =
                queryFactory.selectFrom(qProject)
                        .where(builder)
                        .offset((page - 1) * pageSize)
                        .limit(pageSize);

        PageVO<Project> pageVO = new PageVO<>();
        pageVO.init(projectJPAQuery, pageSize);

        return pageVO;
    }

    @Override
    public Project addProject(Project project) {
        project.setPid(null);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Integer pid) throws ErpException {
        Project project = projectRepository.findById(pid)
                .orElseThrow(
                        () -> new ErpException(ErrEumn.PROJECT_LIFE_SPAN_NOT_EXIST)
                );
        project.setPDelete(1);
        projectRepository.save(project);

    }
}
