package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnterpriseInformationVO implements Serializable {
    private String epName;
    private String epShortName;
    private String logoUrl;
}
