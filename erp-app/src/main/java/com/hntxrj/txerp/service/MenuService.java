package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.Menu;
import com.hntxrj.txerp.vo.MenuListVO;

import java.util.List;

public interface MenuService {


    List<MenuListVO> getMenuTree(Integer enterprise);

    List<MenuListVO> getMenuTreeByProject(Integer pid);


    List<MenuListVO> getMenuTree(Integer enterprise, Integer status);


    List<Menu> getMenusByEnterprise(Integer eid);

    List<Menu> getMenusByEnterprise(Integer eid, Integer status);

    Menu getMenuById(Integer mid);

    Menu saveMenu(Menu menu);


}
