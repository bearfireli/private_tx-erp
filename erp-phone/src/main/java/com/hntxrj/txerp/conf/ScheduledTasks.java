package com.hntxrj.txerp.conf;


import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.rabbitmq.JPushUtil;
import com.hntxrj.txerp.server.CompService;
import com.hntxrj.txerp.server.MsgQueueService;
import com.hntxrj.txerp.server.VehicleService;
import com.hntxrj.txerp.vo.MessagePushVO;
import com.hntxrj.txerp.vo.VehicleIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    private final VehicleService vehicleService;
    private final CompService compService;

    private final JPushUtil jPushUtil;

    @Resource
    private RedisTemplate<String, List<VehicleIdVO>> redisTemplate;

    @Autowired
    public ScheduledTasks(MsgQueueService msgQueueService, VehicleService vehicleService, CompService compService,
                          JPushUtil jPushUtil) {
        this.vehicleService = vehicleService;
        this.compService = compService;
        this.jPushUtil = jPushUtil;
    }


    @Scheduled(cron = "*/60 * * * * ?")
    public void waitCarsPush() throws ErpException {
        //获取所有的企业id
        List<String> compIds = compService.getAllCompId();
        MessagePushVO messagePushVO = new MessagePushVO();

        //对每个企业等待生产的车辆进行消息推送
        for (String compid : compIds) {
            messagePush(compid, messagePushVO);
        }
    }


    private void messagePush(String compid, MessagePushVO messagePushVO) throws ErpException {
        messagePushVO.setCompid(compid);
        //当前正在等待的车辆
        List<VehicleIdVO> waitCars = vehicleService.getWaitCars(compid);

        //获取缓存中等待生产的车辆
        List<VehicleIdVO> vehicleIdVOS = redisTemplate.opsForValue().get("waitCars:" + compid);

        //先判断等待派车的车辆与缓存中的车辆是否一致，如果不一致，则发送消息提示
        if (vehicleIdVOS == null || vehicleIdVOS.size() < 1) {
            //缓存中等待派车的车辆为0辆
            for (int j = 0; j < waitCars.size(); j++) {
                //给每个查出来的等待派车的车辆发消息
                switch (j) {
                    case 0:
                        //给第一辆车发信息
                        firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                        break;
                    case 1:
                        //给第二辆车发信息
                        secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                        break;
                    case 2:
                        //给第三辆车发信息
                        thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
            }

        } else if (vehicleIdVOS.size() < 2) {
            //缓存中等待派车的车辆为1辆
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中第一辆车比较，如果不一样就给第一辆车辆发消息，其余等待派车的车辆直接发消息
                if (!waitCars.get(0).equals(vehicleIdVOS.get(0)) && j == 0) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j >= 1) {
                    switch (j) {
                        case 1:
                            //给第二辆车发信息
                            secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                            break;
                        case 2:
                            //给第三辆车发信息
                            thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                    }
                }

            }
        } else if (vehicleIdVOS.size() < 3) {
            //缓存中等待派车的车辆为2辆
            //跟缓存中前两辆车比较，如果不一样就给前两辆车辆发消息，其余等待派车的车辆直接发消息
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中第一辆车比较，如果不一样就给第一辆车辆发消息，其余等待派车的车辆直接发消息
                if (j == 0 && !waitCars.get(0).equals(vehicleIdVOS.get(0))) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j >= 1 && !waitCars.get(1).equals(vehicleIdVOS.get(1))) {
                    //给第二辆车发信息
                    secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
                if (j >= 2) {
                    //给第三辆车发信息
                    thirdWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
            }
        } else {
            for (int j = 0; j < waitCars.size(); j++) {
                //跟缓存中所有三辆等待车辆比较，如果不一样就给发消息
                if (j == 0 && !waitCars.get(0).equals(vehicleIdVOS.get(0))) {
                    //给第一辆发消息
                    firstWaitCarPush(compid, messagePushVO, waitCars.get(0).getVehicleId());
                }
                if (j == 1 && !waitCars.get(1).equals(vehicleIdVOS.get(1))) {
                    //给第二辆车发信息
                    secondWaitCarPush(compid, messagePushVO, waitCars.get(1).getVehicleId());
                }
                if (j == 2 && !waitCars.get(2).equals(vehicleIdVOS.get(2))) {
                    //给第三辆车发信息
                    thirdWaitCarPush(compid, messagePushVO, waitCars.get(2).getVehicleId());
                }
            }
        }

        //把新的等待派车的集合存进缓存中
        redisTemplate.opsForValue().set("waitCars:" + compid, waitCars);
    }


    //获取司机代号
    private List<String> getDriverCode(String compid, String vehicleId) {
        return vehicleService.getDriverByVehicleId(compid, vehicleId);
    }


    //给第一辆等待生产的车辆发消息
    private void firstWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车辆即将生产，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }

    //给第二辆等待生产的车辆发消息
    private void secondWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车前面还有一辆等待车辆，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }


    //给第三辆等待生产的车辆发消息
    private void thirdWaitCarPush(String compid, MessagePushVO messagePushVO, String vehicleId) throws ErpException {
        messagePushVO.setVehicleId(vehicleId);
        messagePushVO.setMessage(vehicleId + "号车前面还有两辆等待车辆，请做好准备");
        List<String> driverCodes = getDriverCode(compid, vehicleId);
        jPushUtil.erpDriverMessagePush(compid, messagePushVO, driverCodes);
    }


}
