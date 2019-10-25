package com.hntxrj.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author qyb
 * @ClassName ConcreteVO
 * @Description 砼产量统计
 * @Date 19-6-10 下午4:57
 * @Version 1.0
 **/
@Data
public class ConcreteVO implements Serializable {

    /**
     * 日期
     */
    private String sendTime;
    /**
     * 任务单号
     */
    private String taskId;
    /**
     * 施工单位
     */
    private String builderName;

    /**
     * 工程名称
     */
    private String eppName;

    /**
     * 浇筑部位
     */
    private String placing;

    /**
     * 车数
     */
    private String carNum;

    /**
     * 强度
     */
    private String stgId;

    /**
     * 产量
     */
    private String produceNum;

    /**
     * 销量
     */
    private String saleNum;

    /**
     * 销量合计
     */
    private String saleNumList;
}
