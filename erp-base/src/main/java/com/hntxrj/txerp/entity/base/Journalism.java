
package com.hntxrj.txerp.entity.base;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Journalism implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*新闻id*/
    private  Integer id;
    /*新闻标题*/
    private  String title;
    /*新闻详情*/
    private String  content;
    /*新闻图片*/
    private String img;
    /*创建时间*/
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /*修改时间*/
    @Column(insertable = false, updatable = false)
    private  Date updateTime;
    /*创建人*/
    private Integer createUser;
    /*点击量*/
    private  Integer clickRate;
}

