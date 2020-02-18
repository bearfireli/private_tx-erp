package com.hntxrj.txerp.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "De_MsgQueue")
public class MsgQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msgId;
    private String msgTag;
    private String msgType;
    private String msgOp;
    private java.sql.Timestamp msgCreateTime;
    private long msgStatusTxErp;
    private String msgKeys;

}
