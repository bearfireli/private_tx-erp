package com.hntxrj.txerp.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 企业产品表
 * @author lhr
 */
@Entity
@Data
@ToString
public class UserCompProduct {


    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 产品
     */
    @OneToOne(targetEntity = DeProduct.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private DeProduct product;


    /**
     *  产品到期时间
     */
    private java.sql.Timestamp productExpiryTime;

    /**
     * 企业id
     */
    @OneToOne(targetEntity = UserComp.class)
    @JoinColumn(name = "compid", referencedColumnName = "compid")
    private UserComp comp;

}
