package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class TaskPlanPK implements Serializable {
    /* 站别代号 */
    @Id
    @Column(name = "compid")
    private String compid;
    /* 任务单号 */
    @Id
    @Column(name = "taskid")
    private String taskId;

}
