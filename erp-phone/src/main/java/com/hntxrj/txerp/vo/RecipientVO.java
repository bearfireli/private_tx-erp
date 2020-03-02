package com.hntxrj.txerp.vo;

import lombok.Data;

@Data
public class RecipientVO {
    /*id*/
    private Integer uid;
    /*接收人姓名*/
    private String username;
    private String phone;
    /*站别代号*/
    private String compid;
    /*类型id*/
    private Integer typeId;
}
