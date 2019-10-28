package com.hntxrj.txerp.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 附件
 */
@Data
public class Adjunct implements Serializable {

    /* 附件id */
    private Integer adjunctId;
    /* 附件名 */
    private String adjunctName;
    /* 附件类型，1为合同，2为任务单 */
    private Integer adjunctType;
    /* 外键关联到指定数据 */
    private String adjunctKey;
    /* 第几个附件 */
    private Integer adjunctNum;
    /* 存放在服务器上的文件名 */
    private String adjunctFileName;
    /* 企业id */
    private String compid;

}
