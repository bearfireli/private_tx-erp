package com.hntxrj.txerp.mapper;


import com.hntxrj.txerp.entity.TaskPlan;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TaskPlanMapper {

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
     * @return 任务单列表对象
     */
    List<TaskPlanListVO> getTaskPlanList(String beginTime, String endTime, String eppCode,
                                         String builderCode, String placing, String taskId,
                                         Integer taskStatus, String compid, Integer verifyStatus);


    /**
     * 审核任务单
     *
     * @param taskId       任务单id
     * @param compid       企业id
     * @param verifyStatus 审核状态
     */
    void verifyTaskPlan(String taskId, String compid, Integer verifyStatus, Date verifyTime);

    /**
     * 获取任务单
     *
     * @param taskId 任务单id
     * @param compid 企业id
     */
    TaskPlanVO getTaskPlanByTaskId(String compid, String taskId);


    /**
     * 调度派车列表
     *
     * @param compid      企业代号
     * @param searchWords 搜索关键字
     * @return 调度派车列表
     */
    List<SendCarListVO> getSendCarList(String compid, String searchWords);


    /**
     * 调度派车详情
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarDistanceVO> getSendCarDistance(String compid, String TaskId);

    /**
     * 司机排班LED
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产  1 等待生产
     * @param vehicleClass  班次
     */
    List<DispatchVehicle> getDriverShiftLED(String compid, String stirId, Integer vehicleStatus, String vehicleClass);


    /**
     * 司机App中的司机排班LED
     *
     * @param compid        企业id
     * @param stirId        线号
     * @param vehicleStatus 车辆状态
     * @return 司机排班LED
     */
    List<ProductDriverLEDVo> getProductDriverShiftLED(String compid, String stirId, Integer vehicleStatus);

    /**
     * 司机排班列表信息
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机代号
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     */
    List<DriverShiftListVO> getDriverShiftListNew(String compid, String vehicleId, String personalCode,
                                                  String personalName, String workClass, String beginTime,
                                                  String endTime);

    /**
     * 查询司机
     *
     * @param compid     　企业id
     * @param driverName 司机名称
     */
    List<PersonalNameVO> getPersonalName(String compid, String driverName);

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
     */
    Integer getDriverShiftInsert(String compid, String opId, String personalCode, String vehicleId,
                                 String workClass, String workStarTime, String workOverTime,
                                 String remarks, Date createTime, Integer workTime, Integer upDown,
                                 Integer upDownMark, Integer recStatus);

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
     */
    Integer getDriverShiftUpdate(Integer id, String compid, String personalCode, String vehicleId,
                                 String workClass, String workStarTime, String workOverTime,
                                 String remarks, Date createTime);

    /**
     * 调度派车正在生产
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarTotalNumVO> getSendCarCountNum(String compid);

    /**
     * 调度派车今日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarTotalNumVO> getSendCarTodayNum(String compid);

    /**
     * 调度派车昨日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarTotalNumVO> getSendCarYesterDayNum(String compid, String time);

    /**
     * 调度派车本月方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarTotalNumVO> getMonthTotalNum(String compid, String monthStart, String monthEnd);


    /**
     * 获取任务单号
     */
    TaskPlanListVO selectId(String taskId);


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
     * @return 任务单生产配比日志查询
     */
    List<ProductionRatioVO> getProductionRatio(String compid, String beginTime, String endTime,
                                               String formulaCode, String stgId, String stirId,
                                               String eppCode, String placing, String taskId);

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
     * @return 任务单理论配比日志查询
     */
    List<ProductionRatioVO> getTheoreticalproportioning(String compid, String beginTime,
                                                        String endTime, String formulaCode,
                                                        String stgId, String stirId, String eppCode, String placing,
                                                        String taskId);


    /**
     * 获取方量统计
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     */
    SquareQuantityVO getSquareQuantitySum(String compid, String beginTime, String endTime, Integer quantityQueryType);

    /**
     * 从DD_QueryTimeSet表中获取用户自定义查询时间
     *
     * @param compid    　企业
     * @param queryType 　查询统计类型
     */
    QueryTimeSetVO getQueryTime(String compid, int queryType);

    /**
     * 获取今日预计方量
     *
     * @param compid    企业
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    SquareQuantityVO phoneStatistics(String compid, String beginTime, String endTime);

    /**
     * 预计方量
     */
    SquareQuantityVO taskPlanPreNum(String compid, String beginTime, String endTime);

    /**
     * 用户自定义时间查询
     */
    QueryTimeSetVO queryTimeId(String compid);

    /**
     * 用户自定义任务单预览时间查询
     */
    QueryTimeSetVO queryTimeTask(String compid);

    /**
     * 查询当天任务单总数
     */
    List<TodayTaskVO> todayTask(String compid, String beginTime, String endTime);

    /**
     * 判断任务单是否存在
     */
    Integer checkTaskIdExit(String compid, String taskId);

    /**
     * 获取加价项目
     */
    List<PriceMarkupVO> getPriceMarkup(String compid);

    /**
     * 根据加价项目编号获取加价项目名称
     *
     * @param compid 企业
     * @param ppCode 加价项目代号
     */
    PriceMarkupVO getPriceMarkupByPPCode(String compid, String ppCode);

    /**
     * 添加加价项目
     *
     * @param compid          企业
     * @param taskId          任务单
     * @param ppCode          加价项目编号
     * @param unitPrice       单价
     * @param selfDiscPrice   自卸价
     * @param jumpPrice       泵送价
     * @param towerCranePrice 塔吊价
     * @param otherPrice      其他价
     */
    void addTaskPriceMarkup(String compid, String taskId, String ppCode, BigDecimal unitPrice, BigDecimal selfDiscPrice,
                            BigDecimal jumpPrice, BigDecimal towerCranePrice, BigDecimal otherPrice);

    /**
     * 获取加价项目编号
     */
    List<String> getPPCodeByTaskId(String compid, String taskId);

    /**
     * 查询系统变量
     */
    SystemVarInitVO getSystemVarInit(String compid);

    /**
     * 删除任务单加价项目
     */
    void deletePPCodeStatus(String compid, String taskId);

    /**
     * 修改技术要求
     *
     * @param compid       企业id
     * @param taskId       任务单号
     * @param ppNames      加价项目名称
     * @param concreteMark 砼标记
     */
    void updateTechnicalRequirements(String compid, String taskId, String ppNames, String concreteMark);


    /**
     * 调度派车中获取所有正在生产的搅拌车车辆
     */
    List<DispatchVehicle> getProduceCars(String compid, String stirId);


    /**
     * 司机排班列表信息
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机代号
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     * @return 司机排班列表信息
     */
    List<DriverShiftListVO> getDriverShiftList(String compid, String vehicleId, String personalCode, String personalName, String workClass, String beginTime, String endTime);


    /**
     * 查询司机是否已经排班
     *
     * @param compid       企业代号
     * @param personalCode 司机代号
     * @param workClass    班次
     */
    Integer checkDriverScheduling(String compid, String personalCode, String workClass);


    /**
     * 调度派车中今日调度
     **/
    List<SendCarDetailVO> getSendDetail(String compid, String vehicleId, String beginTime, String endTime);

    /**
     * 施工方App中的任务单列表
     *
     * @param contractDetailCodes 子合同集合
     * @param contractUIDList     主合同集合
     * @param beginTime           开始时间
     * @param endTime             结束时间
     * @param eppCode             工程代号
     * @param placing             浇筑部位
     * @param taskId              任务单号
     * @param taskStatus          生产状态
     * @param verifyStatus        审核状态
     */
    List<TaskPlanListVO> buildTaskPlanList(List<String> contractDetailCodes, List<String> contractUIDList,
                                           String beginTime, String endTime, String eppCode, String placing,
                                           String taskId, Integer taskStatus, Integer verifyStatus);

    /**
     * 获取工地端任务单预计方量汇总
     *
     * @param contractDetailCodes 子合同集合
     * @param contractUIDList     主合同集合
     * @param beginTime           开始时间
     * @param endTime             结束时间
     * @param eppCode             工程代号
     * @param placing             浇筑部位
     * @param taskId              任务单号
     * @param taskStatus          任务单状态
     * @param verifyStatus        审核标识  0：未审核； 1：已审核
     * @return 任务单列表预计方量汇总
     */
    BigDecimal getBuildTaskPreCount(List<String> contractDetailCodes, List<String> contractUIDList, String beginTime,
                                    String endTime, String eppCode, String placing,
                                    String taskId, Integer taskStatus, Integer verifyStatus);

    /**
     * 任务单号集合
     *
     * @param compid      企业
     * @param searchWords 搜索关键字
     */
    List<String> getTaskIds(String compid, String searchWords);


    /**
     * 查询任务单集合下的所有车辆
     *
     * @param compid  企业代号
     * @param taskIds 任务单号集合
     * @return 车辆集合
     */
    List<DispatchVehicle> getCarsByTaskIds(String compid, List<String> taskIds);


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
    BigDecimal getPreNumCount(String beginTime, String endTime, String eppCode, String builderCode, String placing,
                              String taskId, Integer taskStatus, String compid, Integer verifyStatus);

    /**
     * 获取塌落度下拉
     *
     * @param compid 企业id
     * @param slump  塌落度
     */
    List<SlumpDropDownVO> getSlumpDropDown(String compid, String slump);

    //删除任务单
    void deleteTaskPlan(String compid, String taskId);

    // 根据任务单号查询任务单的实体类
    HashMap<String, String> selectOneByTaskId(String taskId, String compid);

    // 根据id查询司机排班信息
    HashMap<String, String> getDriverShiftById(Integer id, String compid);

    // 获取任务单加价项目
    Map<String, String> getTaskPriceMarkup(String compid, String taskId, String ppCode);

    // 获取任务单加价项目列表
    List<TaskPriceMarkup> getTaskPriceMarkupList(String compid, String taskId);

    // 获取司机排班对象
    Map<String, String> getDriverShiftVO(String compid, String vehicleId, String personalCode, String workStarTime);
}
