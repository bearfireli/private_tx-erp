package com.hntxrj.txerp.dao;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;

import java.sql.Timestamp;

/**
 * 功能:  施工单位Dao接口
 *
 * @auther 李帅
 * @Data 2017-08-11.上午 11:08
 */
public interface BuilderDao {
    /**
     *   加载详情列表
     * @param builderName 工程名
     * @param pageBean 页码相关实体
     * @return
     */
    JSONArray getBuilderList(String builderName,PageBean pageBean,String compid);


    /**
     *    添加修改删除  施工单位
     * @param Mark    操作标识 0 插入 1 更新 2 删除
     * @param compid    企业ID
     * @param OpId     操作员ID
     * @param BuilderCode   施工单位代号
     * @param BuilderName_1     施工单位名
     * @param BuilderShortName_2    施工单位名简称
     * @param Address_3     地址
     * @param CreateTime_4     创建时间
     * @param Corporation_5    法人
     * @param Fax_6       传真
     * @param LinkTel_7    联系电话
     * @param RecStatus_8   记录状态(有效)   0未启用 1启用(0无效1有效)
     * @return
     */
    JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String OpId, String BuilderCode,
                                         String BuilderName_1, String BuilderShortName_2, String Address_3, Timestamp CreateTime_4,
                                         String Corporation_5, String Fax_6, String LinkTel_7, byte RecStatus_8);
}
