package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierVO implements Serializable {
    private String supName;  //供应商名称
    private String supCode;  //供应商编号
}
