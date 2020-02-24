package com.hntxrj.txerp.vo;

import lombok.Data;

@Data
public class IMUserVO {
    private String identifier;      //即时通讯用户名
    private String Nick;        //用户昵称
    private String faceUrl;     //用户头像地址
}
