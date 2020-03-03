package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.TaskPlan;
import com.hntxrj.txerp.im.MsgService;
import com.hntxrj.txerp.mapper.*;
import com.hntxrj.txerp.repository.TaskPlanRepository;
import com.hntxrj.txerp.server.TaskPlanService;
import com.hntxrj.txerp.util.EntityTools;
import com.hntxrj.txerp.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskPlanServiceImpl implements TaskPlanService {

    private final TaskPlanMapper taskPlanMapper;

    private final TaskPlanRepository taskPlanRepository;

    private final StockMapper stockMapper;
    private final ConcreteMapper concreteMapper;
    private final PublicInfoMapper publicInfoMapper;
    private final SystemVarInitMapper systemVarInitMapper;
    private final ConstructionMapper constructionMapper;
    private StirInfoSetServiceImpl stirInfoSetMapper;
    private final MsgService msgService;

    private final MsgMapper msgMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    public TaskPlanServiceImpl(TaskPlanMapper taskPlanMapper, TaskPlanRepository taskPlanRepository,
                               StockMapper stockMapper, ConcreteMapper concreteMapper,
                               PublicInfoMapper publicInfoMapper, SystemVarInitMapper systemVarInitMapper,
                               ConstructionMapper constructionMapper, StirInfoSetServiceImpl stirInfoSetMapper,
                               MsgService msgService, MsgMapper msgMapper) {
        this.taskPlanMapper = taskPlanMapper;
        this.taskPlanRepository = taskPlanRepository;
        this.stockMapper = stockMapper;
        this.concreteMapper = concreteMapper;
        this.publicInfoMapper = publicInfoMapper;
        this.systemVarInitMapper = systemVarInitMapper;
        this.constructionMapper = constructionMapper;
        this.stirInfoSetMapper = stirInfoSetMapper;
        this.msgService = msgService;
        this.msgMapper = msgMapper;
    }

    @Override
    public PageVO<TaskPlanListVO> getTaskPlanList(String beginTime, String endTime,
                                                  String eppCode, String builderCode,
                                                  String placing, String taskId, Integer taskStatus,
                                                  String compid, Integer verifyStatus, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<TaskPlanListVO> taskPlanListVOList = taskPlanMapper.getTaskPlanList(beginTime, endTime, eppCode,
                builderCode, placing, taskId, taskStatus, compid, verifyStatus);
        //循环截取预计时间preTime，格式为年月日
        for (TaskPlanListVO t : taskPlanListVOList) {
            if (!"".equals(t.getPreTime())) {
                t.setPreTime(t.getPreTime().substring(0, 16));
            }
            if (t.getOverNum() == null) {
                t.setOverNum(new BigDecimal("0.0"));
            }
        }
        PageInfo<TaskPlanListVO> pageInfo = new PageInfo<>(taskPlanListVOList);
        PageVO<TaskPlanListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public TaskPlanVO getTaskPlanDetail(String compid, String taskId) {
        List<String> ppCodes = taskPlanMapper.getPPCodeByTaskId(compid, taskId);
        TaskPlanVO taskPlanVO = taskPlanMapper.getTaskPlanByTaskId(compid, taskId);
        taskPlanVO.setPpCodes(ppCodes);
        return taskPlanVO;
    }


    private static String makeTaskId(String compid, String lastTask) {
        // 需要把任务单号修改为：P+企业代号(2为)+年后两位（2位）+顺序编号(5位)
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String taskId;
        taskId = "P" + compid + String.valueOf(year).substring(2);
        if (lastTask == null || "".equals(lastTask)) {
            lastTask = "0";
        }

        if (lastTask.length() >= 5) {
            lastTask = lastTask.substring(lastTask.length() - 5);
        }
        StringBuilder lastTaskBuilder = new StringBuilder(String.valueOf(Integer.parseInt(lastTask) + 1));
        while (lastTaskBuilder.length() < 5) {
            lastTaskBuilder.insert(0, "0");
        }
        lastTask = lastTaskBuilder.toString();
        return taskId + lastTask;
    }

    public static void main(String[] args) {
        System.out.println(makeTaskId("01", "P01170823008"));
        System.out.println(makeTaskId("01", "P18190830002"));
        System.out.println(makeTaskId("01", null));
    }

    @Override
    public void addTaskPlan(TaskPlan taskPlan,String type) throws ErpException {
        if (StringUtils.isEmpty(taskPlan.getCompid())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_COMPID);
        }
        if (taskPlan.getPreTime() == null) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_PRETIME);
        }
        if (StringUtils.isEmpty(taskPlan.getContractUid()) || StringUtils.isEmpty(taskPlan.getContractDetailCode())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_CONTRACT);
        }
        if (StringUtils.isEmpty(taskPlan.getEppCode())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_EPP);
        }
        if (StringUtils.isEmpty(taskPlan.getBuilderCode())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_BUILDER);
        }
        if (StringUtils.isEmpty(taskPlan.getStgId())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_STGID);
        }
        if (type.equals("1")) {
            /* type 下任务单标识  1 工地端 ，0 手机端*/
            taskPlan.setClientType(1);
        }else {
            taskPlan.setClientType(0);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (StringUtils.isEmpty(taskPlan.getTaskId())) {

            /*
             * *****************拼接TaskId******start****************/
            String taskTd = taskPlanSplicing(taskPlan.getCompid());
            taskPlan.setTaskId(taskTd);
        }

        /*
         ******************拼接TaskId******end****************/

        String markFlag = "A";
        String stgId = taskPlan.getStgId();
        String slump = taskPlan.getSlump();
        String slumps = slump.replaceAll("[^0-9]", "|");
        String[] a = slumps.split("\\|");
        String x = a[0];
        int y = Integer.parseInt(x);
        String slumpFlag = "";
        if (y <= 90) {
            slumpFlag = "(S2)";
        } else if (y < 160) {
            slumpFlag = "(S3)";
        } else {
            slumpFlag = "(S4)";
        }
        String concreteMark = markFlag + "-" + stgId + "-" + x + slumpFlag + "-GB/T14902";
        taskPlan.setConcreteMark(concreteMark);
        // 设置任务单为等待生产
        taskPlan.setTaskStatus(0);
        // 设置审核状态为未审核
        taskPlan.setVerifyStatus(false);
        taskPlan.setCreateTime(sdf.format(new Date()));
        EntityTools.setEntityDefaultValue(taskPlan);
        // 设置记录有效
        taskPlan.setRecStatus('1');
        taskPlan.setTaskType(1);
        taskPlan.setOpId("0225");
        if ("".equals(taskPlan.getDefaultJump())) {
            taskPlan.setDefaultJump("自卸");
        }
        taskPlan.setAdjustmentTime(taskPlan.getPreTime());
        try {
            taskPlanRepository.save(taskPlan);
            int typeId =1;
            List<RecipientVO> recipoentList = msgMapper.getRecipientList(taskPlan.getCompid(),typeId);
            for (RecipientVO r : recipoentList) {
                SendmsgVO sendmsgVO = new SendmsgVO();
                sendmsgVO.setSyncOtherMachine(2);
                sendmsgVO.setToAccount(r.getPhone());
                sendmsgVO.setMsgLifeTime(7);
                sendmsgVO.setMsgContent("有新添加的任务单，请审核!");
                msgService.sendMsg(sendmsgVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_TASK_ERROR);
        }
    }

    @Override
    public void verifyTaskPlan(String taskId, String compid, Integer verifyStatus) throws ErpException {
        try {
            taskPlanMapper.verifyTaskPlan(taskId, compid, verifyStatus, new Date());
            if (verifyStatus==1){
                int typeId = 2;
                List<RecipientVO> recipoentList = msgMapper.getRecipientList(compid,typeId);
                for (RecipientVO r : recipoentList) {
                    SendmsgVO sendmsgVO = new SendmsgVO();
                    sendmsgVO.setSyncOtherMachine(2);
                    sendmsgVO.setToAccount(r.getPhone());
                    sendmsgVO.setMsgLifeTime(7);
                    String  msgContent ="任务单：【"+taskId+"】已审核，请开配比。";
                    sendmsgVO.setMsgContent(msgContent);
                    msgService.sendMsg(sendmsgVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.VERIFY_TASK_ERROR);
        }
    }

    @Override
    public PageVO<SendCarListVO> getSendCarList(String compid, String searchWords, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SendCarListVO> sendCarList = taskPlanMapper.getSendCarList(compid, searchWords);

        //查询出所有正在生产的任务单号集合。
        List<String> taskIds = new ArrayList<>();
        for (SendCarListVO sendCarListVO : sendCarList) {
            if (sendCarListVO.getTotalProduceNum() == null) {
                sendCarListVO.setTotalProduceNum("0.0");
            }
            if (sendCarListVO.getTaskId() != null) {
                taskIds.add(sendCarListVO.getTaskId());
            }

        }

        //根据任务单号集合查询出所有的车号。
        List<DispatchVehicle> cars = new ArrayList<>();
        if (taskIds.size() > 0) {
            cars = taskPlanMapper.getCarsByTaskIds(compid, taskIds);
        }
        //根据每个车辆的任务单号，把所有车辆关联到调度派车列表中
        for (SendCarListVO sendCarListVO : sendCarList) {
            List<DispatchVehicle> dispatchVehicleList = new ArrayList<>();
            for (DispatchVehicle car : cars) {
                //判断厦门华信特殊情况（先打票，再生产）
                if (compid.equals("24")) {
                    if (car.getTaskStatus() == 1 && car.getInvoiceType() == 4) {
                        car.setVehicleStatus("3");
                        car.setStatusName("生产");
                    }
                }
                //根据任务单号关联调度派车列表和其对应的车辆
                if (sendCarListVO.getTaskId().equals(car.getTaskId())) {
                    dispatchVehicleList.add(car);
                }
            }
            sendCarListVO.setCars(dispatchVehicleList);
        }
        PageInfo<SendCarListVO> pageInfo = new PageInfo<>(sendCarList);
        PageVO<SendCarListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<SendCarDetailVO> getSendDetail(String compid, String vehicleId, String beginTime, String endTime,
                                                 Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SendCarDetailVO> sendCarDetailVOS = taskPlanMapper.getSendDetail(compid, vehicleId, beginTime, endTime);

        PageInfo<SendCarDetailVO> pageInfo = new PageInfo<>(sendCarDetailVOS);
        PageVO<SendCarDetailVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 调度派车详情
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @Override
    public PageVO<SendCarDistanceVO> getSendCarDistance(String compid, String taskId) {
        List<SendCarDistanceVO> sendCarList = taskPlanMapper.getSendCarDistance(compid, taskId);
        PageInfo<SendCarDistanceVO> pageInfo = new PageInfo<>(sendCarList);
        PageVO<SendCarDistanceVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 调度派车今日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @Override
    public PageVO<SendCarTotalNumVO> getSendCarCountNum(String compid, Integer page, Integer pageSize) {
        List<SendCarTotalNumVO> sendCarList = taskPlanMapper.getSendCarCountNum(compid);
        PageInfo<SendCarTotalNumVO> pageInfo = new PageInfo<>(sendCarList);
        PageVO<SendCarTotalNumVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 调度派车今日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @Override
    public PageVO<SendCarTotalNumVO> getSendCarTodayNum(String compid, Integer page, Integer pageSize) {

        List<SendCarTotalNumVO> sendCarTotalNumVO = taskPlanMapper.getSendCarTodayNum(compid);
        PageInfo<SendCarTotalNumVO> pageInfo = new PageInfo<>(sendCarTotalNumVO);
        PageVO<SendCarTotalNumVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 调度派车昨日方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @Override
    public PageVO<SendCarTotalNumVO> getSendCarYesterDayNum(String compid, Integer page, Integer pageSize,
                                                            String time) throws ParseException {
        SendCarTotalNumVO totalNum = new SendCarTotalNumVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdf.parse(sdf.format(new Date()));
        Date as = DateUtils.addDays(today, -1);
        if (totalNum.getTime() != as) {
            totalNum.setTime(as);
        }
        List<SendCarTotalNumVO> SendCarTotalNumVO = taskPlanMapper.getSendCarYesterDayNum(compid, time);
        PageInfo<SendCarTotalNumVO> pageInfo = new PageInfo<>(SendCarTotalNumVO);
        PageVO<SendCarTotalNumVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 调度派车本月方量
     *
     * @param compid 企业代号
     * @return 调度派车列表
     */
    @Override
    public PageVO<SendCarTotalNumVO> getMonthTotalNum(String compid, Integer page, Integer pageSize,
                                                      String monthStart, String monthEnd) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

        Date date = new Date();
        monthStart = format.format(date) + "-01";
        monthEnd = format.format(DateUtils.addMonths(date, 1)) + "-01";


        List<SendCarTotalNumVO> SendCarTotalNumVO = taskPlanMapper.getMonthTotalNum(compid, monthStart, monthEnd);
        PageInfo<SendCarTotalNumVO> pageInfo = new PageInfo<>(SendCarTotalNumVO);
        PageVO<SendCarTotalNumVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机排班LED（老版本）
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产 , 1 等待生产(7、停用，包含在等待生产中) , 4 维修 ， 5 休息 ，
     *                      8 工地托水  ， 9 工地拖泵 ，10 工地脱水拖泵 ，11 场内托水 ， 16 自动回厂
     * @param vehicleClass  班次
     * @return 司机排班LED
     */
    @Override
    public List<DirverLEDListVO> getDriverShiftLED(String compid, String stirId, Integer vehicleStatus,
                                                   String vehicleClass) {
        List<DirverLEDListVO> list = new ArrayList<>();

        DirverLEDListVO driverLEDListVO = new DirverLEDListVO();   //所有正在生产中的车辆集合
        List<DispatchVehicle> zeroStirIdCars = new ArrayList<>();    //所有0号线正在生产的集合
        List<DispatchVehicle> firstStirIdCars = new ArrayList<>();  //所有1号线正在生产的集合
        List<DispatchVehicle> secondStirIdCars = new ArrayList<>();  //所有2号线正在生产的集合
        List<DispatchVehicle> thirdStirIdCars = new ArrayList<>();  //所有3号线正在生产的集合
        List<DispatchVehicle> fourthStirIdCars = new ArrayList<>();  //所有4号线正在生产的集合
        List<DispatchVehicle> fifthStirIdCars = new ArrayList<>();  //所有5号线正在生产的集合
        driverLEDListVO.setStatus(DirverLEDListVO.PRODUCTION_VEHICLE_STATUS);
        driverLEDListVO.setStatusName("正在生产");
        vehicleStatus = DirverLEDListVO.PRODUCTION_VEHICLE_STATUS;

        List<DispatchVehicle> dispatchVehicleList = taskPlanMapper.getDriverShiftLED(compid, stirId, vehicleStatus,
                vehicleClass);
        if (dispatchVehicleList != null) {
            driverLEDListVO.setCars(dispatchVehicleList);
            driverLEDListVO.setCarNum(dispatchVehicleList.size());
            for (DispatchVehicle driverShiftLEDVO : dispatchVehicleList) {
                if ("0".equals(driverShiftLEDVO.getStirId())) {
                    zeroStirIdCars.add(driverShiftLEDVO);
                } else if ("1".equals(driverShiftLEDVO.getStirId())) {
                    firstStirIdCars.add(driverShiftLEDVO);
                } else if ("2".equals(driverShiftLEDVO.getStirId())) {
                    secondStirIdCars.add(driverShiftLEDVO);
                } else if ("3".equals(driverShiftLEDVO.getStirId())) {
                    thirdStirIdCars.add(driverShiftLEDVO);
                } else if ("4".equals(driverShiftLEDVO.getStirId())) {
                    fourthStirIdCars.add(driverShiftLEDVO);
                } else if ("5".equals(driverShiftLEDVO.getStirId())) {
                    fifthStirIdCars.add(driverShiftLEDVO);
                }
            }
        }
        driverLEDListVO.setZeroStirIdCars(zeroStirIdCars);
        driverLEDListVO.setFirstStirIdCars(firstStirIdCars);
        driverLEDListVO.setSecondStirIdCars(secondStirIdCars);
        driverLEDListVO.setThirdStirIdCars(thirdStirIdCars);
        driverLEDListVO.setFourthStirIdCars(fourthStirIdCars);
        driverLEDListVO.setFifthStirIdCars(fifthStirIdCars);
        list.add(driverLEDListVO);

        DirverLEDListVO driverLEDListVOs = new DirverLEDListVO();
        driverLEDListVOs.setStatus(DirverLEDListVO.WAIT_VEHICLE_STATUS);
        driverLEDListVOs.setStatusName("等待生产");
        vehicleStatus = DirverLEDListVO.WAIT_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVOs, compid, stirId, vehicleStatus, vehicleClass));


        DirverLEDListVO driverLEDListVO1 = new DirverLEDListVO();
        driverLEDListVO1.setStatus(DirverLEDListVO.REPAIR_VEHICLE_STATUS);
        driverLEDListVO1.setStatusName("维修");
        vehicleStatus = DirverLEDListVO.REPAIR_VEHICLE_STATUS;

        list.add(getDriverLEDListVO(driverLEDListVO1, compid, stirId, vehicleStatus, vehicleClass));


        DirverLEDListVO driverLEDListVO2 = new DirverLEDListVO();
        driverLEDListVO2.setStatus(DirverLEDListVO.REST_VEHICLE_STATUS);
        driverLEDListVO2.setStatusName("休息");
        vehicleStatus = DirverLEDListVO.REST_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO2, compid, stirId, vehicleStatus, vehicleClass));

        DirverLEDListVO driverLEDListVO3 = new DirverLEDListVO();
        driverLEDListVO3.setStatus(DirverLEDListVO.WATER_VEHICLE_STATUS);
        driverLEDListVO3.setStatusName("工地脱水");
        vehicleStatus = DirverLEDListVO.WATER_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO3, compid, stirId, vehicleStatus, vehicleClass));

        DirverLEDListVO driverLEDListVO4 = new DirverLEDListVO();
        driverLEDListVO4.setStatus(DirverLEDListVO.PUMP_VEHICLE_STATUS);
        driverLEDListVO4.setStatusName("工地拖泵");
        vehicleStatus = DirverLEDListVO.PUMP_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO4, compid, stirId, vehicleStatus, vehicleClass));

        DirverLEDListVO driverLEDListVO5 = new DirverLEDListVO();
        driverLEDListVO5.setStatus(DirverLEDListVO.WATER_PUMP_VEHICLE_STATUS);
        driverLEDListVO5.setStatusName("工地拖水拖泵");
        vehicleStatus = DirverLEDListVO.WATER_PUMP_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO5, compid, stirId, vehicleStatus, vehicleClass));

        DirverLEDListVO driverLEDListVO6 = new DirverLEDListVO();
        driverLEDListVO6.setStatus(DirverLEDListVO.FACTORY_WATER_VEHICLE_STATUS);
        driverLEDListVO6.setStatusName("场内托水");
        vehicleStatus = DirverLEDListVO.FACTORY_WATER_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO6, compid, stirId, vehicleStatus, vehicleClass));

        DirverLEDListVO driverLEDListVO7 = new DirverLEDListVO();
        driverLEDListVO7.setStatus(DirverLEDListVO.BACK_VEHICLE_STATUS);
        driverLEDListVO7.setStatusName("自动回厂");
        vehicleStatus = DirverLEDListVO.BACK_VEHICLE_STATUS;
        list.add(getDriverLEDListVO(driverLEDListVO7, compid, stirId, vehicleStatus, vehicleClass));

        return list;
    }

    /**
     * 司机排班LED（新版本）
     *
     * @param compid        企业id
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 　车状态  3 正在生产 , 1 等待生产(7、停用，包含在等待生产中) , 4 维修 ， 5 休息 ，
     *                      8 工地托水  ， 9 工地拖泵 ，10 工地脱水拖泵 ，11 场内托水 ， 16 自动回厂
     * @param vehicleClass  班次
     * @return 司机排班LED
     */
    @Override
    public Map<String, DirverLEDListVO> getDriverShiftLEDNew(String compid, String stirId, Integer vehicleStatus,
                                                             String vehicleClass) {
        //查询出所有状态的车辆
        List<DispatchVehicle> dispatchVehicleList = taskPlanMapper.getDriverShiftLED(compid, stirId,
                null, vehicleClass);
        //用于返回的map集合。
        Map<String, DirverLEDListVO> driverLEDMap = new HashMap<>();

        for (DispatchVehicle dispatchVehicle : dispatchVehicleList) {
            if (driverLEDMap.get(dispatchVehicle.getVehicleStatus()) == null) {
                DirverLEDListVO dirverLEDListVO = new DirverLEDListVO();
                //每种状态下的车辆集合。
                List<DispatchVehicle> cars = new ArrayList<>();
                dirverLEDListVO.setStatus(Integer.parseInt(dispatchVehicle.getVehicleStatus()));
                dirverLEDListVO.setStatusName(dispatchVehicle.getStatusName());
                cars.add(dispatchVehicle);
                dirverLEDListVO.setCars(cars);
                dirverLEDListVO.setCarNum(1);
                driverLEDMap.put(dispatchVehicle.getVehicleStatus(), dirverLEDListVO);
            } else {
                driverLEDMap.get(dispatchVehicle.getVehicleStatus()).getCars().add(dispatchVehicle);
                //总车辆加1
                driverLEDMap.get(dispatchVehicle.getVehicleStatus()).setCarNum(
                        driverLEDMap.get(dispatchVehicle.getVehicleStatus()).getCarNum() + 1);
            }
        }

        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.WAIT_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.WAIT_VEHICLE_STATUS, "待班");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.PRODUCTION_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.PRODUCTION_VEHICLE_STATUS, "生产");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.REPAIR_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.REPAIR_VEHICLE_STATUS, "维修");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.REST_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.REST_VEHICLE_STATUS, "休息");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.WATER_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.WATER_VEHICLE_STATUS, "工地脱水");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.PUMP_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.PUMP_VEHICLE_STATUS, "工地拖泵");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.WATER_PUMP_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.WATER_PUMP_VEHICLE_STATUS, "工地脱水拖泵");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.FACTORY_WATER_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.FACTORY_WATER_VEHICLE_STATUS, "场内托水");
        }
        if (driverLEDMap.get(String.valueOf(DirverLEDListVO.BACK_VEHICLE_STATUS)) == null) {
            setDriverLEDMap(driverLEDMap, DirverLEDListVO.BACK_VEHICLE_STATUS, "自动回厂");
        }

        //各个线号正在生产的车辆
        Map<String, List<DispatchVehicle>> productMap = new HashMap<>();
        for (DispatchVehicle productCar :
                driverLEDMap.get(String.valueOf(DirverLEDListVO.PRODUCTION_VEHICLE_STATUS)).getCars()) {
            //循环正在生产的车辆，按照线号分类
            if (productMap.get(productCar.getStirId()) == null) {
                List<DispatchVehicle> dispatchStirIdVehicles = new ArrayList<>();
                dispatchStirIdVehicles.add(productCar);
                productMap.put(productCar.getStirId(), dispatchStirIdVehicles);
            } else {
                productMap.get(productCar.getStirId()).add(productCar);
            }
        }

        driverLEDMap.get(String.valueOf(DirverLEDListVO.PRODUCTION_VEHICLE_STATUS)).setProductVehicleMap(productMap);

        return driverLEDMap;
    }


    //3 ： 正在生产    1：等待生产
    @Override
    public List<ProductDriverListvo> getProductDriverShiftLED(String compid) {
        //最终返回的集合，包括车辆状态和车辆集合。
        List<ProductDriverListvo> ProductDriverList = new ArrayList<>();
        //正在生产的车辆
        ProductDriverListvo producingDriverList = new ProductDriverListvo();
        producingDriverList.setVehicleStatus(3);
        producingDriverList.setVehicleStatusName("正在生产");
        //等待生产的车辆
        ProductDriverListvo waitDriverList = new ProductDriverListvo();
        waitDriverList.setVehicleStatus(1);
        waitDriverList.setVehicleStatusName("等待生产");

        //从系统变量表中查询出用户的设置信息，包括是否显示等待派车，和显示几辆
        DriverWaitLEDVO driverWaitLEDVO = systemVarInitMapper.getDriverWaitLED(compid);
        if (driverWaitLEDVO == null) {
            driverWaitLEDVO = new DriverWaitLEDVO();
        }
        //获取所有等待生产的车辆的集合
        List<ProductDriverLEDVo> waitDriverShiftLED =
                taskPlanMapper.getProductDriverShiftLED(compid, null, 1);
        if (waitDriverShiftLED != null && waitDriverShiftLED.size() > driverWaitLEDVO.getValue()) {
            waitDriverShiftLED = waitDriverShiftLED.subList(0, driverWaitLEDVO.getValue());
        }

        //获取全部线号
        List<StirIdVO> stirIds = stockMapper.getStirIds(compid);
        //获取所有正在生产的车辆的集合，根据线号分类
        List<List<ProductDriverLEDVo>> produceList = new ArrayList<>();

        for (StirIdVO stirId : stirIds) {
            List<ProductDriverLEDVo> produceDriverShiftLED =
                    taskPlanMapper.getProductDriverShiftLED(compid, stirId.getStirId(), 3);
            if (produceDriverShiftLED != null && produceDriverShiftLED.size() != 0) {
                if (produceDriverShiftLED.size() > 3) {
                    produceDriverShiftLED = produceDriverShiftLED.subList(0, 3);
                }
                produceList.add(produceDriverShiftLED);
            }
            //获取正在生产车辆的总数
            assert produceDriverShiftLED != null;
            producingDriverList.setCarNum(producingDriverList.getCarNum() + produceDriverShiftLED.size());
        }

        //获取等待生产的车辆总数
        assert waitDriverShiftLED != null;
        waitDriverList.setCarNum(waitDriverShiftLED.size());


        producingDriverList.setCars(produceList);
        //创建一个集合，用于存储等待生产的车辆的集合，便于前台处理。
        List<List<ProductDriverLEDVo>> waitList = new ArrayList<>();
        waitList.add(waitDriverShiftLED);
        waitDriverList.setCars(waitList);
        ProductDriverList.add(producingDriverList);
        if (driverWaitLEDVO.getIsShow()) {
            ProductDriverList.add(waitDriverList);
        }

        return ProductDriverList;
    }

    /**
     * 司机排班列表信息(老版本)
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机
     * @param workClass    班次状态
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return 司机排班列表信息
     */
    @Override
    public PageVO<DriverShiftListVO> getDriverShiftList(String compid, String vehicleId, String personalCode,
                                                        String personalName,
                                                        String workClass,
                                                        String beginTime, String endTime,
                                                        Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "WorkStarTime DESC");
        List<DriverShiftListVO> sendCarList = taskPlanMapper.getDriverShiftList(compid, vehicleId,
                personalCode, personalName, workClass, beginTime, endTime);
        PageInfo<DriverShiftListVO> pageInfo = new PageInfo<>(sendCarList);
        PageVO<DriverShiftListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 司机排班列表信息(新版本)
     *
     * @param compid       企业ｉｄ
     * @param vehicleId    车号
     * @param personalCode 司机
     * @param workClass    班次状态
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return 司机排班列表信息
     */
    @Override
    public List<ShiftListVO> getDriverShiftListNew(String compid, String vehicleId, String personalCode,
                                                   String personalName,
                                                   String workClass,
                                                   String beginTime, String endTime,
                                                   Integer page, Integer pageSize) {
        List<ShiftListVO> list = new ArrayList<>();
        List<DropDownVO> dropDownVOS = publicInfoMapper.getDropDown(16, compid);
        if (dropDownVOS != null) {
            for (DropDownVO d : dropDownVOS) {
                ShiftListVO shiftListVO = new ShiftListVO();
                shiftListVO.setWorkClass(d.getCode());
                shiftListVO.setWorkName(d.getName());
                workClass = d.getCode();
                shiftListVO.setShiftList(taskPlanMapper.getDriverShiftListNew(compid, vehicleId,
                        personalCode, personalName, workClass, beginTime, endTime));
                list.add(shiftListVO);
            }
        }
        return list;
    }

    /**
     * 查询司机
     *
     * @param compid 　企业ｉｄ
     */
    @Override
    public PageVO<PersonalNameVO> getPersonalName(String compid) {
        List<PersonalNameVO> sendCarList = taskPlanMapper.getPersonalName(compid);
        PageInfo<PersonalNameVO> pageInfo = new PageInfo<>(sendCarList);
        PageVO<PersonalNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 添加司机排班信息
     *
     * @param compid       　　　企业ｉｄ
     * @param opId         　　　　　当前操作员
     * @param personalCode 　　司机代码
     * @param vehicleId    车号
     * @param workClass    班次
     * @param workStarTime 　　　　上班开始时间
     * @param workOverTime 下班结束时间
     * @param remarks      备注
     * @throws ErpException 定义的异常
     */
    @Override
    public void getDriverShiftInsert(String compid, String opId, String personalCode,
                                     String vehicleId, String workClass,
                                     String workStarTime, String workOverTime, String remarks) throws ErpException {
        if (vehicleId.isEmpty()) {
            throw new ErpException(ErrEumn.ADD_VEHICLEID_ERROR);
        }
        if (personalCode.isEmpty()) {
            throw new ErpException(ErrEumn.ADD_PERSONALCODE_ERROR);
        }
        if (workClass.isEmpty()) {
            throw new ErpException(ErrEumn.ADD_WORKCLASS_ERROR);
        }
        if (remarks == null || "".equals(remarks)) {
            remarks = "　";
        }
        Date createTime = new Date();
        Integer workTime = 0;
        Integer upDown = 0;
        Integer upDownMark = 0;
        Integer recStatus = 1;
        Integer count = taskPlanMapper.checkDriverScheduling(compid, personalCode, workClass);
        if (count > 0) {
            //此司机已经在此班次绑定过车辆
            throw new ErpException(ErrEumn.DRIVER_IS_BIND);
        }

        try {
            taskPlanMapper.getDriverShiftInsert(compid, opId, personalCode,
                    vehicleId, workClass, workStarTime,
                    workOverTime, remarks, createTime, workTime, upDown, upDownMark, recStatus);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_ERROR);
        }
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
     * @throws ErpException 定义的异常
     */
    @Override
    public void getDriverShiftUpdate(Integer id, String compid, String personalCode,
                                     String vehicleId, String workClass,
                                     String workStarTime, String workOverTime, String remarks) throws ErpException {
        Date createTime = new Date();
        Integer count = taskPlanMapper.checkDriverScheduling(compid, personalCode, workClass);
        if (count > 0) {
            //此司机已经在此班次绑定过车辆
            throw new ErpException(ErrEumn.DRIVER_IS_BIND);
        }

        try {
            taskPlanMapper.getDriverShiftUpdate(id, compid, personalCode, vehicleId,
                    workClass, workStarTime,
                    workOverTime, remarks, createTime);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADJUNCT_UPDATE_ERROR);
        }

    }

    @Override
    public PageVO<ProductionRatioVO> getProductionRatio(String compid, String beginTime, String endTime,
                                                        String formulaCode, String stgId, String stirId,
                                                        String eppCode, String placing,
                                                        String taskId,
                                                        Integer page, Integer pageSize) {
        PageVO<ProductionRatioVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<ProductionRatioVO> vehicleWorkloadDetailVOS =
                taskPlanMapper.getProductionRatio(compid, beginTime, endTime, formulaCode, stgId,
                        stirId, eppCode, placing, taskId);
        PageInfo<ProductionRatioVO> pageInfo = new PageInfo<>(vehicleWorkloadDetailVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<ProductionRatioVO> getTheoreticalproportioning(String compid, String beginTime,
                                                                 String endTime, String formulaCode,
                                                                 String stgId, String stirId,
                                                                 String eppCode, String placing,
                                                                 String taskId,
                                                                 Integer page, Integer pageSize) {
        PageVO<ProductionRatioVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<ProductionRatioVO> vehicleWorkloadDetailVOS =
                taskPlanMapper.getTheoreticalproportioning(compid, beginTime, endTime, formulaCode, stgId,
                        stirId, eppCode, placing, taskId);
        PageInfo<ProductionRatioVO> pageInfo = new PageInfo<>(vehicleWorkloadDetailVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }


    @Override
    public SquareQuantityVO getSquareQuantitySum(String compid, String beginTime, String endTime, int type) {
        //根据传递过来的type，判断查询的是今日，昨日还是本月的方量。
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        String _tmpMo = endTime.substring(5, 7);
        if ("13".equals(_tmpMo)) {
            int year = Integer.parseInt(endTime.substring(0, 4));
            endTime = endTime.replace(year + "-13", (year + 1) + "-01");
        }

        if (type == 3) {
            int queryType = 2;
            QueryTimeSetVO queryTime = taskPlanMapper.getQueryTime(compid, queryType);
            if (queryTime != null) {
                endTime = endTime.substring(0, 8) + queryTime.getQueryStopTime();
                beginTime = beginTime.substring(0, 8) + queryTime.getQueryStartTime();
            } else {
                endTime = endTime.substring(0, 8) + "01 00:00:00";
                beginTime = beginTime.substring(0, 8) + "01 00:00:01";
            }
        } else {
            //拼接时间
            if (type == 1) {
                int queryType = 6;
                QueryTimeSetVO queryTime = taskPlanMapper.getQueryTime(compid, queryType);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (queryTime != null) {
                    beginTime = beginTime.substring(0, 10) + " " + queryTime.getQueryStartTime();
                } else {
                    beginTime = beginTime.substring(0, 10) + " 00:00:00";
                }
                endTime = sdf1.format(new Date());
            }
            if (type == 2) {
                int queryType = 1;
                QueryTimeSetVO queryTime = taskPlanMapper.getQueryTime(compid, queryType);
                endTime = endTime.substring(0, 10);
                beginTime = beginTime.substring(0, 10);
                try {
                    Date date = sdf.parse(beginTime);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DATE, -1);
                    if (queryTime != null) {
                        beginTime = sdf.format(cal.getTime()) + " " + queryTime.getQueryStartTime();
                        endTime = endTime + " " + queryTime.getQueryStartTime();
                    } else {
                        beginTime = sdf.format(cal.getTime()) + " " + "00:00:00";
                        endTime = endTime.substring(0, 10) + " " + "00:00:00";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        SquareQuantityVO vehicleWorkloadDetailVOS = taskPlanMapper.getSquareQuantitySum(compid, beginTime, endTime);

        //根据compid、beginTime、endTime从生产消耗表中查询出生产方量。
        BigDecimal productNum = concreteMapper.getProductConcreteSum(compid, beginTime, endTime);

        if (vehicleWorkloadDetailVOS != null) {
            vehicleWorkloadDetailVOS.setSale_num(vehicleWorkloadDetailVOS.getSaleNum());
            if (productNum != null) {
                vehicleWorkloadDetailVOS.setProduce_num(productNum.intValue());
            } else {
                vehicleWorkloadDetailVOS.setProduce_num(0);
            }
        }

        return vehicleWorkloadDetailVOS;
    }

    @Override
    public JSONArray phoneStatistics(String compid, String beginTime, String endTime) {

        SquareQuantityVO taskPlanPreNum = taskPlanMapper.taskPlanPreNum(compid, beginTime, endTime);
        QueryTimeSetVO queryTime = taskPlanMapper.queryTimeId(compid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (queryTime != null) {
            beginTime = beginTime.substring(0, 10) + " " + queryTime.getQueryStartTime();
        } else {
            beginTime = beginTime.substring(0, 10) + " 00:00:00";
        }
        endTime = sdf.format(new Date());
        SquareQuantityVO squareQuantityVO = taskPlanMapper.phoneStatistics(compid, beginTime, endTime);
        if (taskPlanPreNum != null) {
            if (squareQuantityVO != null) {
                squareQuantityVO.setProduce_num((int) squareQuantityVO.getSaleNum());
                squareQuantityVO.setPre_num((int) taskPlanPreNum.getPreNum());
            }
        }
        JSONArray array = new JSONArray();
        if (squareQuantityVO != null) {
            array.add(squareQuantityVO);
        }
        System.out.println(array.size());
        return array;
    }

    @Override
    public JSONArray todayTask(String compid, String beginTime, String endTime) {
        QueryTimeSetVO queryTime = taskPlanMapper.queryTimeTask(compid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (queryTime != null) {
            try {
                Date date = sdf.parse(beginTime);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (queryTime.getCountInterval() == 1) {
                    cal.add(Calendar.DATE, 1);
                } else {
                    cal.add(Calendar.DATE, queryTime.getCountInterval());
                }
                beginTime = sdf.format(cal.getTime()) + " " + queryTime.getQueryStartTime();
                endTime = endTime + " " + queryTime.getQueryStartTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<TodayTaskVO> todayTaskVO = taskPlanMapper.todayTask(compid, beginTime, endTime);
        TodayTaskVO todayTaskVO1 = new TodayTaskVO();
        int i = 1;
        for (TodayTaskVO taskVO : todayTaskVO) {
            if (i == 1) {
                todayTaskVO1.setTaskStatus1(taskVO.getTaskStatus());
            } else if (i == 2) {
                todayTaskVO1.setTaskStatus2(taskVO.getTaskStatus());
            } else if (i == 3) {
                todayTaskVO1.setTaskStatus3(taskVO.getTaskStatus());
            } else if (i == 4) {
                todayTaskVO1.setTaskStatus4(taskVO.getTaskStatus());
            } else if (i == 5) {
                todayTaskVO1.setTaskStatus5(taskVO.getTaskStatus());
            }
            i++;
        }
        JSONArray array = new JSONArray();
        array.add(todayTaskVO1);
        System.out.println(array.size());
        return array;
    }

    /**
     * 给前台返回一个默认的任务单号
     */
    @Override
    public Map<String, String> makeAutoTaskPlanId(String compid) {
        Map<String, String> taskPlanIdMap = new HashMap<>();
        taskPlanIdMap.put("taskId", taskPlanSplicing(compid));
        return taskPlanIdMap;
    }

    /**
     * 判断任务单编号是否存在
     */
    @Override
    public boolean isExistence(String compid, String taskId) {
        Integer taskIdCount = taskPlanMapper.checkTaskIdExit(compid, taskId);
        return taskIdCount != null && taskIdCount != 0;
    }

    /**
     * 得到所有加价项目下拉
     */
    @Override
    public List<PriceMarkupVO> getPriceMarkup(String compid) {
        return taskPlanMapper.getPriceMarkup(compid);
    }


    /**
     * 通过加价项目编号得到加价项目数据
     */
    @Override
    public PriceMarkupVO getPriceMarkupByPPCode(String compid, String ppCode) {
        return taskPlanMapper.getPriceMarkupByPPCode(compid, ppCode);
    }

    /**
     * 添加任务单和加价项目
     */
    public void addTaskPriceMarkup(String compid, String taskId, PriceMarkupVO priceMarkupVO) {
        taskPlanMapper.addTaskPriceMarkup(compid, taskId, priceMarkupVO.getPPCode(), priceMarkupVO.getUnitPrice(),
                priceMarkupVO.getSelfDiscPrice(), priceMarkupVO.getJumpPrice(),
                priceMarkupVO.getTowerCranePrice(), priceMarkupVO.getOtherPrice());
    }


    /**
     * 修改任务单技术要求
     */
    @Override
    public void updateTechnicalRequirements(String compid, String taskId, String ppNames) {
        //根据compid查询系统变量
        SystemVarInitVO systemVarInitVO = taskPlanMapper.getSystemVarInit(compid);
        TaskPlanVO taskPlan = taskPlanMapper.getTaskPlanByTaskId(compid, taskId);
        String markFlag = "A";
        String stgId = taskPlan.getStgId();
        String slump = taskPlan.getSlump();
        String slumps = slump.replaceAll("[^0-9]", "|");
        String[] a = slumps.split("\\|");
        String x = a[0];
        int y = Integer.parseInt(x);
        String slumpFlag = "";
        if (y <= 90) {
            slumpFlag = "(S2)";
        } else if (y < 160) {
            slumpFlag = "(S3)";
        } else {
            slumpFlag = "(S4)";
        }
        if (systemVarInitVO != null) {
            if (systemVarInitVO.getVarValue() == 1) {
                if (!"".equals(ppNames)) {
                    String concreteMark = markFlag + "-" + stgId + "-" + x + slumpFlag + "-" + ppNames + "-GB/T14902";
                    //把选择的特殊材料名称添加到技术要求里面
                    taskPlanMapper.updateTechnicalRequirements(compid, taskId, ppNames, concreteMark);
                }
            }
        }
    }

    /**
     * 删除任务单加价项目
     */
    @Override
    public void deletePPCodeStatus(String compid, String taskId) {
        taskPlanMapper.deletePPCodeStatus(compid, taskId);
    }

    /**
     * 调度派车中获取所有正在生产的搅拌车车辆
     */
    @Override
    public List<DirverLEDListVO> getProduceCars(String compid) {
        List<StirInfoSetVO> stirInfoSet = stirInfoSetMapper.getStirInfoSet(compid);
        List<DirverLEDListVO> list = new ArrayList<>();
        for (StirInfoSetVO stir : stirInfoSet) {
            DirverLEDListVO dirverLEDListVO = new DirverLEDListVO();
            String stirId = stir.getStirId();
            dirverLEDListVO.setStatus(Integer.valueOf(stirId));
            dirverLEDListVO.setStatusName(stir.getStirName());
            dirverLEDListVO.setCars(taskPlanMapper.getProduceCars(compid, stirId));
            list.add(dirverLEDListVO);
        }
        return list;
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
    @Override
    public PageVO<TaskPlanListVO> buildTaskPlanList(String beginTime, String endTime, String eppCode, String placing,
                                                    String taskId, Integer taskStatus, String compid,
                                                    Integer verifyStatus, Integer buildId,
                                                    Integer page, Integer pageSize) throws ErpException {
        //查询当前施工方关联的所有子合同
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);
        if (contractDetailCodes.size() == 0) {
            throw new ErpException(ErrEumn.NOT_BIND_CONTRACT);
        }
        if (contractUIDList.size() == 0) {
            throw new ErpException(ErrEumn.NOT_BIND_CONTRACT);
        }

        //根据子合同查询任务单.
        PageHelper.startPage(page, pageSize);
        List<TaskPlanListVO> taskPlanListVOList = taskPlanMapper.buildTaskPlanList(contractDetailCodes,
                contractUIDList, beginTime, endTime, eppCode,
                placing, taskId, taskStatus, verifyStatus);
        //循环截取preTime，格式为年月日
        for (TaskPlanListVO t : taskPlanListVOList) {
            if (!"".equals(t.getPreTime())) {
                t.setPreTime(t.getPreTime().substring(0, 16));
            }
            if (t.getOverNum() == null) {
                t.setOverNum(new BigDecimal("0.0"));
            }
        }
        PageInfo<TaskPlanListVO> pageInfo = new PageInfo<>(taskPlanListVOList);
        PageVO<TaskPlanListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    //任务单号拼接
    private String taskPlanSplicing(String compid) {
        int page = 0;
        int pageSize = 1;
        //获取年
        SimpleDateFormat sdf2 = new SimpleDateFormat("yy");
        Date date = new Date();
        String year = sdf2.format(date);
        String taskId = "P" + compid + year;
        PageHelper.startPage(page, pageSize, "TaskId desc");
        TaskPlanListVO selectId = taskPlanMapper.selectId(taskId);
        if (selectId != null) {
            taskId = makeTaskId(compid, selectId.getTaskId());
        } else {
            taskId = "p" + compid + year + "00001";
        }

        return taskId;
    }

    //派车LED模块：根据公司代号查询出不同生产状态的车辆和每种状态的车辆总数
    private DirverLEDListVO getDriverLEDListVO(DirverLEDListVO driverLEDListVO, String compid, String stirId,
                                               Integer vehicleStatus, String vehicleClass) {
        List<DispatchVehicle> driverShiftLED = taskPlanMapper.getDriverShiftLED(compid, stirId,
                vehicleStatus, vehicleClass);
        if (driverShiftLED != null) {
            driverLEDListVO.setCars(driverShiftLED);
            driverLEDListVO.setCarNum(driverShiftLED.size());
        }
        return driverLEDListVO;
    }

    //派车LED模块中如果不存在某种状态的车辆，创建一个对象，赋值后存入返回的map中。
    private void setDriverLEDMap(Map<String, DirverLEDListVO> driverLEDMap, Integer status, String statusName) {
        List<DispatchVehicle> dispatchVehicles = new ArrayList<>();
        DirverLEDListVO dirverLEDListVO = new DirverLEDListVO();
        dirverLEDListVO.setStatus(status);
        dirverLEDListVO.setStatusName(statusName);
        dirverLEDListVO.setCars(dispatchVehicles);
        dirverLEDListVO.setCarNum(0);
        driverLEDMap.put(String.valueOf(status), dirverLEDListVO);
    }


}
