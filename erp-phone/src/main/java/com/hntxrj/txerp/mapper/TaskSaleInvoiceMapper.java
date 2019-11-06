package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDriverListVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskSaleInvoiceMapper {
    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @return 小票签收列表
     */
    List<TaskSaleInvoiceListVO> getTaskSaleInvoiceList(String compid, String beginTime, String endTime,
                                                       String eppCode, Byte upStatus, String builderCode,
                                                       String taskId, String placing);
    List<TaskSaleInvoiceDriverListVO> driverGetTaskSaleInvoiceListById(Integer id, String driverCode);

    List<TaskSaleInvoiceDriverListVO> driverGetTaskSaleInvoiceList(String compid, String beginTime, String endTime,
                                                                   String eppCode, Byte upStatus, String builderCode,
                                                                   String placing, String driverCode);

    /**
     * 获取小票需求
     *
     * @param id     小票id
     * @param compid 企业id
     * @return 小票详情
     */
    TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetailVO(Integer id, String compid);


    /**
     * 小票签收审核
     *
     * @param compid      　企业id
     * @param id          　　　小票id
     * @param opid        　　当前用户
     * @param qiannum     　签收数量
     * @param signingTime 签收时间
     */
    void getTaskSaleInvoiceExamine(String compid, Integer id, String opid, Double qiannum, String saleFileImage, Date signingTime);

}