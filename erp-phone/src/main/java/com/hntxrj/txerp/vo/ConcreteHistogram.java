package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 产销统计中的柱状图
 * */

@Data
public class ConcreteHistogram implements Serializable {


    private String dateTime;

    private String saleNum;

}
