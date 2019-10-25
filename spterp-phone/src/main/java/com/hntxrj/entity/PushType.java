package com.hntxrj.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

//@Entity
@Data
public class PushType implements Serializable {

    /* 推送代号 */
    private String pushCode;
    /* 推送描述 */
    private String pushDescribe;
    /* 推送模板 */
    private String pushTemplate;

}
