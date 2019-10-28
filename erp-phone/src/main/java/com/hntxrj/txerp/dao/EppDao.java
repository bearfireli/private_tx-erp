package com.hntxrj.txerp.dao;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;

/**
 * 功能:  工程Dao接口
 *
 * @Auther 李帅
 * @Data 2017-08-11.上午 10:46
 */
public interface EppDao {

    /**
     *   加载详情列表
     * @param eppName  工程名
     * @param pageBean 页码相关实体
     * @return
     */
    JSONArray getEppList(String eppName, PageBean pageBean, String compid);


    /**
     *  加载 浇筑部位
     * @param eppCode  工程代号
     * @param pageBean   分页
     * @return
     */
    JSONArray getEppPlacing(String eppCode,String placing,  PageBean pageBean,String compid);
}
