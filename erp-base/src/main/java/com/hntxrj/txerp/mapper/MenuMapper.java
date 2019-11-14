package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Menu;

/**
 * @Author 陈世强
 * @e-mail chenshiqiang@wisfaith.net
 **/

public interface MenuMapper {
    //通过funcName获取menu
    Menu getMenuByfuncNameAndPid(String funcName,int pid);
}