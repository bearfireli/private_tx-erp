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

    /*
     * @Description 通过funcName，pid获取menu
     * @Author 陈世强
     * @e-mail chenshiqiang@wisfaith.net
     * @Date 14:06 2019-11-13
     */
    Menu getMenuByfuncNameAndPid(String funcName,int pid);

    List<Menu> getFunctionMenuList();
}
