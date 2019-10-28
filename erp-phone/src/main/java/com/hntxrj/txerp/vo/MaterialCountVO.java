package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName MaterialCountVO
 * @Description 材料入库汇总
 * @Date 19-6-11 下午2:36
 * @Version 1.0
 **/
@Data
public class MaterialCountVO {
    /**
     *线号
     */
    private  String stirName;
    /**
     * 项目
     */
    private  String stoName;
    /**
     * 进仓量
     */
    private  double clWeight;
    /**
     *消耗量
     */
    private  double conWeight;
    /**
     * 实际库存
     */
    private  double stoCurqty;
    private String matSpecs;
}
