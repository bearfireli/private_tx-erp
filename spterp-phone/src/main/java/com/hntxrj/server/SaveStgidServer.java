package com.hntxrj.server;


import com.alibaba.fastjson.JSONArray;

public interface SaveStgidServer {

    JSONArray sp_insertUpDel_SM_StgidInfoPrice(String mark, String compid, String stgid, String performmonth, Double notpumpprice, Double pumpprice, Double towercraneprice, String opid);


    /**
     * 砼标号管理     sp_insertUpDel_SM_GradePriceInfo
     * @param mark 0 插入 1 更新 2 删除
     * @param compid 企业
     * @param id 无
     * @param stgid 砼标号
     * @param grade  等级
     * @param pumpprice 泵送价格
     * @param notpumpprice 非泵送价格
     * @param pricedifference 差价
     * @param isdefault 是否默认
     * @param towercraneprice 塔吊价格
     * @param recstatus 状态
     * @param opid 用户
     * @return json
     */
    JSONArray sp_insertUpDel_SM_GradePriceInfo(String mark, String compid, String id, String stgid, String grade, Double pumpprice, Double notpumpprice, Double pricedifference, Byte isdefault, Double towercraneprice, Byte recstatus, String opid);



    /**
     * 查看砼标号
     * @param opid 用户
     * @param compid 企业
     * @param stgid 砼标号
     * @param grade 强度等级
     * @param pagesize 页长度
     * @param currpage 当前页
     * @return json
     */
    JSONArray sp_quancha_SM_GradePriceInfo(String opid, String compid, String stgid, String grade, Integer pagesize, Integer currpage);
}
