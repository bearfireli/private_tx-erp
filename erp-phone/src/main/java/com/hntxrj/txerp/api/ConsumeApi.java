package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.ConsumeService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/consume")
public class ConsumeApi {
    private final ConsumeService consumeService;

    @Autowired
    public ConsumeApi(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }

    /**
     * 任务单消耗列表
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 任务单消耗列表
     */
    @PostMapping("/getTaskConsumeList")
    public ResultVO getTaskConsumeList(String compid, Long beginTime, Long endTime,
                                       String vehicleId, String stgId , String taskId, String stirId,
                                       Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(consumeService.getTaskConsumeList(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                vehicleId,stgId , taskId, stirId, page, pageSize));
    }

    /**
     * 每盘配料明细
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 每盘配料明细
     */
    @PostMapping("/getFormulaDetails")
    public ResultVO getFormulaDetails(String compid, Long beginTime, Long endTime,
                                        String vehicleId,String stgId , String taskId, String stirId,
                                        Integer page, Integer pageSize){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(consumeService.getFormulaDetails(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                vehicleId,stgId,taskId, stirId, page, pageSize));
    }

    /**
     * 标号消耗汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 标号消耗汇总
     */
    @PostMapping("/getConsumptionTotal")
    public ResultVO getConsumptionTotal(String compid, Long beginTime, Long endTime,
                                        String vehicleId, String stgId, String taskId, String stirId,
                                        Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(consumeService.getConsumptionTotal(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                vehicleId,stgId , taskId, stirId, page, pageSize));

    }

    /**
     * 查询材料名
     * @param compid 企业id
     * @return  查询材料名
     */
    @PostMapping("/getProductDatail")
    public ResultVO getProductDatail(String compid,Integer stirid){
        return ResultVO.create(consumeService.getProductDatail(compid,stirid));

    }


    /**
     * 原材料统计汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @PostMapping("/getMatTotal")
    public ResultVO getMatTotal(String compid, Long beginTime, Long endTime,
                                String vehicleId, String taskId, String stirId,
                                String stgId,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(consumeService.getMatTotal(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                vehicleId, taskId, stirId, stgId, page, pageSize));

    }

    /**
     * 生产消耗汇总合计方量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @PostMapping("/getConsumeClose")
    public ResultVO getConsumeClose(String compid, Long beginTime, Long endTime,
                                    String vehicleId, String stgId, String taskId, String stirId,
                                    Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(consumeService.getConsumeClose(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                vehicleId, stgId, taskId, stirId, page, pageSize));

    }


    /**
     * 获取指定时间的超差盘数
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     */
    @PostMapping("/getErroPan")
    public ResultVO getErroPan(String compid,
                               Long beginTime,
                               Long endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer value = consumeService.getErroPan(compid, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)));
        return ResultVO.create(value);
    }
}