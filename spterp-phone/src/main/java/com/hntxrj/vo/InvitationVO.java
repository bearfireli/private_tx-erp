package com.hntxrj.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName InvitationVO
 * @Description TODO
 * @Date 19-7-25 下午4:16
 * @Version 1.0
 **/
@Data
public class InvitationVO {

    private String buildinvitationcode;
    private String compid;
    private String buildcode;
    private String usestatus;
    private String createuser;
    private String createtime;
    private String builderName;

}
