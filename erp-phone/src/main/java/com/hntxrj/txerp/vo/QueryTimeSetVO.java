package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName QueryTimeSetVO
 * @Description TODO
 * @Date 19-8-9 上午9:08
 * @Version 1.0
 **/
@Data
public class QueryTimeSetVO {
    private String compid;
    private String queryName;
    private int queryCode;
    private int queryType;
    private int querytime;
    private String queryStartTime;
    private String queryStopTime;
    private int recStatus;
    private int countInterval;
    private int upDownMark;
    private int upDown;
    private String beginTime;
    private String endTime;
}
