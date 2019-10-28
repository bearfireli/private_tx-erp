package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDriverListVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceListVO;
import org.springframework.stereotype.Service;

@Service
public interface TaskSaleInvoiceService {


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
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    PageVO<TaskSaleInvoiceListVO> getTaskSaleInvoiceList(String compid, String beginTime, String endTime,
                                                         String eppCode, Byte upStatus, String builderCode, String taskId,
                                                         String placing, Integer page, Integer pageSize);

    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param placing     浇筑部位
     * @param page        页数
     * @param pageSize    每页数量
     * @param driverCode  司机代号
     * @return 小票签收列表
     */
    PageVO<TaskSaleInvoiceDriverListVO> getTaskSaleInvoiceList(Integer id, String compid, String beginTime, String endTime, String eppCode,
                                                               Byte upStatus, String builderCode, String placing,
                                                               Integer page, Integer pageSize, String driverCode);

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
     * @param compid  　企业id
     * @param id      　　　小票id
     * @param opid    　　当前用户
     * @param qiannum 　签收数量
     * @throws ErpException 定义的异常
     */
    void getTaskSaleInvoiceExamine(String compid, Integer id, String opid, Double qiannum,
                                   String saleFileImage) throws ErpException;
}
