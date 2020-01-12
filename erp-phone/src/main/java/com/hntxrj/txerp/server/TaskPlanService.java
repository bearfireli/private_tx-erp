package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.TaskPlan;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务单服务
 */
public interface TaskPlanService {

    /**
     * 获取任务单列表
     *
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param placing     浇筑部位
     * @param taskId      任务单号
     * @param taskStatus  任务单状态
     * @param compid      企业id
     * @param page        页码
     * @param pageSize    每页数量
     * @return 任务单列表对象
     */
    PageVO<TaskPlanListVO> getTaskPlanList(String beginTime, String endTime, String eppCode,
                                           String builderCode, String placing, String taskId,
                                           Integer taskStatus, String compid, Integer verifyStatus,
                                           Integer page, Integer pageSize);


    /**
     * 任务单详情
     *
     * @param compid 企业id
     * @param taskId 任务单id
     * @return 任务单数据
     */
    TaskPlanVO getTaskPlanDetail(String compid, String taskId);


    /**
     * 添加任务单
     *
     * @param taskPlan 任务单实体类
     * @return 成功或者失败
     * @throws ErpException 定义的异常
     */
    void addTaskPlan(TaskPlan taskPlan) throws ErpException;


    /**
     * 审核任务单
     *
     * @param taskId       任务单id
     * @param compid       企业id
     * @param verifyStatus 审核状态
     * @return 成功或者失败
     * @throws ErpException 定义的异常
     */
    void verifyTaskPlan(String taskId, String compid, Integer verifyStatus) throws ErpException;


    /**
     * 调度派车列表
     *
     * @param compid     企业代号
     * @param searchName 搜索关键字
     * @return 调度派车列表
     */
    PageVO<SendCarListVO> getSendCarList(String compid, String searchName, Integer page, Integer pageSize);

    /**
     * 调度派车中每个车辆的今日调度
     *
     * @param compid    企业代号
     * @param vehicleId 车号
     * @param beginTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return 调度派车列表
     */
    PageVO<SendCarDetailVO> getSendDetail(String compid, String vehicleId, String beginTime, String endTime,
                                          Integer page,
                                          Integer pageSize);


    /**
     * 调度派车详情
     *
     * @param compid 企业代号
     * @param taskId 任务单号
     * @return 调度派车列表
     */
    PageVO<SendCarDistanceVO> getSendCarDistance(String compid, String taskId);

    /**
     * 调度派车正在生产
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarTotalNumVO> getSendCarTodayNum(String compid, Integer page, Integer pageSize);

    /**
     * 调度派车今日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarTotalNumVO> getSendCarCountNum(String compid, Integer page, Integer pageSize);

    /**
     * 调度派车昨日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarTotalNumVO> getSendCarYesterDayNum(String compid, Integer page, Integer pageSize,
                                                     String time) throws ParseException;

    /**
     * 调度派车本月方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarTotalNumVO> getMonthTotalNum(String compid, Integer page, Integer pageSize,
                                               String monthStart, String monthEnd) throws ParseException;

    /**
     * 司机派车LED(老版本)
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产  1 等待生产
     * @param vehicleClass  班次
     * @return 司机排班LED
     *
     * 目前前台司机派车LED页面调用的是这个方法，没有调用新版本
     */
    @Deprecated
    List<DirverLEDListVO> getDriverShiftLED(String compid, String stirId, Integer vehicleStatus, String vehicleClass);


    /**
     * 司机派车LED(新版本)
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产  1 等待生产
     * @param vehicleClass  班次
     * @return 司机排班LED
     *
     * 此方法优化了老版本的代码，前台还未启用
     */
    Map<String, DirverLEDListVO> getDriverShiftLEDNew(String compid, String stirId, Integer vehicleStatus, String vehicleClass);


    /**
     * 司机App中的司机排班LED
     *
     * @param compid 企业id
     * @return 司机排班LED
     */
    List<ProductDriverListvo> getProductDriverShiftLED(String compid);

    /**
     * 司机排班列表信息（老版本）
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return 司机排班列表信息
     */
    PageVO<DriverShiftListVO> getDriverShiftList(String compid, String vehicleId, String personalName,
                                                 String personalCode, String workClass,
                                                 String beginTime, String endTime, Integer page, Integer pageSize);

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
    List<ShiftListVO> getDriverShiftListNew(String compid, String vehicleId, String personalName,
                                            String personalCode, String workClass,
                                            String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     * 查询司机
     *
     * @param compid 　企业id
     */
    PageVO<PersonalNameVO> getPersonalName(String compid);

    /**
     * 添加司机排班信息
     *
     * @param compid       企业id
     * @param opId         当前操作员
     * @param personalCode 司机代码
     * @param vehicleId    车号
     * @param workClass    班次
     * @param workStarTime 上班开始时间
     * @param workOverTime 上班结束时间
     * @param remarks      备注
     * @throws ErpException 定义的异常
     */
    void getDriverShiftInsert(String compid, String opId, String personalCode, String vehicleId, String workClass,
                              String workStarTime, String workOverTime, String remarks) throws ErpException;

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
     * @throws ErpException 定义的异常
     */
    void getDriverShiftUpdate(Integer id, String compid, String personalCode,
                              String vehicleId, String workClass, String workStarTime,
                              String workOverTime, String remarks) throws ErpException;



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
    PageVO<ProductionRatioVO> getProductionRatio(String compid, String beginTime, String endTime, String formulaCode,
                                                 String stgId, String stirId, String eppCode, String placing,
                                                 String taskId, Integer page,
                                                 Integer pageSize);


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
    PageVO<ProductionRatioVO> getTheoreticalproportioning(String compid, String beginTime, String endTime,
                                                          String formulaCode,
                                                          String stgId, String stirId, String eppCode, String placing,
                                                          String taskId, Integer page,
                                                          Integer pageSize);


    /**
     * 获取方量统计
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     * @param type      1 今日，2，昨日，3本月
     */
    SquareQuantityVO getSquareQuantitySum(String compid, String beginTime, String endTime, int type);


    /**
     * 获取今日预计方量
     *
     * @param compid  企业
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @throws SQLException
     */
    JSONArray phoneStatistics(String compid, String beginTime, String endTime) throws SQLException;


    JSONArray todayTask(String compid, String beginTime, String endTime) throws SQLException;

    /**
     * 获取默认的任务单号
     *
     * @param compid  企业
     */
    Map<String, String> makeAutoTaskPlanId(String compid) throws SQLException;

    /**
     * 判断任务单编号是否存在
     *
     * @param compid  企业
     * @param taskId  任务单号
     */
    boolean isExistence(String compid, String taskId);

    /**
     * 得到所有加价项目下拉
     *
     * @param compid  企业
     */
    List<PriceMarkupVO> getPriceMarkup(String compid);


    /**
     * 通过加价项目编号得到加价项目数据
     *
     * @param compid  企业
     * @param ppCode  加价项目
     */
    PriceMarkupVO getPriceMarkupByPPCode(String compid, String ppCode);


    /**
     * 添加任务单和加价项目
     *
     * @param taskId  任务单id
     * @param compid  企业id
     * @param priceMarkupVO 加价项目对象
     */
    void addTaskPriceMarkup(String compid, String taskId, PriceMarkupVO priceMarkupVO);

    /**
     * 修改任务单技术要求
     */
    void updateTechnicalRequirements(String compid, String taskId, String ppNames);

    /**
     * 修改任务单加价项目状态为0
     */
    void deletePPCodeStatus(String compid, String taskId);


    /**
     * 调度派车中查询正在生产的搅拌车
     *
     * @param compid 企业
     */
    List<DirverLEDListVO> getProduceCars(String compid);



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
    PageVO<TaskPlanListVO> buildTaskPlanList(String beginTime, String endTime, String eppCode, String placing,
                                             String taskId, Integer taskStatus, String compid, Integer verifyStatus,
                                             Integer buildId, Integer page, Integer pageSize) throws ErpException;

}
