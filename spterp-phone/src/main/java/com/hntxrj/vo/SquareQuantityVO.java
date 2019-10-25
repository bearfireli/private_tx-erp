package com.hntxrj.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName SquareQuantityVO
 * @Description TODO
 * @Date 19-8-9 上午8:48
 * @Version 1.0
 **/
@Data
public class SquareQuantityVO {

    /*已完成*/
    private double produceNum;
    /*计划产量总量*/
    private int produce_num;
    private double sale_num;
    /*方量统计(今日，昨日，本月)*/
    private double saleNum;
    private double PreNum;
    private int pre_num;
}
