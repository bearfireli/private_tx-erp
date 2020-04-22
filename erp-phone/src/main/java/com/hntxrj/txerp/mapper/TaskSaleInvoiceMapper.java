package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.TaskSaleInvoiceCountVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
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
     * @param invoiceId   小票id
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param type        搜索时间类型：0表示派车时间；1表示出厂时间
     * @return 小票签收列表
     */
    List<TaskSaleInvoiceListVO> getTaskSaleInvoiceList(String invoiceId, String compid, String beginTime, String endTime,
                                                       String eppCode, Byte upStatus, String builderCode,
                                                       String taskId, String placing, String taskStatus, Integer type);

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
     * @param compid           　企业id
     * @param id               　　　小票id
     * @param opid             　　当前用户
     * @param numberOfSignings 　签收数量
     * @param signingTime      签收时间
     */
    void getTaskSaleInvoiceExamine(String compid, Integer id, String opid, Double numberOfSignings, String saleFileImage,
                                   Date signingTime);

    /**
     * 获取小票签收列表
     *
     * @param id          小票id
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param taskStatus  生产状态
     * @return 小票签收列表
     */
    TaskSaleInvoiceCountVO getTaskSaleInvoiceCount(String id, String compid, String beginTime, String endTime,
                                                   String eppCode, Byte upStatus, String builderCode,
                                                   String taskId, String placing, String taskStatus, Integer type);
}
