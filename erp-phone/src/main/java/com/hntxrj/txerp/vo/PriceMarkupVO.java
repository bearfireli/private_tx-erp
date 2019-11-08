package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 添加任务单是获取的加价项目下拉列表
 */
@Data
public class PriceMarkupVO implements Serializable {


    //加价特殊材料名称
    private String PPName;

    //材料代号
    private String PPCode;

    String MeasureUnit;   //价格单位
    BigDecimal UnitPrice;  //价格
    BigDecimal SelfDiscPrice;        //自卸价
    BigDecimal JumpPrice;      //泵送价
    BigDecimal TowerCranePrice;       //塔掉价
    BigDecimal OtherPrice;      // 其他价

}
