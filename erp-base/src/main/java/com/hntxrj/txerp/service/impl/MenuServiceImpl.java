package com.hntxrj.txerp.service.impl;


import com.hntxrj.txerp.mapper.MenuMapper;
import com.hntxrj.txerp.service.MenuService;
import com.hntxrj.txerp.entity.base.Menu;
import com.hntxrj.txerp.entity.base.QMenu;
import com.hntxrj.txerp.repository.MenuRepository;
import com.hntxrj.txerp.vo.MenuListVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private JPAQueryFactory queryFactory;
    private MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository,
                           EntityManager entityManager,
                           MenuMapper menuMapper) {
        super(entityManager);
        queryFactory = getQueryFactory();
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }


    @Override
    public List<MenuListVO> getMenuTree(Integer enterprise) {

        return this.getMenuTree(enterprise, null);
    }

    @Override
    public List<MenuListVO> getMenuTreeByProject(Integer pid) {
        QMenu qMenu = QMenu.menu;
        List<Menu> menus = queryFactory.selectFrom(qMenu)
                .where(qMenu.project.eq(pid))
                .where(qMenu.menuStatus.eq(0)) // 只查询启用状态的菜单
                .orderBy(qMenu.menuLevel.desc())
                .fetch();
//                menuRepository
//                .findAllByProject(
//                        pid, Sort.by(Sort.Direction.DESC,
//                                "menuLevel"));


        List<MenuListVO> menuListVOS = new ArrayList<>();

        menus.forEach(menu -> menuListVOS.add(makeMenuListVO(menu)));
        List<MenuListVO> result = new ArrayList<>();


        menuToVO(menuListVOS, result);

        return result;

    }

    private void menuToVO(List<MenuListVO> menuListVOS, List<MenuListVO> result) {
        menuListVOS.forEach(menuListVO -> {
            if (menuListVO.getFid() == 0) {
                result.add(menuListVO);
            } else {
                // Client
                menuListVOS.forEach(m -> {
                    if (m.getId() == menuListVO.getFid()) {
                        m.getChildren().add(menuListVO);
                    }
                });
            }

        });
    }


    @Override
    public List<MenuListVO> getMenuTree(Integer enterprise, Integer status) {

        List<Menu> menus = this.getMenusByEnterprise(enterprise, status);

        List<MenuListVO> menuListVOS = new ArrayList<>();

        menus.forEach(menu -> menuListVOS.add(makeMenuListVO(menu)));
        List<MenuListVO> result = new ArrayList<>();


        menuToVO(menuListVOS, result);

        return result;
    }

    @Override
    public List<Menu> getMenusByEnterprise(Integer eid) {

        return menuRepository
                .findAllByEnterpriseIn(
                        Arrays.asList(eid, 0), Sort.by(Sort.Direction.DESC,
                                "menuLevel"));
    }

    @Override
    public List<Menu> getMenusByEnterprise(Integer eid, Integer status) {
        if (status == null) {
            return this.getMenusByEnterprise(eid);
        }

        return menuRepository
                .findAllByMenuStatusAndEnterpriseIn(
                        status, Arrays.asList(eid, 0), Sort.by(Sort.Direction.DESC,
                                "menuLevel"));
    }

    @Override
    public Menu getMenuById(Integer mid) {
        Optional<Menu> op = menuRepository.findById(mid);

        return op.get();
    }

    @Override
    public Menu saveMenu(Menu menu) {
        if (menu.getEnterprise() == null) {
            menu.setEnterprise(0);
        }
        if (menu.getProject() == null) {
            menu.setProject(0);
        }
        if (menu.getUpdateUser() == null) {
            menu.setUpdateUser(1);
        }

        menu.setModelName("");
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuByfuncNameAndPid(String funcName,int pid) {
        return menuMapper.getMenuByfuncNameAndPid(funcName,pid);
    }

    private void addTopMenu(List<MenuListVO> menuListVOS, Menu menu) {
        menuListVOS.add(makeMenuListVO(menu));
    }

    private MenuListVO makeMenuListVO(Menu menu) {
        MenuListVO menuListVO = new MenuListVO();
        menuListVO.setId(menu.getMid());
        menuListVO.setLabel(menu.getMenuName());
        menuListVO.setChildren(new ArrayList<>());
        menuListVO.setFid(menu.getMenuFmid());
        menuListVO.setLevel(menu.getMenuLevel());
        menuListVO.setUrl(menu.getMenuUrl());
        menuListVO.setApi(menu.getMenuApi());
        menuListVO.setFuncName(menu.getFuncName());
        return menuListVO;
    }

    private List<MenuListVO> getClient(List<Menu> menus, Integer fid, int addTime) {
        List<MenuListVO> menuListVOS = new ArrayList<>();


        menus.forEach(menu -> {
            if (fid.equals(menu.getMenuFmid())) {
                menuListVOS.add(makeMenuListVO(menu));
            }
        });
        return menuListVOS;
    }

}
