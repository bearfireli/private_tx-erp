package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskSaleInvoiceCountVO implements Serializable {

    private Double saleNum;     //总发货放量
    private Double signNum;     //总签收方量
}
