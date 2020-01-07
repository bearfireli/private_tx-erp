package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qyb
 * @ClassName BuildAccountsVO
 * @Description TODO
 * @Date 19-8-23 下午2:17
 * @Version 1.0
 **/
@Data
public class BuildAccountsVO implements Serializable {
    private  int id ;
    private String loginName ;
    private  String password;
    private String buildName;
}
