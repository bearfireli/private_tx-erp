package com.hntxrj.api;

import com.hntxrj.server.DriverService;
import com.hntxrj.server.TaskSaleInvoiceService;
import com.hntxrj.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 司机端api
 */
@RestController
@RequestMapping("/driver")
@Slf4j
public class DriverApi {

    private final TaskSaleInvoiceService taskSaleInvoiceService;
    private final DriverService driverService;

    @Value("${app.spterp.taskSaleInvoiceUploadPath}")
    private String taskSaleInvoiceUploadPath;

    @Autowired
    public DriverApi(TaskSaleInvoiceService taskSaleInvoiceService, DriverService driverService) {
        this.taskSaleInvoiceService = taskSaleInvoiceService;
        this.driverService = driverService;
    }

    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param driverCode  司机代号
     * @param placing     浇筑部位
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    @PostMapping("/getTaskSaleInvoiceList")
    public ResultVO getTaskSaleInvoiceList(Integer id, String compid, Long beginTime, Long endTime, String eppCode,
                                           @RequestParam(defaultValue = "-1") Byte upStatus,
                                           String builderCode, String placing,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize, String driverCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskSaleInvoiceService.getTaskSaleInvoiceList(id, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus == -1 ? null : upStatus, builderCode, placing, page, pageSize, driverCode));
    }


    @PostMapping("/getDriverList")
    public ResultVO getDriverList(String compid, String token) {
        return ResultVO.create(driverService.getDriverList(compid, token, null));
    }


    /**
     * 获取任务单泵车列表
     * 此接口用来删除泵车deletemark
     *
     * @return
     */
    @PostMapping("/getJumpVehicleList")
    public ResultVO getJumpVehicleList(String compid, String token, String taskId) {
        return ResultVO.create(driverService.getJumpVehicleList(compid, token, taskId));
    }


    /**
     * 小票签收
     *
     * @param receiptPeople 签收人
     * @param receiptNum    签收数量
     * @param jumpVehicle   泵车
     * @param sign          签字图片
     * @param invoiceId     小票代号
     * @return 结果
     */
    @PostMapping("/taskSaleInvoiceReceipt")
    public ResultVO taskSaleInvoiceReceipt(String receiptPeople,
                                           Double receiptNum, String jumpVehicle, String sign, String invoiceId) {
        log.info("receiptPeople:{}, receiptNum:{}, jumpVehicle:{}, sign:{}, invoiceId:{}",
                receiptPeople, receiptNum, jumpVehicle, sign, invoiceId);
        driverService.taskSaleInvoiceReceipt(receiptPeople, receiptNum, jumpVehicle, sign, invoiceId);
        return ResultVO.create();
    }


    @RequestMapping("/uploadTaskSaleInvoiceReceiptSign")
    public ResultVO uploadTaskSaleInvoiceReceiptSign(MultipartFile image, String invoiceId) throws IOException {
        File file = new File(taskSaleInvoiceUploadPath + invoiceId + ".png");
        if (file.exists()) {
            assert file.delete();
        }
        if (file.createNewFile()) {
            IOUtils.copy(image.getInputStream(), new FileOutputStream(file));
        }
        Map<String, String> out = new HashMap<>();
        out.put("fileName", invoiceId + ".png");
        return ResultVO.create(out);
    }

    @PostMapping("/getDriverName")
    public ResultVO getDriverName(String compid, String driverCode) {
        return ResultVO.create(driverService.getDriverName(compid, driverCode));
    }


    /*
    根据司机编号集合查询出司机名称集合
    */
    @RequestMapping("/getDriverNames")
    public ResultVO getDriverNames(String compid, String driverCodes) {

        Map<String, String> map = new HashMap<>();
        String[] driverCodeList = driverCodes.split(",");
        for (String driverCode : driverCodeList) {
            String driverName = driverService.getDriverNames(compid, driverCode);
            map.put(driverCode, driverName);
        }

        return ResultVO.create((map));
    }

}
