package com.hntxrj.txerp.vo;


import lombok.Data;

import java.util.List;

/**
 * 菜单列表对象
 */
@Data
public class MenuListVO {

    private int id;

    private String label;

    private int fid;

    private int level;

    private String url;

    private String api;

    private boolean selected;

    private List<MenuListVO> children;

}
