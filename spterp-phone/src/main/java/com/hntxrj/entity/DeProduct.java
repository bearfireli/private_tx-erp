package com.hntxrj.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 产品表
 * @author lhr
 */
@Data
@Entity
@ToString
public class DeProduct {


    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品状态
     */
    private Integer productStatus;

    /**
     * 产品单价
     */
    private BigDecimal productPrice;



}
