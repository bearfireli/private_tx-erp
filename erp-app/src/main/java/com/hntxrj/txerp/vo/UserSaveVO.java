package com.hntxrj.txerp.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserSaveVO  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    //新添加用户的hashcode,此字段只在添加用户时使用
    private Integer Identification;
}
