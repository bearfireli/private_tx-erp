package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qyb
 * @ClassName bdBindVO
 * @Description TODO
 * @Date 19-8-15 下午5:39
 * @Version 1.0
 **/
@Data
public class bdBindVO implements Serializable {

    private String buildid;  //施工方Id
    private String compid;    //公司代号
    private String contractUID;  //主合同号
    private String contractDetailCode; //子合同号
}
