package com.hntxrj.txerp.vo;

import lombok.Data;

@Data
public class RecipientVO {
    /*id*/
    private Integer id;
    /*接收人手机号*/
    private String name;
    /*站别代号*/
    private String compid;
    /*类型id*/
    private Integer typeId;
}
