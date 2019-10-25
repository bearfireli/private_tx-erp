package com.hntxrj.api;

import com.hntxrj.server.ConcreteService;
import com.hntxrj.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qyb
 *  ConcreteApi
 *  砼产量
 *  19-6-10 下午3:14
 **/
@RestController
@RequestMapping("/api/concrete")
@Slf4j
public class ConcreteApi {


    private ConcreteService concreteService;

    @Autowired
    public ConcreteApi(ConcreteService concreteService) {
        this.concreteService = concreteService;
    }


    /**getConcreteCount
     * 砼产量统计
     *
     * @param compid    　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     */
//    @Cacheable("getConcreteCount")
    @PostMapping("/getConcreteCount")
    public ResultVO getConcreteCount(String compid, String eppCode, String placing,
                                     String taskId, String stgId,
                                     Long beginTime, Long endTime,Integer timeStatus,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("getConcreteCount");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResultVO resultVO = ResultVO.create(concreteService.getConcreteCount(compid, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),timeStatus, page, pageSize));
        log.info("『砼产量统计』resultVO={}", resultVO);
        return resultVO;
    }

    /**
     * 砼产量统计合计
     *
     * @param compid    　企业
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　任务单号
     * @param stgId     　砼标记
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     * @param page      　页数
     * @param pageSize  　每页显示多少条
     */
    @PostMapping("/getConcreteSum")
    public ResultVO getConcreteSum(String compid, String eppCode, String placing,
                                   String taskId, String stgId,
                                   Long beginTime, Long endTime,
                                   Integer timeStatus,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(concreteService.getConcreteSum(compid, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),timeStatus, page, pageSize));
    }

}
