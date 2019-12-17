package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qyb
 * @ClassName InvitationVO
 * @Description TODO
 * @Date 19-7-25 下午4:16
 * @Version 1.0
 **/
@Data
public class InvitationVO implements Serializable {

    private String buildinvitationcode;  //施工方邀请码
    private String compid;  //公司名称
    private String builderName;  //施工单位
    private String usestatus;  //使用状态
    private String createuser;  //创建人
    private String createtime;  //创建时间
    private String ccontractCode; //子合同号
    private String contractUID;  //主合同号
    private String eppName; //工程名称
    private String shortName; //工程名称简称


}
