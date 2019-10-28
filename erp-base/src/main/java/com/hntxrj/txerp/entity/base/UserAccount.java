package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (UserAccount)表实体类
 *
 * @author lhr
 * @since 2018-09-18 15:22:47
 */
@Data
@Entity
public class UserAccount implements Serializable {

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer acid;
    //用户id
    private Integer uid;
    //绑定类型
    private String acType;
    //绑定值
    private String acValue;
}