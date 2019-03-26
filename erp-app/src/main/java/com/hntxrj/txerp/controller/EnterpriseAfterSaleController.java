package com.hntxrj.txerp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.service.EnterpriseAfterSaleLogService;
import com.hntxrj.txerp.service.EnterpriseAfterSaleService;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.entity.base.EnterpriseAfterSale;
import com.hntxrj.txerp.entity.base.EnterpriseAfterSaleLog;
import com.hntxrj.txerp.entity.base.ProjectLifespan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/afterSale")
@Slf4j
public class EnterpriseAfterSaleController {

    private final EnterpriseAfterSaleService enterpriseAfterSaleService;
    private final EnterpriseAfterSaleLogService enterpriseAfterSaleLogService;
    private ResultVO resultVO;

    @Value("${app.afterSaleLog.upload.path}")
    private String uploadPath;

    @Autowired
    public EnterpriseAfterSaleController(EnterpriseAfterSaleService enterpriseAfterSaleService,
                                         EnterpriseAfterSaleLogService enterpriseAfterSaleLogService) {
        this.enterpriseAfterSaleService = enterpriseAfterSaleService;
        this.enterpriseAfterSaleLogService = enterpriseAfterSaleLogService;
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
    }


    @PostMapping("/list")
    public String list(@RequestHeader String token, Integer enterprise,
                       long page, long pageSize) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleService.list(token, enterprise, page, pageSize))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/save")
    public String save(EnterpriseAfterSale enterpriseAfterSale,
                       @RequestHeader String token,
                       ProjectLifespan projectLifespan) throws ErpException {
        log.info("【添加企业售后信息】enterpriseAfterSale={}, projectLifespan={}",
                enterpriseAfterSale, projectLifespan);
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleService.save(enterpriseAfterSale,
                        token, projectLifespan))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/saveLog")
    public String saveLog(EnterpriseAfterSaleLog enterpriseAfterSaleLog, String token) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleLogService
                        .saveEnterpriseAfterSaleLog(enterpriseAfterSaleLog, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/logInfo")
    public String logInfo(Integer logId, @RequestHeader String token) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleLogService
                        .enterpriseAfterSaleLogInfo(logId, token))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/logList")
    public String logList(Integer enterprise, long page, long pageSize) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleLogService
                        .enterpriseAfterSaleLogList(enterprise, page, pageSize))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/overAfterSale")
    public String overAfterSale(@RequestHeader String token, String processingType,
                                String sumup, Integer logId) throws ErpException {
        resultVO.setData(JSONObject.parseObject(
                JSON.toJSONString(enterpriseAfterSaleLogService
                        .overAfterSale(token, processingType, sumup, logId))));
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/acceptTask")
    public String acceptTask(@RequestHeader String token, Integer logId) throws ErpException {
        enterpriseAfterSaleLogService.acceptTask(token, logId);
        return JSON.toJSONString(resultVO);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("files") MultipartFile[] files, Integer logId) throws ErpException {
        log.info("【文件上传路径】path={}", uploadPath);
        JSONArray fs = new JSONArray();
        for (MultipartFile file : files) {
            String[] fileNameSplits = file.getOriginalFilename().split("\\.");
            String reName = UUID.randomUUID().toString() +
                    (fileNameSplits.length != 0 ? "." + fileNameSplits[fileNameSplits.length - 1] : "");
            String filePath = uploadPath + reName;
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(filePath)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("【上传文件失败】errorMsg={}", e.getMessage());
                throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
            }
            JSONObject fileObj = new JSONObject();
            fileObj.put("fileName", file.getOriginalFilename());
            fileObj.put("filePath", reName);
            fs.add(fileObj);
        }
        enterpriseAfterSaleLogService.uploadFile(fs, logId);
        return JSON.toJSONString(resultVO);

    }

    @GetMapping("/file")
    public void download(String filePath, String fileName, HttpServletResponse response) throws ErpException {
        File file = new File(uploadPath + filePath);
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");
        try {
            OutputStream os = response.getOutputStream();
            byte[] bis = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (-1 != inputStream.read(bis)) {
                os.write(bis);
            }
        } catch (Exception e) {
            throw new ErpException(ErrEumn.DOWNLOAD_FILE_ERROR);
        }
    }


}
