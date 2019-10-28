package com.hntxrj.txerp.service;

public interface PVStatisticsService {

    /* 访问量增加 */
    void augmentPV(String name, Integer eid);


    /* 保存访问量数据 */
    void savePVData();

}
