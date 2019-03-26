package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.sell.*;
import com.hntxrj.txerp.entity.sell.*;
import com.hntxrj.txerp.service.BuilderService;
import com.hntxrj.txerp.service.ContractService;
import com.hntxrj.txerp.service.EngineeringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contract")
@Slf4j
public class ContractController {

    private final EngineeringService engineeringService;
    private final BuilderService builderService;
    private final ContractService contractService;
    private ResultVO resultVO;

    @Autowired
    public ContractController(EngineeringService engineeringService, BuilderService builderService,
                              ContractService contractService) {
        this.engineeringService = engineeringService;
        this.builderService = builderService;
        this.contractService = contractService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
    }


    /* ============================================================================================================== */
    /*                                                   工程名称                                                      */
    /* ============================================================================================================== */
    @PostMapping("/engineering")
    public String engineering(@RequestBody String param) throws ErpException {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(engineeringService.findById(paramObj.getInteger("id")))));

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/engineeringDownDrop")
    public String engineeringDownDrop(@RequestBody String param,
                                      @RequestHeader(name = "token") String token,
                                      @RequestHeader(name = "enterprise") Integer enterprise) throws ErpException {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(engineeringService.engineeringDownDrop(enterprise,
                paramObj.getString("engineeringName"),
                paramObj.getInteger("page"),
                paramObj.getInteger("pageSize")))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/engineeringList")
    public String engineeringList(@RequestBody String param,
                                  @RequestHeader(name = "token") String token,
                                  @RequestHeader(name = "enterprise") Integer enterprise)
            throws ErpException {
        JSONObject paramObj = JSONObject.parseObject(param);
        log.info("【工程名称】paramObj={}", paramObj);
        String engineeringCode = paramObj.getString("engineeringCode") == null ?
                "" : paramObj.getString("engineeringCode");
        String engineeringName = paramObj.getString("engineeringName") == null ?
                "" : paramObj.getString("engineeringName");
        String linkMan = paramObj.getString("linkMan") == null ?
                "" : paramObj.getString("linkMan");

        Integer page = paramObj.getInteger("page") == null || paramObj.getInteger("page") <= 0 ?
                1 : paramObj.getInteger("page");

        Integer pageSize = paramObj.getInteger("pageSize") == null || paramObj.getInteger("pageSize") <= 0 ?
                10 : paramObj.getInteger("pageSize");


        resultVO.setData(
                JSONObject.parseObject(JSON.toJSONString(engineeringService.select(
                        engineeringCode, engineeringName, token, linkMan, enterprise, page, pageSize)))
        );

        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveEngineering")
    public String addEngineering(@RequestBody String param, HttpServletRequest request) throws ErpException {
        log.info("【添加工程】param={}", param);
        Engineering engineering = JSONObject.parseObject(param, Engineering.class);
        engineeringService.save(engineering, request.getHeader("token"));
        return JSON.toJSONString(resultVO);
    }

    /* ============================================================================================================== */
    /*                                                   施工单位                                                      */
    /* ============================================================================================================== */
    @PostMapping("/saveBuilder")
    public String saveBuilder(@RequestBody String param,
                              @RequestHeader String token,
                              @RequestHeader Integer enterprise) throws ErpException {
        log.info("【添加施工】param={}", param);
        Builder builder = JSONObject.parseObject(param, Builder.class);
        log.info("【添加施工单位】builder={}", builder);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(builderService
                .saveBuilder(builder, token, enterprise))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getBuilder")
    public String getBuilder(@RequestBody String param, HttpServletRequest request) throws ErpException {
        JSONObject paramObj = JSONObject.parseObject(param);
        Integer builderId = paramObj.getInteger("builderId");
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(builderService.getOne(builderId))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/builderDownDrop")
    public String builderDownDrop(@RequestBody String param,
                                  @RequestHeader Integer enterprise,
                                  HttpServletRequest request) throws ErpException {
        JSONObject paramsObj = JSONObject.parseObject(param);
        String name = paramsObj.getString("builderName") == null ? "" : paramsObj.getString("builderName");
        int page = paramsObj.getInteger("page") == null || paramsObj.getInteger("page") <= 0 ?
                1 : paramsObj.getInteger("page");
        int pageSize = paramsObj.getInteger("pageSize") == null || paramsObj.getInteger("pageSize") <= 0 ?
                10 : paramsObj.getInteger("pageSize");
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(
                builderService.builderDownDrop(name, enterprise, page, pageSize))));
        return JSON.toJSONString(resultVO);

    }

    @PostMapping("/builderList")
    public String builderList(@RequestBody String param,
                              @RequestHeader Integer enterprise,
                              @RequestHeader String token,
                              HttpServletRequest request) throws ErpException {
        JSONObject paramsObj = JSONObject.parseObject(param);
        log.info("【施工单位列表】paramsObj={}", paramsObj);
        String name = paramsObj.getString("builderName") == null ? "" : paramsObj.getString("builderName");
        int page = paramsObj.getInteger("page") == null || paramsObj.getInteger("page") <= 0 ?
                1 : paramsObj.getInteger("page");
        int pageSize = paramsObj.getInteger("pageSize") == null || paramsObj.getInteger("pageSize") <= 0 ?
                10 : paramsObj.getInteger("pageSize");

        log.info("【builderList】builderName={},enterprise={} token={}, page={}, pageSize={}",
                name, enterprise, token, page, pageSize);

        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(builderService
                .findBuilder(name, enterprise, page, pageSize))));

        return JSON.toJSONString(resultVO);
    }

    /* ============================================================================================================== */
    /*                                                    合 同                                                       */
    /* ============================================================================================================== */

    @PostMapping("/contractList")
    public String contractList(@RequestBody String param, HttpServletRequest request) throws ErpException {
        JSONObject paramsObj = JSONObject.parseObject(param);
        log.info("【合同列表】paramsObj={}", paramsObj);
        String builderName = paramsObj.getString("builderName");
        String engineeringName = paramsObj.getString("engineeringName");
        String contractId = paramsObj.getString("contractId");
        Integer saleUid = paramsObj.getInteger("saleUid");
        Integer contractStatus = paramsObj.getInteger("contractStatus");
        Integer del = paramsObj.getInteger("del");
        long page = paramsObj.getLong("page") == null ? 1 : paramsObj.getLong("page");
        long pageSize = paramsObj.getLong("pageSize") == null ? 10 : paramsObj.getLong("pageSize");

        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(
                contractService.list(builderName, engineeringName, contractId, saleUid,
                        contractStatus, del, page, pageSize, request.getHeader("token")))));

        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveMasterContract")
    public String saveContract(@RequestBody String param, HttpServletRequest request) throws ErpException {
        Contract Contract = JSONObject.parseObject(param, Contract.class);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(contractService.saveMasterContract(Contract,
                request.getHeader("token")))));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveContractDetails")
    public String saveContractDetails(@RequestBody String param, @RequestHeader String token,
                                      @RequestHeader Integer enterprise) throws ErpException {
        ContractDetails contractDetails = JSONObject.parseObject(param, ContractDetails.class);
        contractDetails.setEnterprise(enterprise);
        log.info("【添加子合同】contractDetails={}", contractDetails);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(contractService.saveContractDetails(contractDetails, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getContractMaster")
    public String getContractMaster(@RequestBody String param) throws ErpException {
        String cmUid = JSONObject.parseObject(param).getString("cmUid");
        if (cmUid == null || cmUid.equals("")) {
            throw new ErpException(ErrEumn.CONTRACT_CMUID_NULL);
        }

        resultVO.setData(contractService.getMasterContract(cmUid));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/getContractDetails")
    public String getContractDetails(@RequestBody String param) throws ErpException {
        String cdUid = JSONObject.parseObject(param).getString("cdUid");
        if (cdUid == null || cdUid.equals("")) {
            throw new ErpException(ErrEumn.CONTRACT_CMUID_NULL);
        }
        resultVO.setData(contractService.getContractDetails(cdUid));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/getAllContractDetails")
    public String getAllContractDetails(@RequestBody String param) throws ErpException {
        String cmUid = JSONObject.parseObject(param).getString("cmUid");
        if (cmUid == null || cmUid.equals("")) {
            throw new ErpException(ErrEumn.CONTRACT_CMUID_NULL);
        }
        resultVO.setData(contractService.getAllContractDetails(cmUid));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/gradePriceList")
    public String gradePriceList(@RequestBody String param, @RequestHeader Integer enterprise) {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(
                contractService.gradePriceList(
                        paramObj.getString("stgId") == null ? "" : paramObj.getString("stgId"),
                        enterprise,
                        paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                        paramObj.getLong("pageSize") == null ? 10 : paramObj.getLong("pageSize"))
        );
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/priceMarkupList")
    public String priceMarkupList(@RequestBody String param,
                                  @RequestHeader Integer enterprise) {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(
                contractService.priceMarkupList(
                        paramObj.getString("projectName") == null ?
                                "" : paramObj.getString("projectName"),
                        enterprise,
                        paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                        paramObj.getLong("pageSize") == null ? 10 : paramObj.getLong("pageSize"))
        );
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/pumpPriceList")
    public String pumpPriceList(@RequestBody String param,
                                @RequestHeader Integer enterprise) {
        JSONObject paramObj = JSONObject.parseObject(param);
        log.info("【泵车价格】paramObj={}, enterprise={}", paramObj, enterprise);
        resultVO.setData(
                contractService.pumpPriceList(
                        paramObj.getString("typeName"),
                        enterprise,
                        paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                        paramObj.getLong("pageSize") == null ? 10 : paramObj.getLong("pageSize"))
        );
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/salesmanList")
    public String salesmanList(@RequestBody String param,
                               @RequestHeader Integer enterprise) {
        JSONObject paramObj = JSONObject.parseObject(param);
        log.info("【销售员列表】paramObj={}, enterprise={}", paramObj, enterprise);
        resultVO.setData(
                contractService.salesmanList(
                        paramObj.getString("salesmanName"),
                        paramObj.getString("salesmanCode"),
                        enterprise,
                        paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                        paramObj.getLong("pageSize") == null ? 10 : paramObj.getLong("pageSize"))
        );
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveGradePrice")
    public String saveGradePrice(@RequestBody String param,
                                 @RequestHeader Integer enterprise,
                                 @RequestHeader String token) throws ErpException {
        GradePrice gradePrice = JSONObject.parseObject(param, GradePrice.class);
        gradePrice.setEnterpriseId(enterprise);
        resultVO.setData(contractService.saveGradePrice(gradePrice, token));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/savePriceMarkup")
    public String savePriceMarkup(@RequestBody String param,
                                  @RequestHeader Integer enterprise,
                                  @RequestHeader String token) throws ErpException {
        PriceMarkup priceMarkup = JSONObject.parseObject(param, PriceMarkup.class);
        priceMarkup.setEnterprise(enterprise);
        resultVO.setData(contractService.savePriceMarkup(priceMarkup, token));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/savePumpPrice")
    public String savePumpPrice(@RequestBody String param,
                                @RequestHeader Integer enterprise,
                                @RequestHeader String token) throws ErpException {
        PumpPrice pumpPrice = JSONObject.parseObject(param, PumpPrice.class);
        pumpPrice.setEnterprise(enterprise);
        resultVO.setData(contractService.savePumpPrice(pumpPrice, token));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveSalesman")
    public String saveSalesman(@RequestBody String param,
                               @RequestHeader Integer enterprise,
                               @RequestHeader String token) {
        Salesman salesman = JSONObject.parseObject(param, Salesman.class);
        salesman.setEnterprise(enterprise);
        resultVO.setData(contractService.saveSalesman(salesman, token));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/contractGradePriceList")
    public String contractGradePriceList(@RequestBody String param,
                                         @RequestHeader Integer enterprise,
                                         @RequestHeader String token) {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(
                        contractService.contractGradePriceList(
                                paramObj.getString("cdUid"),
                                paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                                paramObj.getLong("pageSize") == null ? 1 : paramObj.getLong("pageSize")
                        ))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/contractPriceMarkupList")
    public String contractPriceMarkupList(@RequestBody String param,
                                          @RequestHeader Integer enterprise,
                                          @RequestHeader String token) {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(
                        contractService.contractPriceMarkupList(
                                paramObj.getString("cdUid"),
                                paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                                paramObj.getLong("pageSize") == null ? 1 : paramObj.getLong("pageSize")
                        ))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/contractPumpPriceList")
    public String contractPumpPriceList(@RequestBody String param,
                                        @RequestHeader Integer enterprise,
                                        @RequestHeader String token) {
        JSONObject paramObj = JSONObject.parseObject(param);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(
                        contractService.contractPumpPriceList(
                                paramObj.getString("cdUid"),
                                paramObj.getLong("page") == null ? 1 : paramObj.getLong("page"),
                                paramObj.getLong("pageSize") == null ? 1 : paramObj.getLong("pageSize")
                        ))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveContractGradePrice")
    public String saveContractGradePrice(@RequestBody String param,
                                         @RequestHeader Integer enterprise,
                                         @RequestHeader String token) throws ErpException {
        ContractGradePrice contractGradePrice = JSONObject.parseObject(param, ContractGradePrice.class);
        contractGradePrice.setEnterprise(enterprise);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(contractService
                .saveContractGradePrice(contractGradePrice, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveContractPriceMarkup")
    public String saveContractPriceMarkup(@RequestBody String param,
                                          @RequestHeader Integer enterprise,
                                          @RequestHeader String token) {
        ContractPriceMarkup contractPriceMarkup = JSONObject.parseObject(param, ContractPriceMarkup.class);
        contractPriceMarkup.setEnterprise(enterprise);
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(contractService
                .saveContractPriceMarkup(contractPriceMarkup, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveContractPumpPrice")
    public String saveContractPumpPrice(@RequestBody String param, @RequestHeader String token,
                                        @RequestHeader Integer enterprise) {
        ContractPumpPrice contractPumpPrice = JSONObject.parseObject(param, ContractPumpPrice.class);
        contractPumpPrice.setEnterprise(enterprise);
        resultVO.setData(JSONObject.parseObject(JSONObject.toJSONString(
                contractService.saveContractPumpPrice(contractPumpPrice, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveContractGradePrices")
    public String saveContractGradePrices(@RequestBody String param,
                                          @RequestHeader Integer enterprise,
                                          @RequestHeader String token) throws ErpException {
        List<ContractGradePrice> contractGradePrices = JSONArray.parseArray(param, ContractGradePrice.class);
        contractGradePrices.forEach(contractGradePrice -> {
            contractGradePrice.setEnterprise(enterprise);
        });
        log.info("[param]param={}", param);
        contractService
                .saveContractGradePrices(contractGradePrices, token);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveContractPriceMarkups")
    public String saveContractPriceMarkups(@RequestBody String param,
                                           @RequestHeader Integer enterprise,
                                           @RequestHeader String token) {
        List<ContractPriceMarkup> contractPriceMarkups = JSONArray.parseArray(param, ContractPriceMarkup.class);
        contractPriceMarkups.forEach(contractGradePrice -> {
            contractGradePrice.setEnterprise(enterprise);
        });
        contractService
                .saveContractPriceMarkups(contractPriceMarkups, token);
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/saveContractPumpPrices")
    public String saveContractPumpPrices(@RequestBody String param,
                                         @RequestHeader String token,
                                         @RequestHeader Integer enterprise) {
        List<ContractPumpPrice> contractPumpPrices = JSONArray.parseArray(param, ContractPumpPrice.class);
        contractPumpPrices.forEach(contractPumpPrice -> {
            contractPumpPrice.setEnterprise(enterprise);
        });
        contractService.saveContractPumpPrices(contractPumpPrices, token);
        return JSON.toJSONString(resultVO);
    }

}
