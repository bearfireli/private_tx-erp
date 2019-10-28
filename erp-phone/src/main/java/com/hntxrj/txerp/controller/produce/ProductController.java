package com.hntxrj.txerp.controller.produce;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.ProductService;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:  生产模块控制层
 *
 * @Auther 李帅
 * @Data 2017-09-04.下午 6:49
 */
@RestController
@RequestMapping("/produce")
@Scope("prototype")
@EnableAsync
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 生产查询统计
     *
     * @param mark       -- 1 查询统计
     * @param compid     企业ID
     * @param begintime  起始时间
     * @param endtime    结束时间
     * @param taskId     任务单ID
     * @param taskStatus 任务单状态
     * @return
     */
    @RequestMapping("/productTotal")
    public JsonVo productTotal(Integer mark, String compid, String begintime, String endtime, String taskId,
                               String taskStatus, @RequestParam(defaultValue = "0") Integer currPage, String opid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        if (mark < 1) {
            vo.setCode(1);
            vo.setMsg("error,mark is null");
        } else if (begintime == null || "".equals(begintime)) {
            vo.setCode(1);
            vo.setMsg("error,begintime is null");
        } else if (endtime == null || "".equals(endtime)) {
            vo.setCode(1);
            vo.setMsg("error,endtime is null");
        } else {
            vo.setCode(0);
            vo.setMsg("ok");
            PageBean pageBean = new PageBean(10, currPage);
            taskId = taskId == null || "".equals(taskId) ? null : taskId;
            taskStatus = taskStatus == null || "".equals(taskStatus) ? null : taskStatus;
            vo.setData(productService.productTotal(mark, compid, begintime, endtime,
                    taskId, taskStatus, pageBean, opid));
            vo.setPageBean(pageBean);
        }
        return vo;
    }


    /**
     * 销售方量统计
     *
     * @param mark        标记
     * @param beginTime   起始时间
     * @param endTime     结束时间
     * @param builderName 施工单位名称
     * @param eppName     工程名称
     * @param currPage    分页
     * @return
     */
    @RequestMapping("/builderSalaTotal")
    public JsonVo builderSalaTotal(Integer mark, String beginTime, String endTime, String builderName,
                                   String eppName, String compid, @RequestParam(defaultValue = "0") Integer currPage, String opid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        //处理  参数为mull
        if (mark < 1) {
            vo.setCode(1);
            vo.setMsg("error,mark is null");
        } else if (beginTime == null || "".equals(beginTime)) {
            vo.setCode(1);
            vo.setMsg("error,begintime is null");
        } else if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else if (endTime == null || "".equals(endTime)) {
            vo.setCode(1);
            vo.setMsg("error,endtime is null");
        } else {
            //正确传参
            vo.setCode(0);
            vo.setMsg("ok");
            PageBean pageBean = new PageBean(10, currPage);
            builderName = builderName == null || "".equals(builderName) ? null : builderName;
            eppName = eppName == null || "".equals(eppName) ? null : eppName;
            JSONArray result = productService.builderSalaTotal(mark,
                    beginTime, endTime, builderName,
                    eppName, compid, pageBean, opid);
            vo.setData(result);
            vo.setPageBean(pageBean);
        }
        return vo;
    }

    /**
     * 小票签收查询
     *
     * @param mark        标记 1加载列表  2详情
     * @param compid      企业ID
     * @param id          id
     * @param begintime   起始时间
     * @param endtime     结束时间
     * @param vehicleID   车号
     * @param taskId      任务单号
     * @param InvoiceType 小票类型
     * @param placeStyle  浇筑方式
     * @param placing     浇筑部位
     * @param builderName 施工单位
     * @param stgId       搅拌楼
     * @param eppName     工程名称
     * @param currPage    分页
     * @return
     */
    @RequestMapping("/deliveryTicketTotal")
    public JsonVo deliveryTicketTotal(Integer mark, String compid, Integer id, String begintime, String endtime, String vehicleID,
                                      String taskId, String InvoiceType, String placeStyle, String placing, String builderName,
                                      String stgId, String eppName, @RequestParam(defaultValue = "0") Integer currPage, String opid) {
        JsonVoAndPage vo = new JsonVoAndPage();

        /* 参数处理 */
        vehicleID = vehicleID == null || "".equals(vehicleID) ? null : vehicleID;
        taskId = taskId == null || "".equals(taskId) ? null : taskId;
        InvoiceType = InvoiceType == null || "".equals(InvoiceType) ? null : InvoiceType;
        placeStyle = placeStyle == null || "".equals(placeStyle) ? null : placeStyle;
        placing = placing == null || "".equals(placing) ? null : placing;
        builderName = builderName == null || "".equals(builderName) ? null : builderName;
        eppName = eppName == null || "".equals(eppName) ? null : eppName;
        stgId = stgId == null || "".equals(stgId) ? null : stgId;
        PageBean pageBean = new PageBean(10, currPage);

        if (mark < 1) {
            vo.setCode(1);
            vo.setMsg("error,mark is null");
        } else if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {
            if (mark == 1) {
                if (begintime == null || "".equals(begintime)) {
                    vo.setCode(1);
                    vo.setMsg("error,begintime is null");
                } else if (endtime == null || "".equals(endtime)) {
                    vo.setCode(1);
                    vo.setMsg("error,endtime is null");
                } else {
                    vo.setCode(0);
                    vo.setMsg("ok");

                    vo.setData(productService.deliveryTicketTotal(mark, compid, id, begintime, endtime, vehicleID, taskId,
                            InvoiceType, placeStyle, placing, builderName, stgId, eppName, pageBean, opid));
                    if (mark == 1) {
                        vo.setPageBean(pageBean);
                    }
                }
            } else {
                vo.setCode(0);
                vo.setMsg("ok");
                JSONArray jsonArray = productService.deliveryTicketTotal(mark, compid, id, begintime, endtime, vehicleID, taskId,
                        InvoiceType, placeStyle, placing, builderName, stgId, eppName, pageBean, opid);
                vo.setData(jsonArray);
            }


            //设置分页

        }


        return vo;
    }


    /**
     * 调度派车
     *
     * @param mark        1加载列表
     * @param compid      企业ID
     * @param begintime   起始时间
     * @param endtime     结束时间
     * @param taskId      任务单号
     * @param taskStatus  任务状态
     * @param placing     浇筑部位
     * @param builderName 施工单位
     * @param eppName     工程名称
     * @param currPage    当前页码
     * @return
     */
    @RequestMapping("/vehicleCarTotal")
    public JsonVo vehicleCarTotal(Integer mark, String compid, String begintime, String endtime, String taskId, Integer taskStatus,
                                  String placing, String builderName, String eppName, @RequestParam(defaultValue = "0") Integer currPage) {

        JsonVoAndPage vo = new JsonVoAndPage();
        if (mark < 1) {
            vo.setCode(1);
            vo.setMsg("error,mark is null");
        } else if (begintime == null || "".equals(begintime)) {
            vo.setCode(1);
            vo.setMsg("error,begintime is null");
        } else if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else if (endtime == null || "".equals(endtime)) {
            vo.setCode(1);
            vo.setMsg("error,endtime is null");
        } else {
            taskId = taskId == null || "".equals(taskId) ? null : taskId;
            placing = placing == null || "".equals(placing) ? null : placing;
            builderName = builderName == null || "".equals(builderName) ? null : builderName;
            eppName = eppName == null || "".equals(eppName) ? null : eppName;
            PageBean pageBean = new PageBean(10, currPage);
            vo.setCode(0);
            vo.setMsg("ok");
            vo.setData(productService.vehicleCarTotal(mark,
                    compid, begintime, endtime,
                    taskId, taskStatus, placing,
                    builderName, eppName, pageBean));
            vo.setPageBean(pageBean);
        }
        return vo;
    }


    /**
     * 调度管理修改
     * <p>
     * version 1.1
     *
     * @param mark     标记  1 加载列表 2 详情
     * @param compid   企业ID
     * @param taskID   任务单号
     * @param currPage 分页
     * @return
     */
    @RequestMapping("/vehicleCarTotal_Two")
    public JsonVo vehicleCarTotal_Two(Integer mark, String compid,
                                      String taskID,
                                      @RequestParam(defaultValue = "0") Integer currPage,
                                      String opid) {

        JsonVoAndPage vo = new JsonVoAndPage();

        if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {

            vo.setCode(0);
            PageBean pageBean = new PageBean(10, currPage);
            if (mark == 1) {
                vo.setData(productService.vehicleCarTotal_Two(mark,
                        compid, taskID, pageBean, opid));
                vo.setPageBean(pageBean);
            } else if (mark == 2) {
                if (taskID == null || "".equals(taskID)) {
                    vo.setCode(1);
                    vo.setMsg("error,taskID is null");
                } else {
                    vo.setData(productService.vehicleCarTotal_Two(mark,
                            compid, taskID, pageBean, opid));
                }
            }
        }
        return vo;
    }
}
