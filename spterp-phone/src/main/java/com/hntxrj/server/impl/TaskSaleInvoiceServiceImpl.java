package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.exception.ErpException;
import com.hntxrj.exception.ErrEumn;
import com.hntxrj.mapper.TaskSaleInvoiceMapper;
import com.hntxrj.server.TaskSaleInvoiceService;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.TaskSaleInvoiceDetailVO;
import com.hntxrj.vo.TaskSaleInvoiceDriverListVO;
import com.hntxrj.vo.TaskSaleInvoiceListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TaskSaleInvoiceServiceImpl implements TaskSaleInvoiceService {

    private final TaskSaleInvoiceMapper taskSaleInvoiceMapper;

    @Autowired
    public TaskSaleInvoiceServiceImpl(TaskSaleInvoiceMapper taskSaleInvoiceMapper) {
        this.taskSaleInvoiceMapper = taskSaleInvoiceMapper;
    }


    @Override
    public PageVO<TaskSaleInvoiceListVO> getTaskSaleInvoiceList(String compid, String beginTime, String endTime,
                                                                String eppCode, Byte upStatus, String builderCode, String taskId,
                                                                String placing, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceListVO> taskSaleInvoiceLists = taskSaleInvoiceMapper.getTaskSaleInvoiceList(compid, beginTime,
                endTime, eppCode, upStatus, builderCode, taskId, placing);
        PageInfo<TaskSaleInvoiceListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        PageVO<TaskSaleInvoiceListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<TaskSaleInvoiceDriverListVO> getTaskSaleInvoiceList(Integer id, String compid, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String placing, Integer page, Integer pageSize, String driverCode) {
        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceDriverListVO> taskSaleInvoiceLists = null;

        if (id != null) {
            taskSaleInvoiceLists = taskSaleInvoiceMapper.driverGetTaskSaleInvoiceListById(id, driverCode);
        } else {
            taskSaleInvoiceLists =
                    taskSaleInvoiceMapper.driverGetTaskSaleInvoiceList(compid, beginTime,
                            endTime, eppCode, upStatus, builderCode, placing, driverCode);
        }


        PageInfo<TaskSaleInvoiceDriverListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        PageVO<TaskSaleInvoiceDriverListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetail(String compid, Integer id) {
        return taskSaleInvoiceMapper.getTaskSaleInvoiceDetailVO(id, compid);
    }


    /**
     * 小票签收审核
     *
     * @param compid  　企业id
     * @param id      　　　小票id
     * @param opid    　　当前用户
     * @param qiannum 　签收数量
     * @throws ErpException 定义的异常
     */
    @Override
    public void getTaskSaleInvoiceExamine(String compid, Integer id, String opid, Double qiannum,
                                          String saleFileImage) throws ErpException {
        try {
            //获取签收时间
            Date SigningTime = new Date();
            taskSaleInvoiceMapper.getTaskSaleInvoiceExamine(compid, id, opid, qiannum, saleFileImage, SigningTime);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.VERIFY_TICKET_ERROR);
        }
    }
}
