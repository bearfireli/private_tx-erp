package com.hntxrj.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询供货商，业务类别，库位,材料名称一共多少种
 */
@Data
public class ConsultSupplierVO implements Serializable {

    //供货商名称
    private String supName;
    //库位
    private String stoName;
    //业务类别
    private String pITypeName;
    //供货商代号
    private String supCode;
    //库位代号
    private String stkCode;
    //业务类别代号
    private String saleType;
    //材料名称代号
    private String matCode;
    //材料名称
    private String matName;
    //过磅员名称
    private String oper1Name;
    private String oper2Name;
    //过磅员1代号
    private String opid;
    //车辆号码
    private String vehicleId;


}
