package com.hntxrj.txerp.vo;

import lombok.Data;

@Data
public class MaterialVO {
    /**
     *材料代号
     */
    private  String MatCode;
    /**
     * 材料名称
     */
    private  String MatName;
    /**
     * 材料归属
     */
    private  String  MatParent;

}
