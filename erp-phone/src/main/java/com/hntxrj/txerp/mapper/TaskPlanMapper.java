package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface TaskPlanMapper {

    List<TaskPlanListVO> getTaskPlanList(String beginTime, String endTime, String eppCode,
                                         String builderCode, String placing, String taskId,
                                         Integer taskStatus, String compid,Integer verifyStatus);


    void verifyTaskPlan(String taskId, String compid, Integer verifyStatus, Date verifyTime);

    TaskPlanVO getTaskPlanByTaskId(String compid, String taskId);


    /**
     * 调度派车列表
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    List<SendCarListVO> getSendCarList(String compid,String searchName);


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
    List<DriverShiftLEDVO> getDriverShiftLED(String compid, String stirId, String vehicleStatus, String vehicleClass);

    List<ProductDriverLEDVo> getProductDriverShiftLED(String compid, String stirId);

    /**
     * 司机排班列表信息
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机
     * @param workClass    班次状态
     * @param beginTime    　　　　　开始时间
     * @param endTime      结束时间
     */
    List<DriverShiftListVO> getDriverShiftList(String compid, String vehicleId, String personalCode, String personalName,
                                               String workClass, String beginTime, String endTime);

    /**
     * 查询司机
     *
     * @param compid 　企业ｉｄ
     */
    List<PersonalNameVO> getPersonalName(String compid);

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
    List<SendCarTotalNumVO> getSendCarCountNum(String compid,String searchName);

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


    TaskPlanListVO selectid(String taskid);

    List<ProductionRatioVO> getProductionRatio(String compid, String beginTime, String endTime,
                                               String formulaCode, String stgId, String stirId,
                                               String eppCode, String placing, String taskId);

    List<ProductionRatioVO> getTheoreticalproportioning(String compid, String beginTime,
                                                        String endTime, String formulaCode,
                                                        String stgId, String stirId, String eppCode, String placing,
                                                        String taskId);


    SquareQuantityVO getSquareQuantitySum(String compid, String beginTime, String endTime);

    QueryTimeSetVO getQueryTime(String compid, int queryType);

    SquareQuantityVO phoneStatistics(String compid, String beginTime, String endTime);

    SquareQuantityVO taskPlanPreNum(String compid, String beginTime, String endTime);

    QueryTimeSetVO queryTimeId(String compid);

    QueryTimeSetVO queryTimeTask(String compid);

    List<TodayTaskVO> todayTask(String compid, String beginTime, String endTime);

    Integer getTaskIdCount(String compid, String taskId);

    List<PriceMarkupVO> getPriceMarkup(String compid);

    PriceMarkupVO getPriceMarkupByPPCode(String compid,String ppCode);

    void addTaskPriceMarkup(String compid, String taskId, String ppCode, BigDecimal unitPrice, BigDecimal selfDiscPrice, BigDecimal jumpPrice, BigDecimal towerCranePrice, BigDecimal otherPrice);

    List<String> getPPCodeBytaskId(String compid, String taskId);

    SystemVarInitVO getSystemVarInit(String compid);

    void deletePPCodeStatus(String compid, String taskId);
    void updateTechnicalRequirements(String compid, String taskId, String pPNames,String concreteMark);

    List<DriverShiftLEDVO> getProduceCars(String compid,String stirId);

    List<DriverShiftLEDVO> getCarsByTaskId(String compid, String taskId);
}
