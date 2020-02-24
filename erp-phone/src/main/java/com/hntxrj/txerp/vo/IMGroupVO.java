package com.hntxrj.txerp.vo;


import lombok.Data;
import java.io.Serializable;

@Data
public class IMGroupVO implements Serializable {
    private String OwnerAccount;        //  群主的userId
    private  String type = "Public";        //群组类型(必填，默认Public)
    private String name;        //群名称(必填)

}
