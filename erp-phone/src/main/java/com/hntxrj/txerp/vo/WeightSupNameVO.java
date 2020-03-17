package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;

@Data
public class WeightSupNameVO {
    /*材料*/
    private String matName;
    /*供应商*/
    private String supName;
    /*供应商代号*/
    private String supCode;
    /*联系人*/
    private String supLinkMan;
    /*入库量*/
    private double tlWeight;
    /*方量*/
    private double proportion;
    /*车数*/
    private int vCount;
    /*供应商供应的材料信息集合*/
    private List<WeightMatParentNameVO> matNameVOList;

}
