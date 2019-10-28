package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author lhr
 */
@Entity
@Table(name = "de_menuinfo")
@Data
public class DeMenuInfo implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "menuname")
    private String menuName;

    @Column(name = "menuicon")
    private String menuIcon;

    @Column(name = "menugroup")
    private Integer menuGroup;

    @Column(name = "menufid")
    private Integer menuFid;

    @Column(name = "menuurl")
    private String menuUrl;

    @Column(name = "menudourl")
    private String menuDoUrl;

    @Column(name = "menudotaxis")
    private Integer menuDoTaxis;

    @Column(name = "Menuidentification")
    private Integer menuIdentification;

    @Column(name = "createtime")
    private java.util.Date createTime;

    @Column(name = "createuser")
    private String createUser;

    @Column(name = "updatetime")
    private java.util.Date updateTime;

    @Column(name = "menustatus")
    private String menuStatus;

    @Column(name = "Menusort")
    private Integer menuSort;

    @Column(name = "menualias")
    private String menuAlias;

    @Column(name = "menutype")
    private Integer menuType;


}
