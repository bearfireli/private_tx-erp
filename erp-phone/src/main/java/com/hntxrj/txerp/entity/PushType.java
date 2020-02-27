package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class PushType implements Serializable {

    @Id
    @Column(name = "typeId")
    private String typeId;
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "recStatus")
    private String recStatus;

}
