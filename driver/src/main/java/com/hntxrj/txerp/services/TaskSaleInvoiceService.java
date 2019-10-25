package com.hntxrj.txerp.services;

import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;

public interface TaskSaleInvoiceService {

    TaskSaleInvoiceDetailVO getTaskSaleInvoice(Integer id, String compid);

}
