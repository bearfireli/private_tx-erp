package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface TaskSaleInvoiceService {


    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param invoiceId   企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param type        搜索时间类型：0表示派车时间；1表示出厂时间
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    PageVO<TaskSaleInvoiceListVO> getTaskSaleInvoiceList(Integer invoiceId, String compid, String beginTime, String endTime,
                                                         String eppCode, Byte upStatus, String builderCode, String taskId,
                                                         String placing, String taskStatus, Integer type, Integer page,
                                                         Integer pageSize);


    /**
     * 获取小票需求
     *
     * @param id     小票id
     * @param compid 企业id
     * @return 小票详情
     */
    TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetail(String compid, Integer id);

    /**
     * 小票签收审核
     *
     * @param compid           　企业id
     * @param id               　小票id
     * @param opid             　当前用户
     * @param numberOfSignings 　签收数量
     * @throws ErpException 定义的异常
     */
    void getTaskSaleInvoiceExamine(String compid, Integer id, String opid, Double numberOfSignings,
                                   String saleFileImage) throws ErpException;

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
    TaskSaleInvoiceCountVO getTaskSaleInvoiceCount(Integer id, String compid, String beginTime, String endTime, String eppCode,
                                                   Byte upStatus, String builderCode, String taskId,
                                                   String placing, String taskStatus, Integer type);
}
