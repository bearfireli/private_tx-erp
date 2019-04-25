package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.service.AuthGroupService;
import com.hntxrj.txerp.service.MenuService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.vo.AuthGroupDropDownVO;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.AuthValueVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.QAuthGroup;
import com.hntxrj.txerp.entity.base.QAuthValue;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QMenu;
import com.hntxrj.txerp.entity.base.QUser;
import com.hntxrj.txerp.entity.base.QUserAuth;
import com.hntxrj.txerp.repository.AuthGroupRepository;
import com.hntxrj.txerp.repository.AuthValueRepository;
import com.hntxrj.txerp.repository.MenuRepository;
import com.hntxrj.txerp.entity.base.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AuthGroupServiceImpl extends BaseServiceImpl implements AuthGroupService {

    private final AuthGroupRepository authGroupRepository;

    private final AuthValueRepository authValueRepository;

    private final MenuRepository menuRepository;

    private final UserService userService;

    private JPAQueryFactory queryFactory;

    @Autowired
    public AuthGroupServiceImpl(AuthGroupRepository authGroupRepository,
                                AuthValueRepository authValueRepository, MenuRepository menuRepository,
                                EntityManager entityManager, UserService userService, MenuService menuService) {
        super(entityManager);
        this.authGroupRepository = authGroupRepository;
        this.authValueRepository = authValueRepository;
        this.menuRepository = menuRepository;
        this.userService = userService;
        this.queryFactory = getQueryFactory();
    }

    @Override
    public List<AuthGroupDropDownVO> getAuthGroupDropDown(Integer enterprise) throws ErpException {

        if (enterprise == null) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }

        List<AuthGroupDropDownVO> authGroupDropDownVOS = new ArrayList<>();

        List<AuthGroup> authGroups = queryFactory.selectFrom(QAuthGroup.authGroup)
                .where(QAuthGroup.authGroup.enterprise.eq(enterprise)
                        .and(QAuthGroup.authGroup.agStatus.eq(0)))
                .fetch();

        authGroups.forEach(authGroup ->
                makeDropDown(authGroupDropDownVOS, authGroup));


        return authGroupDropDownVOS;
    }

    @Override
    public PageVO<AuthGroupVO> selectAuthGroup(
            String token,
            long page, long pageSize,
            String agName, Integer agStatus,
            Integer enterprise) throws ErpException {
        QAuthGroup qAuthGroup = QAuthGroup.authGroup;
        QUser qUser = QUser.user;
        QEnterprise qEnterprise = QEnterprise.enterprise;

        BooleanBuilder builder = new BooleanBuilder();

        if (agName != null && !"".equals(agName)) {
            builder.and(qAuthGroup.agName.like("%" + agName + "%"));
        }
        if (agStatus != null) {
            builder.and(qAuthGroup.agStatus.eq(agStatus));
        }
        if (!userService.userIsSupperAdmin(token)) {
            builder.and(qAuthGroup.enterprise.eq(enterprise));
        }


        JPAQuery<AuthGroupVO> select = getQueryFactory()
                .select(
                        Projections.bean(
                                AuthGroupVO.class,
                                qAuthGroup.agid,
                                qAuthGroup.agName,
                                qAuthGroup.enterprise,
                                qAuthGroup.agStatus,
                                qAuthGroup.createTime,
                                qAuthGroup.updateTime,
                                qAuthGroup.project,
                                qAuthGroup.updateUser,
                                qUser.username,
                                qEnterprise.epShortName
                        )
                ).from(qAuthGroup)
                .leftJoin(qEnterprise)
                .on(qAuthGroup.enterprise.eq(qEnterprise.eid))
                .leftJoin(qUser)
                .on(qAuthGroup.updateUser.eq(qUser.uid))
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        List<AuthGroupVO> authGroupList =
                select.fetch();


        PageVO<AuthGroupVO> authGroupVOPageVO = new PageVO<>();
        authGroupVOPageVO.init(select.fetchCount(), page, authGroupList);


        return authGroupVOPageVO;
    }

    @Override
    @Transactional
    public AuthGroup editAuthGroup(AuthGroup authGroup)
            throws ErpException {

        AuthGroup authGroup1;
        try {
            authGroup1 = authGroupRepository.save(authGroup);
            if (authGroup.getAgid() == null ||
                    0 == authGroup.getAgid()) {
                List<Menu> menus = menuRepository
                        .findAllByEnterpriseIn(Arrays.asList(authGroup1.getEnterprise(), 0));

                List<AuthValue> initAuthValues = new ArrayList<>();
                for (Menu menu : menus) {
                    AuthValue authValue = new AuthValue();
                    authValue.setGroupId(authGroup1.getAgid());
                    authValue.setMenuId(menu.getMenuFmid());
                    authGroup.setUpdateUser(authGroup1.getUpdateUser());
                    authValue.setValue(0);
                    initAuthValues.add(authValue);
                }

                authValueRepository.saveAll(initAuthValues);
            }
        } catch (Exception e) {
            throw new ErpException(ErrEumn.EDIT_AUTH_GROUP_ERROR);
        }

        return authGroup1;

    }

    @Override
    public List<AuthGroup> getAuthGroup(String token, Integer enterpriseId) throws ErpException {

        if (!userService.userIsSupperAdmin(token)) {
            List<Enterprise> userEnterprises = userService.getEnterprisesByToken(token);
            long count =
                    userEnterprises.stream().filter(enterprise -> enterprise.getEid().equals(enterpriseId)).count();
            if (count <= 0) {
                // 用户不在查询的企业里
                throw new ErpException(ErrEumn.USER_NOT_IN_ENTERPRISE);
            }
        }
        return queryFactory.selectFrom(QAuthGroup.authGroup)
                .where(QAuthGroup.authGroup.enterprise.eq(enterpriseId)
                        .and(QAuthGroup.authGroup.agStatus.eq(0)))
                .fetch();
    }

    @Override
    public List<AuthValueVO> getAuthValue(Integer groupId) {
        QAuthValue qAuthValue = QAuthValue.authValue;
        QAuthGroup qAuthGroup = QAuthGroup.authGroup;
        QMenu qMenu = QMenu.menu;


        return getQueryFactory()
                .select(
                        Projections.bean(
                                AuthValueVO.class,
                                qAuthGroup.agid,
                                qAuthGroup.agName,
                                qAuthGroup.enterprise,
                                qMenu.mid,
                                qMenu.menuName,
                                qMenu.menuUrl,
                                qMenu.menuApi,
                                qMenu.menuFmid,
                                qMenu.menuStatus,
                                qAuthValue.value
                        )
                ).from(qAuthValue)
                .innerJoin(qAuthGroup)
                .on(qAuthValue.groupId.eq(qAuthGroup.agid))
                .innerJoin(qMenu)
                .on(qAuthValue.menuId.eq(qMenu.mid))
                .where(qAuthValue.groupId.eq(groupId))
                .fetch();
    }

    @Override
    @Transactional
    public List<AuthValue> saveAuthValue(List<Integer> menuIds,
                                         Integer groupId, String token,
                                         Integer pid) throws ErpException {
        User user = userService.tokenGetUser(token);

        QAuthValue qAuthValue = QAuthValue.authValue;
        QMenu qMenu = QMenu.menu;
        // 获取权限组的菜单项
        List<AuthValue> authValues = getQueryFactory()
                .selectFrom(qAuthValue)
                .join(qMenu).on(qMenu.mid.eq(qAuthValue.menuId))
                .where(qAuthValue.groupId.eq(groupId))
                .where(qMenu.project.eq(pid)).fetch();
//                authValueRepository.findAllByGroupId(groupId);

        // 对菜单项进行对比
        for (AuthValue authValue : authValues) {
            //Used to control whether menuId still exists in this save.
            boolean isExist = false;
            for (Integer menuId : menuIds) {
                if (menuId != null && menuId.equals(authValue.getMenuId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                // This save operation does not have this menu
                authValue.setValue(0);
            } else {
                authValue.setValue(1);
            }
        }

        for (Integer menuId : menuIds) {
            boolean isExist = false;
            for (AuthValue authValue : authValues) {
                if (menuId != null && menuId.equals(authValue.getMenuId())) {
                    isExist = true;
                }
            }

            if (menuId != null && !isExist) {
                AuthValue authValue = new AuthValue();
                authValue.setValue(1);
                authValue.setGroupId(groupId);
                authValue.setMenuId(menuId);
                authValues.add(authValue);
            }
        }


        for (AuthValue authValue : authValues) {
            authValue.setUpdateUser(user.getUid());
        }


        // save operation
        return authValueRepository.saveAll(authValues);
    }

    @Override
    public Integer[] getOpenAuth(Integer groupId) {
        QAuthValue qAuthValue = QAuthValue.authValue;
        List<AuthValue> authValues = getQueryFactory().selectFrom(qAuthValue)
                .where(qAuthValue.groupId.eq(groupId))
                .where(qAuthValue.value.eq(1))
                .fetch();
        Integer[] resultIds = new Integer[authValues.size()];
        final int[] i = {0};
        authValues.forEach(authValue -> {
            resultIds[i[0]] = authValue.getMenuId();
            i[0]++;
        });

        return resultIds;
    }

    @Override
    public boolean isPermission(String token, Integer enterprise, String uri) throws ErpException {
        // get user

        User user = userService.tokenGetUser(token);
        if (user == null) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        // 超级管理员拥有所有权限
        if (userService.userIsSupperAdmin(user.getUid())) {
            return true;
        }

        if (userService.gerEnterprisesById(user.getUid()).stream()
                .filter(enterprise1 -> enterprise1.getEid().equals(enterprise)).count() <= 0) {
            return false;
        }


        QUserAuth qUserAuth = QUserAuth.userAuth;
        // get auth group id
        UserAuth userAuth = queryFactory.selectFrom(qUserAuth)
                .where(qUserAuth.user.uid.eq(user.getUid()))
                .where(qUserAuth.enterprise.eid.eq(enterprise))
                .fetchOne();
        if (userAuth == null) {
            return false;
        }

        Integer authGroupId = userAuth.getAuthGroup().getAgid();

        // get auth group permission
        List<AuthValueVO> authValueVOS = this.getAuthValue(authGroupId);

        for (AuthValueVO authValueVO : authValueVOS) {
            if (authValueVO.getMenuApi().equals(uri)
                    && authValueVO.getValue() != 0) {
                return true;
            }
        }

        return false;
    }

    private void makeDropDown(
            List<AuthGroupDropDownVO> result, AuthGroup authGroup) {
        AuthGroupDropDownVO authGroupDropDownVO = new AuthGroupDropDownVO();
        BeanUtils.copyProperties(authGroup, authGroupDropDownVO);
        result.add(authGroupDropDownVO);
    }
}
