package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthValueVO implements Serializable {

    private Integer agid;

    /**
     * 权限组名称
     */

    private String agName;

    private Integer enterprise;

    private Integer mid;

    private String menuName;

    private String menuUrl;
    private String funcName;

    private String menuApi;

    private Integer menuFmid;

    private Integer menuStatus;

    private Integer value;


}
