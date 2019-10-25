package com.hntxrj.txerp.vo;

import lombok.Data;


import java.io.Serializable;

@Data
public class UserSaveVO  implements Serializable {

    private Integer uid;

    //新添加用户的hashcode,此字段只在添加用户时使用
    private Integer Identification;
}
