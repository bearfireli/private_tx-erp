package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/*生产消耗柱状图*/

@Data
public class ConsumptionHistogram implements Serializable {

    //标号
    private String stgId;

    //方量
    private double num;

}
