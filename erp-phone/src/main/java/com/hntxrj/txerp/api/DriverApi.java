package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.DriverService;
import com.hntxrj.txerp.server.TaskSaleInvoiceService;
import com.hntxrj.txerp.vo.ResultVO;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
     * @param id          小票id
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param driverCode  司机代号
     * @param placing     浇筑部位
     * @param upStatus    签收状态
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
        return ResultVO.create(driverService.getTaskSaleInvoiceList(id, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus == -1 ? null : upStatus, builderCode, placing, page, pageSize, driverCode));
    }

    /**
     * 修改小票中的车辆状态
     *
     * @param compid        企业
     * @param id            小票id
     * @param vehicleStatus 车辆状态   13：正在卸料； 14：卸料完毕； 1：场内待班
     * @return 小票签收列表
     */
    @PostMapping("/updateVehicleStatus")
    public ResultVO updateVehicleStatus(String compid, Integer id, Integer vehicleStatus) {
        driverService.updateVehicleStatus(compid, id, vehicleStatus);
        return ResultVO.create();
    }


    /**
     * 获取小票详情
     *
     * @param compid 企业
     * @param id     小票id
     * @return 小票签收列表
     */
    @PostMapping("/getTaskSaleInvoiceDetail")
    public ResultVO getTaskSaleInvoiceDetail(String compid, Integer id) {
        TaskSaleInvoiceDetailVO taskSaleInvoiceDetail = driverService.getTaskSaleInvoiceDetail(compid, id);
        return ResultVO.create(driverService.getTaskSaleInvoiceDetail(compid, id));
    }


    /**
     * 获取司机列表
     *
     * @param compid 企业
     * @return 司机列表
     */
    @PostMapping("/getDriverList")
    public ResultVO getDriverList(String compid, String token) {
        return ResultVO.create(driverService.getDriverList(compid, token, null));
    }


    /**
     * 获取任务单泵车列表
     *
     * @param compid 企业
     * @param taskId 任务单号
     * @return 泵车列表
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

    /**
     * 保存小票签收签收图片（老版本）
     *
     * @param image     图片
     * @param invoiceId 小票id
     */
    @RequestMapping("/uploadTaskSaleInvoiceReceiptSign")
    public ResultVO uploadTaskSaleInvoiceReceiptSign(MultipartFile image, String invoiceId) throws IOException {
        File file = new File(taskSaleInvoiceUploadPath + invoiceId + ".png");
        if (file.exists()) {
            file.delete();
        }
        if (file.createNewFile()) {
            IOUtils.copy(image.getInputStream(), new FileOutputStream(file));
        }
        Map<String, String> out = new HashMap<>();
        out.put("fileName", invoiceId + ".png");
        return ResultVO.create(out);
    }


    /**
     * 保存小票签收签收图片并保存到数据库(新版本)
     *
     * @param image     图片
     * @param invoiceId 小票id
     * @param compid    企业
     */
    @RequestMapping("/saveTaskSaleInvoiceReceiptSign")
    public ResultVO saveTaskSaleInvoiceReceiptSign(MultipartFile image, Double receiptNum, String invoiceId, String compid) throws IOException {
        File file = new File(taskSaleInvoiceUploadPath + invoiceId + ".png");
        if (file.exists()) {
            System.gc();
            file.delete();

        }
        if (file.createNewFile()) {
            IOUtils.copy(image.getInputStream(), new FileOutputStream(file));
        }
        String saleFileImage = invoiceId + ".png";
        try {
            driverService.saveSaleFileImage(saleFileImage, invoiceId, compid, receiptNum == null ? 0.0 : receiptNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVO.create();
    }


    /**
     * 编辑签收方量
     *
     * @param compid     公司代号
     * @param receiptNum 签收方量
     * @param invoiceId  小票id
     */
    @RequestMapping("/saveNumberOfSignings")
    public ResultVO saveNumberOfSignings(String compid, Double receiptNum, String invoiceId) throws IOException {
        if (receiptNum == null) {
            receiptNum = 0.0;
        }
        driverService.saveNumberOfSignings(compid, receiptNum, invoiceId);
        return ResultVO.create();
    }


    /**
     * 获取签收人签名图片
     *
     * @param fileName 图片名
     */
    @RequestMapping("/getTaskSaleInvoiceReceiptSign")
    public void getTaskSaleInvoiceReceiptSign(String fileName, HttpServletResponse response) throws IOException {
        driverService.getTaskSaleInvoiceReceiptSign(taskSaleInvoiceUploadPath, fileName, response);
    }

    /**
     * 获取司机姓名
     *
     * @param compid     企业代号
     * @param driverCode 司机代号
     */
    @PostMapping("/getDriverName")
    public ResultVO getDriverName(String compid, String driverCode) {
        return ResultVO.create(driverService.getDriverName(compid, driverCode));
    }


    /**
     * 根据司机编号集合查询出司机名称集合
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


    /**
     * 获取小票签收汇总
     *
     * @param id          小票id
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param driverCode  司机代号
     * @param placing     浇筑部位
     * @param upStatus    签收状态
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    @PostMapping("/getTaskSaleInvoiceSum")
    public ResultVO getTaskSaleInvoiceSum(Integer id, String compid, Long beginTime, Long endTime, String eppCode,
                                          @RequestParam(defaultValue = "-1") Byte upStatus,
                                          String builderCode, String placing,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize, String driverCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(driverService.getTaskSaleInvoiceSum(id, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus == -1 ? null : upStatus, builderCode, placing, page, pageSize, driverCode));
    }


    /**
     * 保存司机打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param time       打卡时间
     * @param timeType   打卡类型  0:上班打卡    1：下班打卡
     */
    @PostMapping("/saveDriverWorkTime")
    public ResultVO saveDriverWorkTime(String compid, String driverCode, Long time, Integer timeType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        driverService.saveDriverWorkTime(compid, driverCode, sdf.format(new Date(time)), timeType);
        return ResultVO.create();
    }

    /**
     * 查询司机当天打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param dateTime   查询日期
     */
    @PostMapping("/getDriverWorkTime")
    public ResultVO getDriverWorkTime(String compid, String driverCode, Long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String queryTime = sdf.format(new Date());
        if (dateTime != null) {
            queryTime = sdf.format(new Date(dateTime));
        }
        return ResultVO.create(driverService.getDriverWorkTime(compid, driverCode, queryTime));
    }
}
