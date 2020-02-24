package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Recipient implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    //收信人
    @Column(name = "name")
    private String name;
    //企业ID
    @Column(name = "compid")
    private String compid;
    //推送类型
    @Column(name = "typeId")
    private int typeId;


}
