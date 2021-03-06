package com.hntxrj.txerp.api;

import com.alibaba.fastjson.JSON;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.entity.TaskPlan;
import com.hntxrj.txerp.server.TaskPlanService;
import com.hntxrj.txerp.server.TaskSaleInvoiceService;
import com.hntxrj.txerp.vo.PriceMarkupVO;
import com.hntxrj.txerp.vo.ResultVO;
import com.hntxrj.txerp.vo.SquareQuantityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Api(tags = "任务单接口", description = "提供任务相关的 api")
@RestController
@RequestMapping("/api/taskPlan")
public class TaskPlanApi {

    private final TaskPlanService taskPlanService;
    private final TaskSaleInvoiceService taskSaleInvoiceService;

    @Autowired
    public TaskPlanApi(TaskPlanService taskPlanService, TaskSaleInvoiceService taskSaleInvoiceService) {
        this.taskPlanService = taskPlanService;
        this.taskSaleInvoiceService = taskSaleInvoiceService;
    }

    /**
     * 获取任务单列表
     *
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param eppCode      工程代号
     * @param builderCode  施工单位代号
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param taskStatus   任务单状态
     * @param compid       企业id
     * @param page         页码
     * @param pageSize     每页数量
     * @param verifyStatus 审核标识  0：未审核； 1：已审核
     * @return 任务单列表对象
     */
    @ApiOperation("任务单列表")
    @PostMapping("/taskPlanList")
    public ResultVO getTaskPlanList(Long beginTime, Long endTime, String eppCode,
                                    String builderCode, String placing, String taskId,
                                    Integer taskStatus, String compid, Integer verifyStatus,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.getTaskPlanList(
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, builderCode, placing, taskId, taskStatus, compid, verifyStatus, page, pageSize));
    }


    /**
     * 获取任务单预计方量汇总
     *
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param eppCode      工程代号
     * @param builderCode  施工单位代号
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param taskStatus   任务单状态
     * @param compid       企业id
     * @param verifyStatus 审核标识  0：未审核； 1：已审核
     * @return 任务单预计方量汇总
     */
    @PostMapping("/getPreNumCount")
    public ResultVO getPreNumCount(Long beginTime, Long endTime, String eppCode,
                                   String builderCode, String placing, String taskId,
                                   Integer taskStatus, String compid, Integer verifyStatus) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.getPreNumCount(
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, builderCode, placing, taskId, taskStatus, compid, verifyStatus));
    }


    /**
     * 添加任务单
     *
     * @param taskPlan 任务单实体类
     * @param type     下任务单标识  1:工地端 ; 0:手机端
     * @return 成功或者失败
     * @throws ErpException 定义的异常
     */
    @PostMapping("/addTaskPlan")
    public ResultVO addTaskPlan(TaskPlan taskPlan, String cContractCode, String type) throws ErpException {
        //兼容老版本，老版本子合同号传递的是cContractCode
        if (taskPlan.getContractDetailCode() == null) {
            taskPlan.setContractDetailCode(cContractCode);
        }
        if (type == null) {
            //兼容手机端与工地端，手机端没有传递type值，默认赋值。
            type = "0";
        }
        taskPlanService.addTaskPlan(taskPlan, type);
        return ResultVO.create();
    }


    /**
     * 添加任务单和加价项目
     *
     * @param taskId  任务单id
     * @param compid  企业id
     * @param ppCodes 加价项目编号
     */
    @PostMapping("/addTaskPriceMarkup")
    public ResultVO addTaskPriceMarkup(String compid, String taskId, String ppCodes) {

        //删除任务单加价项目
        taskPlanService.deletePPCodeStatus(compid, taskId);

        if (!"".equals(ppCodes) && ppCodes != null) {
            String[] ppCodeArray = ppCodes.split(",");

            //拼接加价项目名称
            StringBuilder ppNames = new StringBuilder();
            for (String ppCode : ppCodeArray) {
                //根据ppCode从加价从加价项目表中查询出数据
                PriceMarkupVO priceMarkupVO = taskPlanService.getPriceMarkupByPPCode(compid, ppCode);
                //插入任务单加价项目表中
                taskPlanService.addTaskPriceMarkup(compid, taskId, priceMarkupVO);
                ppNames.append(priceMarkupVO.getPPName());
            }
            taskPlanService.updateTechnicalRequirements(compid, taskId, ppNames.toString());
            return ResultVO.create();
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("未选择加价项目");
        return resultVO;

    }


    /**
     * 审核任务单
     *
     * @param taskId       任务单id
     * @param compid       企业id
     * @param verifyStatus 审核状态
     * @return 成功或者失败
     * @throws ErpException 定义的异常
     */
    @PostMapping("/verifyTaskPlan")
    public ResultVO verifyTaskPlan(String taskId, String compid, Integer verifyStatus) throws ErpException {
        taskPlanService.verifyTaskPlan(taskId, compid, verifyStatus);
        return ResultVO.create();
    }


    /**
     * 任务单详情
     *
     * @param compid 企业id
     * @param taskId 任务单id
     * @return 任务单数据
     */
    @PostMapping("/getTaskPlanDetail")
    public ResultVO getTaskPlanDetail(String compid, String taskId) {
        return ResultVO.create(taskPlanService.getTaskPlanDetail(compid, taskId));
    }

    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param id          小票id
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param upStatus    签收状态
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位g
     * @param type        搜索时间类型：0表示派车时间；1表示出厂时间
     * @param stirId      楼号
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    @PostMapping("/getTaskSaleInvoiceList")
    public ResultVO getTaskSaleInvoiceList(Integer id, String compid, Long beginTime, Long endTime, String eppCode,
                                           Byte upStatus, String builderCode, String taskId, String placing,
                                           String taskStatus, Integer type, String stirId,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskSaleInvoiceService.getTaskSaleInvoiceList(id, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                //upStatus属性老版本查询所有签收状态传的参数是-1，新版本传递的是null.兼顾新老版本，所以判断两次
                eppCode, upStatus == null ? null : (upStatus == -1 ? null : upStatus), builderCode, taskId,
                placing, taskStatus, type, stirId, page, pageSize));
    }


    /**
     * 获取小票发货，签收方量汇总
     *
     * @param id          小票id
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param upStatus    签收状态
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param taskStatus  生产状态
     * @return 小票签收列表
     */
    @PostMapping("/getTaskSaleInvoiceCount")
    public ResultVO getTaskSaleInvoiceCount(Integer id, String compid, Long beginTime, Long endTime, String eppCode,
                                            Byte upStatus, String builderCode, String taskId,
                                            String placing, String taskStatus, Integer type, String stirId) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskSaleInvoiceService.getTaskSaleInvoiceCount(id, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus, builderCode, taskId, placing, taskStatus, type, stirId));
    }

    /**
     * 获取小票详情
     *
     * @param id     小票id
     * @param compid 企业id
     * @return 小票详情
     */
    @PostMapping("/getTaskSaleInvoiceDetail")
    public ResultVO getTaskSaleInvoiceDetail(String compid, Integer id) {
        return ResultVO.create(taskSaleInvoiceService.getTaskSaleInvoiceDetail(compid, id));
    }


    /**
     * 调度派车列表
     *
     * @param compid     企业代号
     * @param searchName 搜索关键字
     * @param page       页码
     * @param pageSize   每页数量
     * @return 调度派车列表
     */
    @PostMapping("/getSendCarList")
    public ResultVO getSendCarList(String compid,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   String searchName) {
        return ResultVO.create(taskPlanService.getSendCarList(compid, searchName, page, pageSize));
    }

    /**
     * 调度派车中每个车辆的今日调度
     *
     * @param compid    企业代号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页码
     * @param pageSize  每页显示条数
     * @return 调度派车列表
     */
    @PostMapping("/getSendDetail")
    public ResultVO getSendDetail(String compid, String vehicleId, Long beginTime, Long endTime,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

        return ResultVO.create(taskPlanService.getSendDetail(compid, vehicleId,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 小票签收审核
     *
     * @param compid  　企业id
     * @param id      　　　小票id
     * @param opid    　　当前用户
     * @param qiannum 　签收数量
     * @return 成功或者失败
     * @throws ErpException 定义的异常
     */
    @PostMapping("getTaskSaleInvoiceExamine")
    public ResultVO getTaskSaleInvoiceExamine(String compid, Integer id, String opid,
                                              Double qiannum, String saleFileImage) throws ErpException {
        taskSaleInvoiceService.getTaskSaleInvoiceExamine(compid, id, opid, qiannum, saleFileImage);
        return ResultVO.create();
    }


    /**
     * 调度派车详情
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @PostMapping("/getSendCarDistance")
    public ResultVO getSendCarDistance(String compid, String taskId
    ) {
        return ResultVO.create(taskPlanService.getSendCarDistance(compid, taskId));
    }


    /**
     * 司机派车LED
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产  1 等待生产
     * @param isNewVersion  　新老版本标识： 1：新版本     null:老版本
     * @param vehicleClass  班次
     */
    @PostMapping("getDriverShiftLED")
    public ResultVO getDriverShiftLED(String compid, String stirId,
                                      Integer vehicleStatus, Integer isNewVersion,
                                      String vehicleClass) {
        if (isNewVersion == null) {
            return ResultVO.create(taskPlanService.getDriverShiftLED(compid, stirId, vehicleStatus, vehicleClass));
        }
        return ResultVO.create(taskPlanService.getDriverShiftLEDNew(compid, stirId, vehicleStatus, vehicleClass));

    }


    /**
     * 司机App的司机排班LED功能
     *
     * @param compid 企业id
     */
    @PostMapping("/getProductDriverShiftLED")
    public ResultVO getProductDriverShiftLED(String compid, Integer type) {
        //兼容老版本
        if (type == null) {
            return ResultVO.create(taskPlanService.getProductDriverShiftLEDOld(compid));
        }
        return ResultVO.create(taskPlanService.getProductDriverShiftLED(compid));
    }


    /**
     * 司机排班列表信息(老版本)
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机编号
     * @param personalName 司机名称
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("getDriverShiftList")
    public ResultVO getDriverShiftList(String compid, String vehicleId,
                                       String personalCode, String personalName, String workClass,
                                       Long beginTime, Long endTime,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.getDriverShiftList(compid, vehicleId, personalCode, personalName,
                workClass,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 司机排班列表信息(新版本)
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机编号
     * @param personalName 司机名称
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("getDriverShiftListNew")
    public ResultVO getDriverShiftListNew(String compid, String vehicleId,
                                          String personalCode, String personalName, String workClass,
                                          Long beginTime, Long endTime,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.getDriverShiftListNew(compid, vehicleId, personalCode, personalName,
                workClass,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 添加司机排班信息
     *
     * @param compid       　　　企业id
     * @param opId         　　　　　当前操作员
     * @param personalCode 　　司机代码
     * @param vehicleId    车号
     * @param workClass    班次
     * @param workStarTime 　　　　上班开始时间
     * @param workOverTime 上班结束时间
     * @param remarks      备注
     * @throws ErpException 异常处理
     */
    @PostMapping("getDriverShiftInsert")
    public ResultVO getDriverShiftInsert(String compid, String opId, String personalCode, String vehicleId,
                                         String workClass, Long workStarTime, Long workOverTime,
                                         String remarks) throws ErpException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        taskPlanService.getDriverShiftInsert(compid, opId, personalCode,
                vehicleId, workClass, workStarTime == null ? null : sdf.format(new Date(workStarTime)),
                workOverTime == null ? null : sdf.format(new Date(workOverTime)), remarks);
        return ResultVO.create();
    }


    /**
     * 修改司机排班信息
     *
     * @param id           id
     * @param compid       　　　企业ｉｄ
     * @param personalCode 　　司机代码
     * @param vehicleId    车号
     * @param workClass    班次
     * @param workStarTime 　　　　上班开始时间
     * @param workOverTime 上班结束时间
     * @param remarks      备注
     * @throws ErpException 异常
     */
    @PostMapping("getDriverShiftUpdate")
    public ResultVO getDriverShiftUpdate(Integer id, String compid, String personalCode, String vehicleId,
                                         String workClass, Long workStarTime, Long workOverTime,
                                         String remarks) throws ErpException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        taskPlanService.getDriverShiftUpdate(id, compid, personalCode,
                vehicleId, workClass, workStarTime == null ? null : sdf.format(new Date(workStarTime)),
                workOverTime == null ? null : sdf.format(new Date(workOverTime)), remarks);
        return ResultVO.create();
    }

    /**
     * 查询司机姓名
     *
     * @param compid     企业id
     * @param driverName 司机姓名
     */
    @PostMapping("/getPersonalName")
    public ResultVO getPersonalName(String compid, String driverName) {
        return ResultVO.create(taskPlanService.getPersonalName(compid, driverName));
    }


    /**
     * 调度派车正在生产
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @PostMapping("/getSendCarCountNum")
    public ResultVO getSendCarCountNum(String compid,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(taskPlanService.getSendCarCountNum(compid, page, pageSize));
    }

    /**
     * 调度派车今日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @PostMapping("/getSendCarTodayNum")
    public ResultVO getSendCarTodayNum(String compid,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(taskPlanService.getSendCarTodayNum(compid, page, pageSize));
    }

    /**
     * 调度派车昨日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @PostMapping("/getSendCarYesterDayNum")
    public ResultVO getSendCarYesterDayNum(String compid,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize, Long time)
            throws ParseException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd hh:mm:ss");
        return ResultVO.create(taskPlanService.getSendCarYesterDayNum(compid, page, pageSize,
                time == null ? null : sdf.format(new Date(time))));
    }

    /**
     * 调度派车本月方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @PostMapping("/getMonthTotalNum")
    public ResultVO getMonthTotalNum(String compid,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     Long monthStart, Long monthEnd) throws ParseException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd hh:mm:ss");
        return ResultVO.create(taskPlanService.getMonthTotalNum(compid, page, pageSize,
                monthStart == null ? null : sdf.format(new Date(monthStart)),
                monthEnd == null ? null : sdf.format(new Date(monthEnd))));
    }


    /**
     * 任务单生产配比日志查询
     *
     * @param compid      企业代码
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param formulaCode 配比编号
     * @param stgId       砼标号
     * @param stirId      搅拌楼
     * @param eppCode     工程代码
     * @param placing     浇筑部位
     * @param taskId      任务单号
     * @param page        分页
     * @param pageSize    每页显示条数
     * @return 任务单生产配比日志查询
     */
    @PostMapping("/getProductionRatio")
    public ResultVO getProductionRatio(String compid,
                                       Long beginTime, Long endTime,
                                       String formulaCode, String stgId, String stirId, String eppCode, String placing,
                                       String taskId,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd hh:mm:ss");
        return ResultVO.create(taskPlanService.getProductionRatio(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                formulaCode, stgId, stirId, eppCode, placing, taskId,
                page, pageSize
        ));
    }


    /**
     * 任务单理论配比日志查询
     *
     * @param compid      企业代码
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param formulaCode 配比编号
     * @param stgId       砼标号
     * @param stirId      搅拌楼
     * @param eppCode     工程代码
     * @param placing     浇筑部位
     * @param taskId      任务单号
     * @param page        分页
     * @param pageSize    每页显示条数
     * @return 任务单理论配比日志查询
     */
    @PostMapping("/getTheoreticalproportioning")
    public ResultVO getTheoreticalproportioning(String compid,
                                                Long beginTime, Long endTime,
                                                String formulaCode, String stgId, String stirId, String eppCode,
                                                String placing, String taskId,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd hh:mm:ss");
        return ResultVO.create(taskPlanService.getTheoreticalproportioning(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                formulaCode, stgId, stirId, eppCode, placing,
                taskId,
                page, pageSize
        ));
    }

    /**
     * 获取方量统计
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     * @param type      1 今日，2，昨日，3本月
     */
    @PostMapping("/getSquareQuantitySum")
    public ResultVO getConcreteSum(String compid,
                                   String beginTime, String endTime, int type) {
        SquareQuantityVO squareQuantityVO = taskPlanService.getSquareQuantitySum(compid, beginTime,
                endTime, type);
        return ResultVO.create(squareQuantityVO);
    }

    /**
     * 获取今日预计方量
     *
     * @param compid    企业
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    @RequestMapping("/phoneStatistics")
    public String phoneStatistics(String compid, String beginTime, String endTime) throws SQLException {
        return JSON.toJSONString(taskPlanService.phoneStatistics(compid, beginTime, endTime));
    }

    @RequestMapping("/todayTask")
    public String todayTask(String compid, String beginTime, String endTime) throws SQLException {
        return JSON.toJSONString(taskPlanService.todayTask(compid, beginTime, endTime));
    }


    /**
     * 生成任务单Id
     *
     * @param compid 企业id
     */
    @PostMapping("/makeAutoTaskPlanId")
    public ResultVO makeAutoTaskPlanId(String compid) throws SQLException {
        return ResultVO.create(taskPlanService.makeAutoTaskPlanId(compid));
    }


    /**
     * 校验用户输入的任务单号是否存在
     *
     * @param compid 企业
     * @param taskId 任务单号
     */
    @PostMapping("/isExistence")
    public ResultVO isExistence(String compid, String taskId) {
        boolean existence = taskPlanService.isExistence(compid, taskId);
        return ResultVO.create(existence);
    }

    /**
     * 获取特殊加价项目列表
     *
     * @param compid 企业
     */
    @PostMapping("/getPriceMarkup")
    public ResultVO getPriceMarkup(String compid) {
        List<PriceMarkupVO> priceMarkupVOs = taskPlanService.getPriceMarkup(compid);
        return ResultVO.create(priceMarkupVOs);
    }

    /**
     * 调度派车中查询正在生产的搅拌车
     *
     * @param compid 企业
     */

    @PostMapping("/getProduceCars")
    public ResultVO getProduceCars(String compid) {
        return ResultVO.create(taskPlanService.getProduceCars(compid));
    }


    /**
     * 获取工地端任务单列表
     *
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param eppCode      工程代号
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param taskStatus   任务单状态
     * @param compid       企业id
     * @param page         页码
     * @param pageSize     每页数量
     * @param verifyStatus 审核标识  0：未审核； 1：已审核
     * @param buildId      施工方id
     * @return 任务单列表对象
     */
    @PostMapping("/buildTaskPlanList")
    public ResultVO buildTaskPlanList(Long beginTime, Long endTime, String eppCode,
                                      String placing, String taskId,
                                      Integer taskStatus, String compid, Integer verifyStatus, Integer buildId,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.buildTaskPlanList(
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, placing, taskId, taskStatus, compid, verifyStatus, buildId, page, pageSize));
    }


    /**
     * 获取工地端任务单预计方量汇总
     *
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param eppCode      工程代号
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param taskStatus   任务单状态
     * @param verifyStatus 审核标识  0：未审核； 1：已审核
     * @param buildId      施工方id
     * @return 任务单列表预计方量汇总
     */
    @PostMapping("/getBuildTaskPreCount")
    public ResultVO getBuildTaskPreCount(Long beginTime, Long endTime, String eppCode, String placing,
                                         String taskId, Integer taskStatus, Integer verifyStatus,
                                         Integer buildId) throws ErpException {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(taskPlanService.getBuildTaskPreCount(
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, placing, taskId, taskStatus, verifyStatus, buildId));
    }

    /**
     * 获取塌落度下拉
     *
     * @param compid   企业id
     * @param slump    塌落度
     * @param page     页码
     * @param pageSize 每页条数
     */
    @PostMapping("getSlumpDropDown")
    public ResultVO getSlumpDropDown(String compid, String slump,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(taskPlanService.getSlumpDropDown(compid, slump, page, pageSize));
    }


    /**
     * 删除任务单
     *
     * @param compid 企业id
     * @param taskId 任务单号
     */
    @PostMapping("deleteTaskPlan")
    public ResultVO deleteTaskPlan(String compid, String taskId) throws ErpException {
        taskPlanService.deleteTaskPlan(compid, taskId);
        return ResultVO.create();
    }
}
