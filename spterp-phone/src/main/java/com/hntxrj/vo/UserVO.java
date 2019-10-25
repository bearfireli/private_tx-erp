package com.hntxrj.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhr
 * @create 2018/1/30
 */
@Data
public class UserVO {

    private String opId;
    private String compid;
    private String compName;
    private String pwd;
    private String empname;
    private String loginName;
    private String groupCode;
    private List<Integer> groupCodes = new ArrayList<>();
    private List<String> groupName = new ArrayList<>();


}
