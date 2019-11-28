package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 产销统计中的砼标号饼图
 */
@Data
public class ConcretePieChart implements Serializable {

    private String stgId;
    private String saleNum;
}
