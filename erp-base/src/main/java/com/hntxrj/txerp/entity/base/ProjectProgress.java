package com.hntxrj.txerp.entity.base;


import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class ProjectProgress implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 项目进度id */
    private Integer ppid;
    /* 项目名称 */
    private String projectName;
    /* 项目负责人id */
    private Integer headUid;
    /* 验收人 */
    private Integer checkUid;
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
    /* 输出成果 */
    private String fruit;
    /* 项目内容 */
    private String projectContent;
    /* 进度 */
    private String schedule;
    /* 0未开始，1正在进行，2等待验收，3已验收 */
    private Integer status;

}
