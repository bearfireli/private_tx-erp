package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.vo.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

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
     * @param compid      企业id
     */
    JSONArray getEppList(String builderName, PageBean pageBean, String compid);


    /**
     * 添加修改删除  施工单位
     *
     * @param Mark             操作标识 0 插入 1 更新 2 删除
     * @param compid           企业ID
     * @param opid             操作员ID
     * @param BuilderCode      施工单位代号
     * @param BuilderName      施工单位名
     * @param BuilderShortName 施工单位名简称
     * @param Address          地址
     * @param CreateTime       创建时间
     * @param Corporation      法人
     * @param Fax              传真
     * @param LinkTel          联系电话
     * @param RecStatus        记录状态(有效)   0未启用 1启用(0无效1有效)
     */
    JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String opid, String BuilderCode,
                                         String BuilderName, String BuilderShortName, String Address,
                                         Timestamp CreateTime, String Corporation, String Fax,
                                         String LinkTel, byte RecStatus);


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
     * @param compid      企业id
     */
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
     * @param type      　type: 查询时间类型; 1:派车时间；0:离场时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     * @return 产销统计列表
     */
    PageVO<ConcreteVO> getBuilderConcreteCount(Integer buildId, String eppCode, String placing,
                                               String taskId, String stgId, String beginTime, String endTime,
                                               Integer type, Integer page, Integer pageSize) throws ErpException;

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
    PageVO<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(Integer buildId, String beginTime, String endTime,
                                                              String eppCode, Byte upStatus, String builderCode,
                                                              String taskId, String placing, String taskStatus,
                                                              Integer page, Integer pageSize) throws ErpException;

    /**
     * 调度派车列表
     *
     * @param buildId     企业代号
     * @param searchWords 搜索关键字
     * @param page        页码
     * @param pageSize    每页数量
     * @return 调度派车列表
     */
    PageVO<SendCarListVO> getBuildSendCarList(Integer buildId, String searchWords, Integer page,
                                              Integer pageSize) throws ErpException;

    /**
     * 工地端App产销总量统计
     *
     * @param buildId   　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param type      　　　type: 查询时间类型; 1:派车时间；0:离场时间
     */
    Map<String, BigDecimal> getBuilderConcreteSum(Integer buildId, String eppCode, String placing, String taskId,
                                                  String stgId, String beginTime, String endTime,
                                                  Integer type) throws ErpException;

    /**
     * 工地端获取任务单详情
     *
     * @param compid  企业id
     * @param taskId  任务单id
     * @param buildId 施工方用户id
     * @return 任务单数据
     */
    TaskPlanVO getBuildTaskPlanDetail(String compid, String taskId, Integer buildId) throws ErpException;

    /**
     * 工地端获取任务单详情
     *
     * @param compid  企业id
     * @param id      小票id
     * @param buildId 施工方用户id
     * @return 任务单数据
     */
    TaskSaleInvoiceDetailVO getBuildTaskSaleInvoiceDetail(String compid, Integer id, Integer buildId) throws ErpException;

    /**
     * 检验工地端用户绑定的合同是否包含小票
     *
     * @param buildId 施工方用户id
     * @param compid  企业id
     * @param id      小票id
     */
    TaskSaleInvoiceDetailVO checkTaskSaleInvoice(Integer buildId, String compid, Integer id) throws ErpException;

    /**
     * 添加施工单位
     *
     * @param compid           //企业代号
     * @param builderName      //施工方名称
     * @param builderShortName //施工方简称
     * @param address          // 地址
     * @param corporation      //法人
     * @param fax              //传真
     * @param phone            //联系人
     */
    @Transactional
    void addBuilderInfo(String compid, String builderName, String builderShortName, String address,
                        String corporation, String fax, String phone);
}
