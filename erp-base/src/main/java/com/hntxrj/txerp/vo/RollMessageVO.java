package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RollMessageVO implements Serializable {

    private Integer id;         //id
    private String content;     //消息内容
    private Date beginTime;     //开始显示时间
    private Date endTime;       //结束显示时间
    private Integer uid;        //创建人id
    private String createUser;  //创建人姓名
    private Date createTime;    //创建时间
    private Date updateTime;    //修改时间
    private String compid;      //企业代号
    private Byte type;          //显示类型0:全部企业都显示；  1:指定企业显示

}
