package com.hntxrj.txerp.entity.sell;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Salesman implements Serializable {

    /* 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /* 销售员名称 */
    private String salesmanName;
    /* 销售员代号 */
    private String salesmanCode;
    /* 所属类别 */
    private String department;
    /* 所属企业 */
    private Integer enterprise;
    /* 删除否 */
    private Integer del;

}
