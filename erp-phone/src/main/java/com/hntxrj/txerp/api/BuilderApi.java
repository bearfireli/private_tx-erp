package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/builder")
@Log4j
public class BuilderApi {
    private final BuilderService builderService;
    private SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public BuilderApi(BuilderService builderService) {
        this.builderService = builderService;

    }


    /**
     * 获取施工单位下拉
     *
     * @param builderName 施工单位名称
     * @param compid      企业id
     * @param page        页码
     * @param pageSize    每页数量
     * @return 施工单位下拉对象
     */
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String builderName, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(builderService.getBuilderDropDown(compid, builderName, page, pageSize));
    }


    /**
     * 工地端App发货统计
     *
     * @param buildId   　 施工方用户id
     * @param type      　 type: 查询时间类型; 1:派车时间；0:离场时间
     * @param eppCode   　 工程代码
     * @param placing   　 浇筑部位
     * @param taskId    　 任务单号
     * @param stgId     　 砼标号
     * @param beginTime 　 开始时间
     * @param endTime   　 结束时间
     * @param page      　　页数
     * @param pageSize  　　每页显示多少条
     * @return 产销统计列表
     */
    @PostMapping("/getBuildConcreteCount")
    public ResultVO getBuilderConcreteCount(Integer buildId, String eppCode, String placing,
                                            String taskId, String stgId,
                                            Long beginTime, Long endTime, Integer type,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        return ResultVO.create(builderService.getBuilderConcreteCount(buildId, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type, page, pageSize));

    }

    /**
     * 工地端App发货总量（销量）统计
     *
     * @param buildId   　施工方用户id
     * @param type      　type: 查询时间类型; 1:派车时间；0:离场时间
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标号
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     */
    @PostMapping("/getBuildConcreteSum")
    public ResultVO getBuilderConcreteSum(Integer buildId, String eppCode, String placing,
                                          String taskId, String stgId,
                                          Long beginTime, Long endTime, Integer type) throws ErpException {
        return ResultVO.create(builderService.getBuilderConcreteSum(buildId, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type));
    }

    /**
     * 获取小票签收列表
     *
     * @param buildId     企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param upStatus    签收状态
     * @param builderCode 施工单位代号
     * @param taskId      任务单id
     * @param placing     浇筑部位
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收列表
     */
    @PostMapping("/getBuildTaskSaleInvoiceList")
    public ResultVO getBuildTaskSaleInvoiceList(Integer buildId, Long beginTime, Long endTime, String eppCode,
                                                Byte upStatus, String builderCode, String taskId, String placing,
                                                String taskStatus, @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {

        return ResultVO.create(builderService.getBuildTaskSaleInvoiceList(buildId,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus, builderCode, taskId, placing, taskStatus, page, pageSize));
    }


    /**
     * 调度派车列表
     *
     * @param buildId    施工方用户id
     * @param searchName 搜索关键字(以后此字段用searchWords代替)
     * @param page       页码
     * @param pageSize   每页数量
     * @return 调度派车列表
     */
    @PostMapping("/getBuildSendCarList")
    public ResultVO getBuildSendCarList(Integer buildId,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String searchName) throws ErpException {
        return ResultVO.create(builderService.getBuildSendCarList(buildId, searchName, page, pageSize));
    }


}
