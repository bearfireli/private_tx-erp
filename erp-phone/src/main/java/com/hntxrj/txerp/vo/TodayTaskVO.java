package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName TodayTaskVO
 * @Description TODO
 * @Date 19-8-20 下午4:44
 * @Version 1.0
 **/
@Data
public class TodayTaskVO {
    private  Integer taskStatus;
    /*等待生产的任务单数量*/
    private  Integer taskStatus1;
    /*正在生产的任务单数量*/
    private  Integer taskStatus2;
    /*暂停的任务单数量*/
    private  Integer taskStatus3;
    /*完成的任务单数量*/
    private  Integer taskStatus4;
    /*删除的任务单数量*/
    private  Integer taskStatus5;

}
