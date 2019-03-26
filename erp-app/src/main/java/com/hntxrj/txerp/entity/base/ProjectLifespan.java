package com.hntxrj.txerp.entity.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

/**
 * (ProjectLifespan)表实体类
 *
 * @author zzlhr
 * @since 2018-10-15 09:35:24
 */
@Data
@Entity
@IdClass(ProjectLifespanKeys.class)
public class ProjectLifespan {
    //项目id
    @Id
    private Integer pid;
    //企业id
    @Id
    private Integer enterpriseId;
    //开始使用时间
    private Date startTime;
    //到期时间
    private Date expireTime;
    //创建时间
    @Column(insertable = false, updatable = false)
    private Date createTime;

    @Column(insertable = false, updatable = false)
    //更新时间
    private Date updateTime;
    //更新用户
    private Integer updateUser;
}