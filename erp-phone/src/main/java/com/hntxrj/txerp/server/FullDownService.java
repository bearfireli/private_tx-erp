package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;

/**
 * 功能: 下拉加载Service层
 *
 * @Auther 李帅
 * @Data 2017-08-14.上午 11:34
 */
public interface FullDownService {

    /**
     *   加载下拉详细内容
     * @param id  1 浇筑方式 2  水泥品种 3  石料要求 4  泵送否 5 塌落度 6  任务单类型 7 企业信息下拉
     * @return
     */
    JSONArray loadFullDown(int id);



    /**
     *  加载砼标号下拉
     * @param ContractUID  合同UID
     * @param CContractCode   子合同编号
     * @param pageBean 分页信息
     * @return
     */
    JSONArray loadStgId(String ContractUID, String CContractCode, PageBean pageBean);


    /**
     *  下拉查询带参数带分页
     * @param type  类型
     * @param param  参数
     * @param pageBean  分页
     * @return
     */
    JSONArray getFullDown(Integer type ,String param, PageBean pageBean,String compid);
}
