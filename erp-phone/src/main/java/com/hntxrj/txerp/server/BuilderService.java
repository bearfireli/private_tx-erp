package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.vo.*;

import java.sql.Timestamp;

/**
 * 功能:  施工服务接口
 * <p>
 * 李帅
 * 2017-08-11.上午 10:58
 */
public interface BuilderService {

    /**
     * 加载详情列表
     *
     * @param builderName 工程名
     * @param pageBean    页码相关实体
     */
    JSONArray getEppList(String builderName, PageBean pageBean, String compid);


    /**
     * 添加修改删除  施工单位
     *
     * @param Mark               操作标识 0 插入 1 更新 2 删除
     * @param compid             企业ID
     * @param OpId               操作员ID
     * @param BuilderCode        施工单位代号
     * @param BuilderName_1      施工单位名
     * @param BuilderShortName_2 施工单位名简称
     * @param Address_3          地址
     * @param CreateTime_4       创建时间
     * @param Corporation_5      法人
     * @param Fax_6              传真
     * @param LinkTel_7          联系电话
     * @param RecStatus_8        记录状态(有效)   0未启用 1启用(0无效1有效)
     */
    JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String OpId, String BuilderCode,
                                         String BuilderName_1, String BuilderShortName_2, String Address_3, Timestamp CreateTime_4,
                                         String Corporation_5, String Fax_6, String LinkTel_7, byte RecStatus_8);


    /**
     * 获取施工单位下拉
     *
     * @param builderName 施工单位名称
     * @param compid      企业id
     * @param page        页码
     * @param pageSize    每页数量
     * @return 施工单位下拉对象
     */
    PageVO<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName, Integer page, Integer pageSize);


    /**
     * 获取施工单位信息
     *
     * @param builderCode 施工单位编号
     * @param compid 企业id
     * */
    BuilderInfo getBuilderInfo(String builderCode, String compid);

    /**
     * 工地端App产销统计
     *
     * @param buildId   　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     * @return 产销统计列表
     */
    PageVO<ConcreteVO> getBuilderConcreteCount(Integer buildId, String eppCode, String placing, String taskId, String stgId, String beginTime, String endTime, Integer timeStatus, Integer page, Integer pageSize);

    /**
     * 获取小票签收列表
     *
     * @param buildId     企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param upStatus    签收状态
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    PageVO<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(Integer buildId, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String taskId, String placing, String taskStatus, Integer page, Integer pageSize);

    /**
     * 调度派车列表
     *
     * @param buildId    企业代号
     * @param searchName 搜索关键字
     * @param page       页码
     * @param pageSize   每页数量
     * @return 调度派车列表
     */
    PageVO<SendCarListVO> getBuildSendCarList(Integer buildId, String searchName, Integer page, Integer pageSize);
}
