package com.hntxrj.txerp.entity.base;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Feedback implements Serializable {


    /* 反馈id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fbId;
    /* 用户id */
    private Integer uid;
    /*反馈人姓名*/
    private String linkName;
    /* 项目id */
    private Integer pid;
    /* 问题 */
    private String fbIssue;
    /* 联系方式 */
    private String linkTel;
    /* 图片 */
    private String fbPictures;
    /* 创建时间 */
    @Column(insertable = false, updatable = false)
    private Timestamp createTime;

}
