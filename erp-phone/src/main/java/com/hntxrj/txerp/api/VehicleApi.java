package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.VehicleService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleApi {


    private final VehicleService vehicleService;

    @Autowired
    public VehicleApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    /**
     * 搅拌车砼运输明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @PostMapping("/getVehicleWorkloadDetail")
    public ResultVO getVehicleWorkloadDetail(String compid, String personalName, String eppCode, String placing,
                                             String taskId, String vehicleId,
                                             Long beginTime, Long endTime,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getVehicleWorkloadDetail(compid, personalName,
                eppCode, placing, taskId,
                vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 搅拌车砼运输汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @PostMapping("/getVehicleWorkloadSummary")
    public ResultVO getVehicleWorkloadSummary(String compid, String personalName, String eppCode, String placing,
                                              String taskId, String vehicleId,
                                              Long beginTime, Long endTime,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getVehicleWorkloadSummary(compid, personalName,
                eppCode, placing, taskId,
                vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 搅拌车砼运输汇总合计
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @PostMapping("/getVehicleWorkloadSummaryCount")
    public ResultVO getVehicleWorkloadSummaryCount(String compid, String personalName, String eppCode, String placing,
                                                   String taskId, String vehicleId,
                                                   Long beginTime, Long endTime,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getVehicleWorkloadSummaryCount(compid, personalName,
                eppCode, placing, taskId,
                vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 搅拌车拖水拖泵明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    @PostMapping("/getVehicleWorkTowingPumpDetail")
    public ResultVO getVehicleWorkTowingPumpDetail(String compid, String personalName, String eppCode, String placing,
                                                   String taskId, String vehicleId,
                                                   Long beginTime, Long endTime,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getVehicleWorkTowingPumpDetail(compid, personalName,
                eppCode, placing, taskId,
                vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 搅拌车拖水拖泵汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     */
    @PostMapping("/getVehicleWorkTowingPumpCount")
    public ResultVO getVehicleWorkTowingPumpCount(String compid, String personalName, String eppCode, String placing,
                                                  String taskId, String vehicleId,
                                                  Long beginTime, Long endTime,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getVehicleWorkTowingPumpCount(compid, personalName,
                eppCode, placing, taskId,
                vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 搅拌车过磅查询
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empNameb   过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param page       分页
     * @param pageSize   每页显示条数
     */
    @PostMapping("/getWorkloadStatistics")
    public ResultVO getWorkloadStatistics(String compid, String eppCode, String empNameb,
                                          @RequestParam(defaultValue = "0")Integer weightType,
                                          Long beginTime, Long endTime,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getWorkloadStatistics(compid,
                eppCode, empNameb, weightType,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 司机砼运输明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getDriverTransportationDetails")
    public ResultVO getDriverTransportationDetails(String compid, String eppCode, String placing,
                                                   String taskId, String vehicleId, String personalName,
                                                   Long beginTime, Long endTime,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getDriverTransportationDetails(compid, eppCode,
                placing, taskId, vehicleId, personalName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 司机砼运输汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param isNewVersion 是否是新版本标识
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getDriverTransportationCount")
    public ResultVO getDriverTransportationCount(String compid, String eppCode, String placing,
                                                 String taskId, String vehicleId, String personalName,
                                                 @RequestParam(defaultValue = "0") String isNewVersion,
                                                 Long beginTime, Long endTime,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("司机砼运输汇总: vehicleId=" + vehicleId);
        return ResultVO.create(vehicleService.getDriverTransportationCount(compid, eppCode,
                placing, taskId, vehicleId, personalName,isNewVersion,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 司机砼运输砼产量合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getDriverTransportationSum")
    public ResultVO getDriverTransportationSum(String compid, String eppCode, String placing,
                                               String taskId, String vehicleId, String personalName,
                                               Long beginTime, Long endTime,
                                               @RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getDriverTransportationSum(compid, eppCode,
                placing, taskId, vehicleId, personalName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 司机砼运输车数合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getDriverTransportationCarNumList")
    public ResultVO getDriverTransportationCarNumList(String compid, String eppCode, String placing,
                                                      String taskId, String vehicleId, String personalName,
                                                      Long beginTime, Long endTime,
                                                      @RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getDriverTransportationCarNumList(compid, eppCode,
                placing, taskId, vehicleId, personalName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }


    /**
     * 司机拖水拖泵汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param isNewVersion 是否是新版本
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getDriverDragPumpCount")
    public ResultVO getDriverDragPumpCount(String compid, String eppCode, String placing,
                                           String taskId, String vehicleId, String personalName,
                                           @RequestParam(defaultValue = "0")String isNewVersion,
                                           Long beginTime, Long endTime,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getDriverDragPumpCount(compid, eppCode, placing,
                taskId, vehicleId, personalName,isNewVersion,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }


    /**
     * 泵车汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getPumpTruckCount")
    public ResultVO getPumpTruckCount(String compid, String eppCode, String personalName,String taskId,
                                      String stirId, String vehicleId,
                                      Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getPumpTruckCount(compid, eppCode, personalName, stirId, vehicleId,taskId,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }


    /**
     * 泵工汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param isNewVersion   是否是新版本
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getPumpOperatorTruckCount")
    public ResultVO getPumpOperatorTruckCount(String compid, String eppCode, String personalName,
                                              String stirId, String vehicleId,String taskId,
                                              @RequestParam(defaultValue = "0") String isNewVersion,
                                              Long beginTime, Long endTime,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getPumpOperatorTruckCount(compid, eppCode, personalName,
                stirId, vehicleId,taskId,isNewVersion,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 泵车明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getPumpTruckDetails")
    public ResultVO getPumpTruckDetails(String compid, String eppCode, String personalName,
                                        String stirId, String vehicleId,String typeName,String taskId,
                                        Long beginTime, Long endTime,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getPumpTruckDetails(compid, eppCode, personalName, stirId, vehicleId,typeName,taskId,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 工作方量统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getPumpTruckWorkloadStatistics")
    public ResultVO getPumpTruckWorkloadStatistics(String compid,
                                                   String eppCode,
                                                   String personalName,
                                                   String stirId,
                                                   String vehicleId,
                                                   String taskId,
                                                   Long beginTime,
                                                   Long endTime,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getPumpTruckWorkloadStatistics(compid, eppCode, personalName,taskId,
                stirId, vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }


    /**
     * 泵车合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    @PostMapping("/getPumpTruckSum")
    public ResultVO getPumpTruckSum(String compid,
                                    String eppCode,
                                    String personalName,
                                    String stirId,
                                    String vehicleId,
                                    Long beginTime,
                                    Long endTime,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(vehicleService.getPumpTruckSum(compid, eppCode, personalName,
                stirId, vehicleId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }
}
