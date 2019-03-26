package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.EnterpriseAfterSale;
import com.hntxrj.txerp.entity.base.ProjectLifespan;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.EnterpriseAfterSaleVO;
import com.hntxrj.txerp.vo.PageVO;

public interface EnterpriseAfterSaleService {

    PageVO<EnterpriseAfterSaleVO> list(String token, Integer enterprise, long page, long pageSize) throws ErpException;


    EnterpriseAfterSale save(EnterpriseAfterSale enterpriseAfterSale,
                             String token,
                             ProjectLifespan projectLifespan) throws ErpException;
}
