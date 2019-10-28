package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (PublicInfo)表实体类
 *
 * @author lhr
 * @since 2018-08-14 13:19:06
 */
@Data
@Entity
public class PublicInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id
    private Integer id;
    //父id
    private Integer fid;
    //名称
    private String name;
    //值
    private String value;
    //是否删除
    @Column(name = "`delete`")
    private Integer delete;
    //状态
    private Integer status;

}