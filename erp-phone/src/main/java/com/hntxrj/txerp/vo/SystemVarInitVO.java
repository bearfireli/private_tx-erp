package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName SystemVarInitVO
 * @Description 系统变量表
 * @Date 2019/11/12 下午3:05
 * @Version 1.0
 **/
@Data
public class SystemVarInitVO {
    private int id;
    private String systemVarName;
    private String systemVar;
    private  int varValue;
}
