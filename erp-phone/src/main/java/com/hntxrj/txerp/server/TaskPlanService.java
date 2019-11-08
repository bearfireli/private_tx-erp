package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.TaskPlan;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;

import java.sql.SQLException;
import java.text.ParseException;
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
                                           Integer taskStatus, String compid, Integer page, Integer pageSize);


    TaskPlanVO getTaskPlanDetail(String compid, String taskId);


    void addTaskPlan(TaskPlan taskPlan) throws ErpException;


    void verifyTaskPlan(String taskId, String compid, Integer verifyStatus) throws ErpException;


    /**
     * 调度派车列表
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarListVO> getSendCarList(String compid, Integer page, Integer pageSize);




    /**
     * 调度派车详情
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    PageVO<SendCarDistanceVO> getSendCarDistance(String compid, String taskId);

    /**
     * 调度派车长在生产
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
     * 司机排班LED
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产  1 等待生产
     * @param vehicleClass  班次
     * @return 司机排班LED
     */
    List<DirverLEDListVO> getDriverShiftLED(String compid, String stirId, String vehicleStatus, String vehicleClass);


    /**
     * 司机App中的司机排班LED
     *
     * @param compid        企业id
     * @return 司机排班LED
     */
    List<ProductDriverLEDListVO> getProductDriverShiftLED(String compid);

    /**
     * 司机排班列表信息
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
     * 查询司机
     *
     * @param compid 　企业ｉｄ
     */
    PageVO<PersonalNameVO> getPersonalName(String compid);

    /**
     * 添加司机排班信息
     *
     * @param compid       　　　企业ｉｄ
     * @param opId         　　　　　当前操作员
     * @param personalCode 　　司机代码
     * @param vehicleId    车号
     * @param workClass    班次
     * @param workStarTime 　　　　上班开始时间
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

    PageVO<ProductionRatioVO> getProductionRatio(String compid, String beginTime, String endTime, String formulaCode,
                                                 String stgId, String stirId, String eppCode, String placing,
                                                 String taskId, Integer page,
                                                 Integer pageSize);

    PageVO<ProductionRatioVO> getTheoreticalproportioning(String compid, String beginTime, String endTime,
                                                          String formulaCode,
                                                          String stgId, String stirId, String eppCode, String placing,
                                                          String taskId, Integer page,
                                                          Integer pageSize);


    SquareQuantityVO getSquareQuantitySum(String compid, String beginTime, String endTime, int type);

    JSONArray phoneStatistics(String compid, String beginTime, String endTime) throws SQLException;

    JSONArray todayTask(String compid, String beginTime, String endTime) throws SQLException;

    /**
     * 获取默认的任务单号
     */
    Map<String, String> makeAutoTaskPlanId(String compid) throws SQLException;

    /**
     * 判断任务单编号是否存在
     */
    boolean isExistence(String compid, String taskId);

    /**
     * 得到所有加价项目下拉
     */
    List<PriceMarkupVO> getPriceMarkup(String compid);


    /**
     * 通过加价项目编号得到加价项目数据
     */
    PriceMarkupVO getPriceMarkupByPPCode(String compid, String ppCode);


    /**
     * 添加任务单和加价项目
     */
    void addTaskPriceMarkup(String compid, String taskId, PriceMarkupVO priceMarkupVO);
}
