package com.hntxrj.txerp.service;

import com.hntxrj.txerp.entity.sell.*;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.ContractListVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.entity.sell.*;

import java.util.List;


public interface ContractService {

    Contract saveMasterContract(Contract Contract, String token) throws ErpException;


    ContractDetails saveContractDetails(ContractDetails contractDetails, String token) throws ErpException;

    PageVO<ContractListVO> list(String builderName, String engineeringName, String contractId,
                                Integer saleUid, Integer contractStatus, Integer del,
                                long page, long pageSize, String token) throws ErpException;


    ContractDetails getContractDetails(String cdUid) throws ErpException;

    Contract getMasterContract(String cmUid) throws ErpException;

    /**
     * 获取主合同下所有子合同
     *
     * @param cmUid 主合同id
     * @return 子合同集合
     */
    List<ContractDetails> getAllContractDetails(String cmUid);


    PageVO<GradePrice> gradePriceList(String stgId, Integer enterprise, long page, long pageSize);

    PageVO<PriceMarkup> priceMarkupList(String projectName, Integer enterprise, long page, long pageSize);

    PageVO<PumpPrice> pumpPriceList(String typeName, Integer enterprise, long page, long pageSize);

    PageVO<Salesman> salesmanList(String salesmanName, String salesmanCode,
                                  Integer enterprise, long page, long pageSize);

    PageVO<ContractPumpPrice> contractPumpPriceList(String cdUid, long page, long pageSize);

    PageVO<ContractGradePrice> contractGradePriceList(String cdUid, long page, long pageSize);

    PageVO<ContractPriceMarkup> contractPriceMarkupList(String cdUid, long page, long pageSize);

    GradePrice saveGradePrice(GradePrice gradePrice, String token) throws ErpException;

    PriceMarkup savePriceMarkup(PriceMarkup priceMarkup, String token) throws ErpException;

    PumpPrice savePumpPrice(PumpPrice pumpPrice, String token) throws ErpException;

    Salesman saveSalesman(Salesman salesman, String token);

    ContractGradePrice saveContractGradePrice(ContractGradePrice contractGradePrice, String token) throws ErpException;

    ContractPriceMarkup saveContractPriceMarkup(ContractPriceMarkup contractPriceMarkup, String token);

    ContractPumpPrice saveContractPumpPrice(ContractPumpPrice contractPumpPrice, String token);

    void saveContractGradePrices(List<ContractGradePrice> contractGradePrices, String token) throws ErpException;

    void saveContractPriceMarkups(List<ContractPriceMarkup> contractPriceMarkups, String token);

    void saveContractPumpPrices(List<ContractPumpPrice> contractPumpPrices, String token);

}
