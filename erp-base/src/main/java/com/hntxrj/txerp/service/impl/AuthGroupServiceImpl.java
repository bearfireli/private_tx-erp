package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.mapper.AuthGroupMapper;
import com.hntxrj.txerp.mapper.UserMapper;
import com.hntxrj.txerp.repository.AuthValueOldRepository;
import com.hntxrj.txerp.service.AuthGroupService;
import com.hntxrj.txerp.service.MenuService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.repository.AuthGroupRepository;
import com.hntxrj.txerp.repository.AuthValueRepository;
import com.hntxrj.txerp.repository.MenuRepository;
import com.hntxrj.txerp.vo.AuthGroupDropDownVO;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.AuthValueVO;
import com.hntxrj.txerp.vo.PageVO;
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
import java.util.Date;
import java.util.List;


@Service
public class AuthGroupServiceImpl extends BaseServiceImpl implements AuthGroupService {

    private final AuthGroupRepository authGroupRepository;

    private final AuthValueRepository authValueRepository;

    private final AuthValueOldRepository authValueOldRepository;

    private final MenuRepository menuRepository;

    private final MenuService menuService;

    private final UserService userService;

    private JPAQueryFactory queryFactory;

    private final UserMapper userMapper;
    private final AuthGroupMapper authGroupMapper;

    @Autowired
    public AuthGroupServiceImpl(AuthGroupRepository authGroupRepository,
                                AuthValueRepository authValueRepository, MenuRepository menuRepository, AuthGroupMapper authGroupMapper,
                                EntityManager entityManager, UserService userService, MenuService menuService, AuthValueOldRepository authValueOldRepository, MenuService menuService1, UserMapper userMapper) {
        super(entityManager);
        this.authGroupRepository = authGroupRepository;
        this.authValueRepository = authValueRepository;
        this.menuRepository = menuRepository;
        this.userService = userService;
        this.authValueOldRepository = authValueOldRepository;
        this.menuService = menuService1;
        this.userMapper = userMapper;
        this.authGroupMapper = authGroupMapper;
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

        List<AuthGroupVO> authGroupList = select.fetch();


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

    /**
     * 获取权限组的方法，名称等信息
     */
    @Override
    public List<AuthValueVO> getAuthValue(Integer groupId) {

        return userMapper.getAuthValue(groupId);


    }


    @Override
    @Transactional
    public List<AuthValue> saveAuthValue(List<String> funNames,
                                         Integer groupId, String token,
                                         Integer pid) throws ErpException {
        User user = userService.tokenGetUser(token);
        //获取权限修改之前的权限
        List<AuthValue> authValues = userService.getAuthValue(groupId, pid);

        // 对菜单项进行对比
        for (AuthValue authValue : authValues) {
            //Used to control whether menuId still exists in this save.
            boolean isExist = false;
            for (String funName : funNames) {
                if (funName != null && funName.equals(authValue.getFunName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                // This save operation does not have this menu
                authValue.setValue(0);
            } else {
                authValue.setValue(1);
            }
        }

        for (String funName : funNames) {
            boolean isExist = false;
            for (AuthValue authValue : authValues) {
                if (funName != null && funName.equals(authValue.getFunName())) {
                    isExist = true;
                    break;
                }
            }

            if (funName != null && !isExist) {
                AuthValue authValue = new AuthValue();
                authValue.setValue(1);
                authValue.setGroupId(groupId);
                authValue.setMenuId(userMapper.getMenuIdByFunName(funName));
                authValue.setFunName(funName);
                authValues.add(authValue);
            }
        }

        for (AuthValue authValue : authValues) {
            authValue.setUpdateUser(user.getUid());
        }
        saveOldAll(funNames, groupId, token, pid);
        // save operation
        return authValueRepository.saveAll(authValues);
    }

    /**
     * @Description 更改权限时兼容旧版本
     * @Author 陈世强
     * @e-mail chenshiqiang@wisfaith.net
     * @Date 15:54 2019-11-13
     **/
    private void saveOldAll(List<String> funNames, Integer groupId, String token, Integer pid) throws ErpException {
        //旧版本表获取权限修改之前的权限
        List<AuthValueOld> authValueOlds = userService.getAuthValueOld(groupId, pid);
        List<Integer> midLIst = new ArrayList<>();
        //遍历funName得到相应的menuID数组
        for (String funcName : funNames) {
            Menu TempMenu = menuService.getMenuByfuncNameAndPid(funcName, pid);
            if (TempMenu != null) {
                midLIst.add(TempMenu.getMid());
            }

        }

        // 遍历修改之前的已有的权限
        for (AuthValueOld authValueOld : authValueOlds) {
            //已有权限修改后是否存在，默认为不存在
            boolean isExist = false;
            //遍历修改后存在的权限
            for (Integer midTemp : midLIst) {
                //修改后的权限存在于已有权限中，改变默认值
                if (midTemp != null && midTemp.equals(authValueOld.getMenuId())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {//修改后没有状态值为禁用
                // This save operation does not have this menu
                authValueOld.setValue(0);
            } else {//修改后仍然有状态值为启用
                authValueOld.setValue(1);
            }
        }
        //遍历修改后的菜单ID
        for (Integer midTemp : midLIst) {
            //默认修改后的权限在修改前不存在
            boolean isExist = false;
            //遍历修改之前的权限
            for (AuthValueOld authValueOld : authValueOlds) {
                //修改后的权限存在于原来已有的权限列表中，改变默认值
                if (midTemp != null && midTemp.equals(authValueOld.getMenuId())) {
                    isExist = true;
                    break;
                }
            }
            //修改之前不存在的权限，创建一个对象状态为启用并添加到列表中
            if (midTemp != null && !isExist) {
                AuthValueOld authValueOld = new AuthValueOld();
                authValueOld.setValue(1);//启用
                authValueOld.setGroupId(groupId);//权限ID
                authValueOld.setMenuId(midTemp);//菜单ID
                authValueOld.setUpdateUser(userService.tokenGetUser(token).getUid());//当前用户ID
                authValueOld.setUpdateTime(new Date());//当前时间戳
                authValueOld.setCreateTime(new Date());
                authValueOlds.add(authValueOld);
            }
        }
        //使用jpa持久化数据
        authValueOldRepository.saveAll(authValueOlds);
    }


    /**
     * 得到此权限组的方法名集合
     */
    @Override
    public String[] getOpenAuth(Integer groupId) {

        List<String> authList = userMapper.getOpenAuth(groupId);

        String[] functionNames = new String[authList.size()];
        for (int i = 0; i < authList.size(); i++) {
            functionNames[i] = authList.get(i);
        }
        return functionNames;
    }


    /**
     * 根据token,compid判断该用户是否具有访问此methodName的权限
     */
    @Override
    public boolean isPermission(String token, Integer enterprise, String methodName) throws ErpException {
        // get user

        User user = userService.tokenGetUser(token);
        if (user == null) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        // 超级管理员拥有所有权限
        if (userService.userIsSupperAdmin(user.getUid())) {
            return true;
        }

        //根据uid和compid查询出用户的权限组id
        Integer authGroupID = userService.getAuthGroupByUserAndCompid(user.getUid(), enterprise);

        //根据authGrouID和methodName从auth_value中查询是否存在数据
        Integer authCount = userService.judgementAuth(authGroupID, methodName);

        if (authCount >= 1) {
            return true;
        }


        return false;
    }

    /**
     * @Description 获得初始化的权限组列表
     * @Author 陈世强
     * @e-mail chenshiqiang@wisfaith.net
     * @Date 16:34 2019-11-11
     **/
    @Override
    public List<AuthGroupVO> getInitAuthGroup() {
        return authGroupMapper.getInitAuthGroup();
    }


    private void makeDropDown(
            List<AuthGroupDropDownVO> result, AuthGroup authGroup) {
        AuthGroupDropDownVO authGroupDropDownVO = new AuthGroupDropDownVO();
        BeanUtils.copyProperties(authGroup, authGroupDropDownVO);
        result.add(authGroupDropDownVO);
    }
}
