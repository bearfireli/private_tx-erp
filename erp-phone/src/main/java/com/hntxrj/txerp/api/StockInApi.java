package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.server.StockInSelectService;
import com.hntxrj.txerp.server.StockInServer;
import com.hntxrj.txerp.server.StockInCollectService;
import com.hntxrj.txerp.vo.ResultVO;
import com.hntxrj.txerp.vo.WeightMatParentNameVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "库存接口", description = "提供库存相关的 api")
@RestController
@RequestMapping("/api/stockIn")
public class StockInApi {
    private final StockInSelectService stockInService;
    private final StockInCollectService stockInCollectService;
    private final StockInServer stockInServer;

    @Autowired
    public StockInApi(StockInSelectService stockInService, StockInCollectService stockInCollectService,
                      StockInServer stockInServer) {

        this.stockInService = stockInService;
        this.stockInCollectService = stockInCollectService;
        this.stockInServer = stockInServer;
    }

    /**
     * /*原材料过磅查询 （老版本 4.2.1+1.apk 之前版本）
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param matName   材料名称
     * @param saleType  业务类别    0：进货，  1：出货，  2：其他，  3：退货
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅查询 （老版本 4.2.1+1.apk 之前版本）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saleType", value = "业务类别 0：进货,1：出货,2：其他,3：退货",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer", paramType = "query"),
    })
    @PostMapping("/getStockInDetails")
    public ResultVO getStockInDetails(String matName, String vehicleId, String supName, String compid, Long beginTime,
                                      Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize, String saleType) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInService.getStockInDetails(matName, vehicleId, supName, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page,
                pageSize, "".equals(saleType) ? null : saleType));
    }

    /**
     * /*原材料过磅查询(新版本  4.2.1+1.apk 之后版本)
     *
     * @param compid      企业id
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param vehicleId   车号
     * @param supName     供货商
     * @param matName     材料名称
     * @param saleType    业务类别    0：进货，  1：出货，  2：其他，  3：退货
     * @param isPassOrNot 材料检测  是否合格 1 合格 0 不合格 2 未检测
     * @param page        页数
     * @param pageSize    每页数量
     * @return 原材料统计汇总
     */

    @ApiOperation("材料过磅列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saleType", value = "业务类别 0：进货，1：出货，2：其他，3：退货",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isPassOrNot", value = "材料检测  是否合格 1 合格 0 不合格 2 未检测",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "String", paramType = "query"),
    })
    @PostMapping("/getStockInList")
    public ResultVO getStockInList(String matName, String vehicleId, String supName, String compid, Long beginTime,
                                   Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize, String saleType,
                                   Integer isPassOrNot) {


        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInService.getStockInList(matName, vehicleId, supName, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize,
                "".equals(saleType) ? null : saleType,
                isPassOrNot));
    }

    /**
     * 材料过磅详情查询
     *
     * @param stiCode 过磅单号
     * @param compid  企业id
     * @return 返回值
     */
    @ApiOperation("材料过磅详情查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stiCode", value = "过磅单号", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/stockInListDetail")
    public ResultVO stockInListDetail(String stiCode, String compid) {
        return ResultVO.create(stockInService.stockInListDetail(stiCode, compid));
    }


    /**
     * /*原材料过磅查询结算重量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅查询结算重量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供应商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saleType", value = "业务类别 0：进货，1：出货，2：其他，3：退货",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getStockInSelectClose")
    public ResultVO getStockInSelectClose(String matName, String vehicleId, String supName,
                                          String compid, Long beginTime, Long endTime,
                                          Integer page, Integer pageSize, String saleType) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInService.getStockInSelectClose(matName, vehicleId, supName, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize,
                "".equals(saleType) ? null : saleType));
    }

    /**
     * 原材料过磅修改语句
     *
     * @param compid    站别代号
     * @param stICode   过磅单号
     * @param grWeight  毛重
     * @param deductNum 另扣量
     * @param taWeight  皮重
     * @param netWeight 净重
     * @param deduct    另扣率
     * @param clWeight  结算重量
     * @param remarks   备注
     */
    @ApiOperation("原材料过磅修改（弃用）")
    @PostMapping("/updateStockInDate")
    public ResultVO updateStockInDate(String supCode, String stkCode, String saleType, String compid, String stICode,
                                      String grWeight, String deductNum,
                                      String taWeight, String netWeight,
                                      String deduct, String clWeight, String remarks, String firstTime,
                                      String secondTime,
                                      String supExFactory, String vehicleId, String matCode, String opid, String opids,
                                      String supOilNum, String stlTimes) {

        return ResultVO.create(stockInService.updateStockInDate(supCode, stkCode, saleType, compid, stICode,
                grWeight, deductNum,
                taWeight, netWeight, deduct, clWeight, remarks, firstTime, secondTime, supExFactory, vehicleId, matCode,
                opid, opids, supOilNum, stlTimes));
    }

    /**
     * 获取供货商一共多少种
     */
    @ApiOperation("获取供应商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供应商名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getSupName")
    public ResultVO getSupName(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getSupName(supName, compid, page, pageSize));
    }

    /**
     * 获取库位一共多少种
     */
    @ApiOperation("获取库位列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "库位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getStoNames")
    public ResultVO getStoNames(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getStoNames(supName, compid, page, pageSize));
    }

    /**
     * 获取业务类别一共多少种
     */
    @ApiOperation("获取业务列表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getTypeName")
    public ResultVO getTypeName(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")
            Integer pageSize) {
        return ResultVO.create(stockInService.getTypeName(page, pageSize));
    }

    /**
     * 获取材料名称
     */
    @ApiOperation("获取材料名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getMaterialName")
    public ResultVO getMaterialName(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getMaterialName(supName, compid, page, pageSize));

    }

    /**
     * 获取过磅员信息
     */
    @ApiOperation("获取过磅员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "过磅员名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getOverweightMation")
    public ResultVO getOverweightMation(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getOverweightMation(supName, compid, page, pageSize));

    }

    /**
     * 获取车辆号码信息
     */
    @ApiOperation("获取车辆代号列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getVehicleNumber")
    public ResultVO getVehicleNumber(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getVehicleNumber(supName, compid, page, pageSize));

    }

    /**
     * /*原材料明细汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param matName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料明细汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matSpecs", value = "材料规格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getMatDetailsList")
    public ResultVO getMatDetailsList(String vehicleId, String supName, String matSpecs, String compid, Long beginTime,
                                      Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize, String matName) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInCollectService.getMatDetailsList(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, matName));
    }

    /**
     * 原材料明细汇总合计入库量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param MatName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料明细汇总合计入库量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matSpecs", value = "材料规格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getStockInCollectClose")
    public ResultVO getStockInCollectClose(String vehicleId, String supName, String matSpecs, String compid,
                                           Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInCollectService.getStockInCollectClose(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * /*原材料汇总统计
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param MatName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料汇总统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matSpecs", value = "材料规格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getMatStatistics")
    public ResultVO getMatStatistics(String vehicleId, String supName, String matSpecs, String compid,
                                     Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInCollectService.getMatStatistics(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * /*原材料汇总统计合计购入量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param MatName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料汇总统计合计购入量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matSpecs", value = "材料规格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getMatStatisticsClose")
    public ResultVO getMatStatisticsClose(String vehicleId, String supName, String matSpecs, String compid,
                                          Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInCollectService.getMatStatisticsClose(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * /*原材料过磅汇总　材料入库汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param MatName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 材料入库汇总
     */
    @ApiOperation("原材料过磅汇总（根据入库库位查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matSpecs", value = "材料规格", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matName", value = "材料名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getMaterialCount")
    public ResultVO getMaterialCount(String vehicleId, String supName, String matSpecs, String compid,
                                     Long beginTime, Long endTime,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize, String MatName) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInCollectService.getMaterialCount(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * 原材料过磅统计  材料名称
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（根据材料名称查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightByMat")
    public ResultVO getWeighByMat(String empName, String compid, String vehicleId,
                                  String stoName, String supName, Long beginTime, Long endTime,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {

        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByMat(
                empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                page, pageSize));

    }

    /**
     * /*原材料过磅统计  车辆代号
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（根据车辆代号查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightByVechicId")
    public ResultVO getWeighByVechicId(String empName, String compid, String vehicleId,
                                       String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                       Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByVehicleId(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计。供应商名
     *
     * @param compid       企业id
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param vehicleId    车号
     * @param supName      供货商
     * @param empName      过磅员
     * @param page         页数
     * @param pageSize     每页数量
     * @param stoName      入库库位
     * @param isNewVersion 是否是新版本。 0：不是新版本， 1：是新版本
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（根据供应商查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isNewVersion", value = "是否是新版本。 0：不是新版本， 1：是新版本",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightByStoName")
    public ResultVO getWeighByStoName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "0") String isNewVersion,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByStoName(empName, compid, vehicleId, stoName, supName,
                isNewVersion, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计。入库库位
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（根据入库库位查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightBySupName")
    public ResultVO getWeighBySupName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "10") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightBySupName(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计。过磅员
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（根据过磅员查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightByEmpName")
    public ResultVO getWeighByEmpName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "10") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByEmpName(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计。综合信息
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅汇总（综合信息）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getSynthesizeByMat")
    public ResultVO getSynthesizeByMat(String empName, String compid, String vehicleId,
                                       String stoName, String supName, Long beginTime, Long endTime,
                                       @RequestParam(defaultValue = "10") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getSynthesizeByMat(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计合计入库量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("原材料过磅统计合计入库量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "每页数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightClose")
    public ResultVO getWeightClose(String empName, String compid, String vehicleId,
                                   String stoName, String supName, Long beginTime, Long endTime,
                                   @RequestParam(defaultValue = "10") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightClose(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 手机erp首页查询原材料采购
     *
     * @param compid    企业代号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param type      标识：type=1代表查询本月,否则查询当天
     */
    @ApiOperation("手机erp首页查询原材料采购")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "标识：type=1代表查询本月,否则查询当天",
                    dataType = "int", paramType = "query"),
    })
    @PostMapping("/getWeightByMatParent")
    public ResultVO getWeightByMatParent(String compid,
                                         Long beginTime,
                                         Long endTime, Integer type) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        List<WeightMatParentNameVO> weightByMatParent = stockInServer.getWeightByMatParent(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type);
        return ResultVO.create(weightByMatParent);
    }


    /**
     * 材料统计中按照材料名称查询的柱状图
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param stoName   入库库位
     * @param matType   材料分类  1:骨料  2：粉料  3：外加剂
     */
    @ApiOperation("材料统计中按照材料名称查询的柱状图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "matType", value = "材料分类  1:骨料  2：粉料  3：外加剂",
                    dataType = "int", paramType = "query"),
    })
    @PostMapping("/getHistogramByMat")
    public ResultVO getHistogramByMat(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "1") Integer matType) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getHistogramByMat(compid, empName, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), matType));
    }


    /**
     * 材料统计中按照供应商查询的饼状图
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("材料统计中按照供应商查询的饼状图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
    })
    @PostMapping("/getPieChartBySupName")
    public ResultVO getPieChartBySupName(String empName, String compid, String vehicleId,
                                         String stoName, String supName, Long beginTime, Long endTime) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

        return ResultVO.create(stockInServer.getPieChartBySupName(compid, empName, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime))));
    }


    /**
     * 材料统计中按照入库库位查询的柱状图
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName   供货商
     * @param empName   过磅员
     * @param stoName   入库库位
     * @return 原材料统计汇总
     */
    @ApiOperation("材料统计中按照入库库位查询的柱状图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "过磅员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车辆代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stoName", value = "入库库位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supName", value = "供货商", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
    })
    @PostMapping("/getHistogramByStoName")
    public ResultVO getHistogramByStoName(String empName, String compid, String vehicleId,
                                          String stoName, String supName, Long beginTime, Long endTime) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getHistogramByStoName(compid, empName, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime))));
    }

    /**
     * 通过 compid  stICode 查询材料过磅
     *
     * @param compid  公司id
     * @param stICode 过磅单号
     */
    @ApiOperation("材料检测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stICode", value = "过磅单号", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getStockCheck")
    public ResultVO getStockCheck(String compid, String stICode) {

        return ResultVO.create(stockInServer.getStockCheck(compid, stICode));
    }

    /**
     * 更新检验状态
     *
     * @param compid      公司id
     * @param deductNum   另扣量
     * @param stICode     过磅单号
     * @param isPassOrNot 是否合格 1合格 0不合格
     * @param picturePath 图片路径
     * @param matCode     材料编码
     * @param stkCode     库位编码
     * @param isAllowOut  是否允许出厂过磅:0不允许 1允许
     */
    @ApiOperation("保存材料检测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stICode", value = "过磅单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deductNum", value = "另扣量", dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "isPassOrNot", value = "是否合格 1合格 0不合格",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "picturePath", value = "图片路径", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "matCode", value = "材料代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stkCode", value = "库位代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "notReason", value = "不合格原因", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isAllowOut", value = "是否允许出厂过磅:0不允许 1允许",
                    dataType = "Int", paramType = "query"),
    })
    @PostMapping("/updateCheckStatus")
    public ResultVO updateCheckStatus(String compid, BigDecimal deductNum, String stICode,
                                      @RequestParam(defaultValue = "1") int isPassOrNot, String picturePath,
                                      String matCode, String stkCode, String notReason,
                                      @RequestParam(defaultValue = "0") int isAllowOut,
                                      HttpServletRequest request) throws ErpException {
        String token = request.getHeader("token");
        stockInServer.updateCheckStatus(token, compid, deductNum, stICode, isPassOrNot, picturePath, matCode, stkCode,
                notReason, isAllowOut);
        return ResultVO.create();
    }

    /**
     * 上传照片
     *
     * @param compid  公司ID
     * @param stICode 过磅单号
     * @param image   图片
     * @return 图片路径
     * @throws ErpException 异常
     */
    @ApiOperation("材料检测图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stICode", value = "过磅单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "image", value = "上传的图片", required = true, dataType = "file", paramType = "query")
    })
    @PostMapping("/uploadPicture")
    public com.hntxrj.txerp.core.web.ResultVO uploadPicture(String compid, String stICode,
                                                            MultipartFile image) throws ErpException {
        return com.hntxrj.txerp.core.web.ResultVO.create(stockInServer.uploadCheckingImg(compid,
                stICode, image));
    }

    /**
     * 删除照片
     *
     * @param compid  公司ID
     * @param stICode 过磅单号
     * @param image   图片路径
     * @return 结果
     */
    @ApiOperation("删除材料检测图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stICode", value = "过磅单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "image", value = "删除的图片名", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/deletePicture")
    public com.hntxrj.txerp.core.web.ResultVO deletePicture(String compid, String stICode, String image) {
        return com.hntxrj.txerp.core.web.ResultVO.create(stockInServer.deleteCheckingImg(compid, stICode,
                image));
    }

    /**
     * 图片展示
     *
     * @param fileName 文件名称
     * @throws ErpException 异常处理
     */
    @ApiOperation("老图片展示（弃用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "图片名称", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/downloadPicture")
    public void downloadPicture(String fileName, HttpServletResponse response) throws ErpException {
        stockInServer.downloadPicture(fileName, response);
    }

    /**
     * 新图片展示
     *
     * @param fileName 文件名称
     * @param compid   公司ID
     * @throws ErpException 异常处理
     */
    @ApiOperation("新图片展示（http直接发送get请求，返回图片资源）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "图片名称", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/showPicture")
    public void showPicture(String fileName, String compid, HttpServletResponse response) throws ErpException {
        stockInServer.showPicture(fileName, compid, response);
    }

    /**
     * 根据公司ID获取材料信息
     *
     * @param compid      公司ID
     * @param searchWords 搜索关键字
     * @param page        当前页
     * @param pageSize    分页大小
     * @return 数据结果
     */
    @ApiOperation("获取材料名称下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchWords", value = "搜索关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer", paramType = "query"),
    })
    @PostMapping("/getMatByComId")
    public ResultVO getMatByComId(String compid, String searchWords,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInServer.getMatByComId(compid, searchWords, page, pageSize));

    }

    /**
     * 根据公司ID获取库存
     *
     * @param compid      公司ID
     * @param searchWords 搜索关键字
     * @param page        当前页
     * @param pageSize    分页大小
     *                    * @return 结果集
     */

    @ApiOperation("获取库位下拉（前面加线号）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchWords", value = "搜索关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer", paramType = "query"),
    })
    @PostMapping("/getStockByComId")
    public ResultVO getStockByComId(String compid, String searchWords,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInServer.getStockByComId(compid, searchWords, page, pageSize));

    }


}
