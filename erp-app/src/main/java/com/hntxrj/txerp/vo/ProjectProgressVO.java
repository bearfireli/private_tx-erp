package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectProgressVO implements Serializable {

    /* 项目进度id */
    private Integer ppid;
    /* 项目名称 */
    private String projectName;
    /* 项目负责人id */
    private Integer headUid;
    private String headName;

    /* 验收人 */
    private Integer checkUid;
    private String checkName;

    /* 计划开始时间 */
    private Date planningBeginTime;
    /* 计划完成时间 */
    private Date planningEndTime;
    /* 实际开始时间 */
    private Date actualBeginTime;
    /* 实际结束时间 */
    private Date actualEndTime;
    /* 工作摘要 */
    private String workSummary;
    /* 输出成功 */
    private String fruit;
    /* 项目内容 */
    private String projectContent;
    /* 进度 */
    private String schedule;
    /* 0未开始，1正在进行，2等待验收，3已验收 */
    private Integer status;


}
