package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.service.EnterpriseAfterSaleService;
import com.hntxrj.txerp.service.EnterpriseService;
import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.core.exception.ErpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/enterprise")
@Slf4j
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseAfterSaleService enterpriseAfterSaleService;
    private ResultVO resultVO;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService, EnterpriseAfterSaleService enterpriseAfterSaleService) {
        this.enterpriseAfterSaleService = enterpriseAfterSaleService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
        resultVO.setData(null);
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("/delete")
    public String updateEnterprise(Integer enterpriseId) throws ErpException {
        log.info("【删除企业】enterpriseId={}", enterpriseId);
        if (enterpriseId == 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST);
        }
        enterpriseService.deleteEnterprise(enterpriseId);
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/getEnterprise")
    public String getEnterprise(Integer eid) throws ErpException {
        resultVO.setData(JSON.parseObject(JSON.toJSONString(enterpriseService.getEnterprise(eid))));
        return JSON.toJSONString(resultVO);
    }


    @PostMapping("/getEnterpriseDropDown")
    public String getEnterpriseDropDown(String token, String keyword) throws ErpException {
        log.info("【获取企业下拉】keyword={}", keyword);

        resultVO.setData(JSON.toJSONString(enterpriseService.getDropDown(token, keyword)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/selectEnterprise")
    public String selectEnterprise(String token, String epName, Integer epType, Integer epStatus,
                                   @RequestParam(defaultValue = "0") Integer delete,
                                   @RequestParam(defaultValue = "1") long page,
                                   @RequestParam(defaultValue = "10") long pageSize,
                                   HttpServletRequest request) throws ErpException {
        log.info("【查询企业】epName={},epType={},epStatus={}", epName, epType, epStatus);

        resultVO.setData(JSON.toJSONString(
                enterpriseService.selectEnterprise(token, epName,
                        epType, epStatus, delete,
                        page, pageSize)));

        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/addEnterprise")
    public String addEnterprise(@RequestHeader String token, Enterprise enterprise) throws ErpException {
        log.info("【添加企业】enterprise={}", enterprise);
        if (enterprise == null) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_NULL);
        }
//        enterprise.setEid(null);
        if (StringUtils.isEmpty(enterprise.getEpShortName())) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_SHORT_NAME_NULL);
        }
        if (StringUtils.isEmpty(enterprise.getEpName())) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_NAME_NULL);
        }
        if (enterprise.getEpShortName() == null) {
            enterprise.setEpStatus(0);
        }

        resultVO.setData(JSON.toJSONString(enterpriseService.addEnterprise(token, enterprise)));
        return JSON.toJSONString(resultVO);

    }

    @PostMapping("/updateEnterprise")
    public String updateEnterprise(Enterprise enterprise, Integer eidCode) throws ErpException {
        log.info("【修改企业】enterprise={}", enterprise);
        if (enterprise == null
                || enterprise.getEid() == null
                || enterprise.getEid() == 0) {
            throw new ErpException(ErrEumn.ADD_ENTERPRISE_NULL);
        }
        resultVO.setData(JSON.toJSONString(enterpriseService.updateEnterprise(enterprise, eidCode)));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/afterSaleList")
    public String afterSaleList(@RequestHeader String token, @RequestHeader Integer enterprise,
                                long page, long pageSize) throws ErpException {
        resultVO.setData(JSONObject.parseObject(JSON.toJSONString(
                enterpriseAfterSaleService.list(token, enterprise, page, pageSize))));
        return JSON.toJSONString(resultVO);
    }


    //支付
    @PostMapping("/setCollectionCode")
    public ResultVO setCollectionCode(Integer eid, MultipartFile imageFile,
                                      Integer type) throws ErpException {
        resultVO.setData(enterpriseService.setCollectionCode(eid, imageFile, type));
        return resultVO;
    }

    //读取地址
    @GetMapping("/getCollectionCode")
    public void getCollectionCode(@RequestHeader Integer enterprise, Integer type,
                                  HttpServletResponse response) throws ErpException {
        enterpriseService.getCollectionCode(enterprise, type, response);
    }

    //上传收款码
    @PostMapping("/uploadPicture")
    public ResultVO uploadPicture(MultipartFile image) throws ErpException {
        return ResultVO.create(enterpriseService.uploadFeedbackImg(image));
    }

    //读取图片
    @GetMapping("/getFeedboackPicture")
    public void getFeedbackPicture(Integer eid, Integer type, HttpServletResponse response) throws ErpException {
        enterpriseService.getFeedbackImg(eid, type, response);
    }

    //上传成功回显图片
    @GetMapping("/getimage")
    public void getimage(String fileName, HttpServletResponse response) throws ErpException {
        enterpriseService.getimage(fileName, response);
    }

    //保存收款码
    @PostMapping("/saveCollectionCode")
    public ResultVO saveCollectionCode(Integer eid, String imageFile,
                                       Integer type) throws ErpException {
        resultVO.setData(enterpriseService.saveCollectionCode(eid, imageFile, type));
        return resultVO;
    }
}
