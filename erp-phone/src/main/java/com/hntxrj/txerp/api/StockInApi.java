package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.StockInSelectService;
import com.hntxrj.txerp.server.StockInServer;
import com.hntxrj.txerp.server.StockInCollectService;
import com.hntxrj.txerp.vo.ResultVO;
import com.hntxrj.txerp.vo.WeightMatParentNameVO;
import io.swagger.annotations.Api;
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
    @PostMapping("/getStockInDetails")
    public ResultVO getStockInDetails(String matName, String vehicleId, String supName, String compid, Long beginTime,
                                      Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize, String saleType) {


        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getStockInList")
    public ResultVO getStockInList(String matName, String vehicleId, String supName, String compid, Long beginTime,
                                   Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize, String saleType,
                                   Integer isPassOrNot) {


        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getStockInSelectClose")
    public ResultVO getStockInSelectClose(String matName, String vehicleId, String supName,
                                          String compid, Long beginTime, Long endTime,
                                          Integer page, Integer pageSize, String saleType) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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

    @PostMapping("/getSupName")
    public ResultVO getSupName(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getSupName(supName, compid, page, pageSize));
    }

    /**
     * 获取库位一共多少种
     */
    @PostMapping("/getStoNames")
    public ResultVO getStoNames(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getStoNames(supName, compid, page, pageSize));
    }

    /**
     * 获取业务类别一共多少种
     */
    @PostMapping("/getTypeName")
    public ResultVO getTypeName(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")
            Integer pageSize) {
        return ResultVO.create(stockInService.getTypeName(page, pageSize));

    }

    /**
     * 获取材料名称
     */
    @PostMapping("/getMaterialName")
    public ResultVO getMaterialName(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getMaterialName(supName, compid, page, pageSize));

    }

    /**
     * 获取过磅员信息
     */
    @PostMapping("/getOverweightMation")
    public ResultVO getOverweightMation(String supName, String compid, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInService.getOverweightMation(supName, compid, page, pageSize));

    }

    /**
     * 获取车辆号码信息
     */
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
    @PostMapping("/getMatDetailsList")
    public ResultVO getMatDetailsList(String vehicleId, String supName, String matSpecs, String compid, Long beginTime,
                                      Long endTime, @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize, String matName) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getStockInCollectClose")
    public ResultVO getStockInCollectClose(String vehicleId, String supName, String matSpecs, String compid,
                                           Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getMatStatistics")
    public ResultVO getMatStatistics(String vehicleId, String supName, String matSpecs, String compid,
                                     Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getMatStatisticsClose")
    public ResultVO getMatStatisticsClose(String vehicleId, String supName, String matSpecs, String compid,
                                          Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getMaterialCount")
    public ResultVO getMaterialCount(String vehicleId, String supName, String matSpecs, String compid,
                                     Long beginTime, Long endTime,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize, String MatName) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightByMat")
    public ResultVO getWeighByMat(String empName, String compid, String vehicleId,
                                  String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                  Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightByVechicId")
    public ResultVO getWeighByVechicId(String empName, String compid, String vehicleId,
                                       String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                       Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightByStoName")
    public ResultVO getWeighByStoName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "0") String isNewVersion,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightBySupName")
    public ResultVO getWeighBySupName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                      Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightByEmpName")
    public ResultVO getWeighByEmpName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                      Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getSynthesizeByMat")
    public ResultVO getSynthesizeByMat(String empName, String compid, String vehicleId,
                                       String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                       Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getWeightClose")
    public ResultVO getWeightClose(String empName, String compid, String vehicleId,
                                   String stoName, String supName, Long beginTime, Long endTime, Integer page,
                                   Integer pageSize) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
     * @param type      标识：type=1代表查询本月
     */
    @PostMapping("/getWeightByMatParent")
    public ResultVO getWeightByMatParent(String compid,
                                         Long beginTime,
                                         Long endTime, Integer type) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getHistogramByMat")
    public ResultVO getHistogramByMat(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "1") Integer matType) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
    @PostMapping("/getPieChartBySupName")
    public ResultVO getPieChartBySupName(String empName, String compid, String vehicleId,
                                         String stoName, String supName, Long beginTime, Long endTime) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

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
    @PostMapping("/getHistogramByStoName")
    public ResultVO getHistogramByStoName(String empName, String compid, String vehicleId,
                                          String stoName, String supName, Long beginTime, Long endTime) {
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

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
    @ApiOperation(value = "通过 compid  stICode 查询材料过磅", httpMethod = "get", notes = "compid 公司ID|stICode 过磅单号")
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
     */
    @ApiOperation(value = "更新检验状态", httpMethod = "get", notes = "compid 公司ID|stICode 过磅单号|" +
            "isPassOrNot 是否合格 1合格 0不合格|picturePath 图片路径|matCode 材料编码|stkCode 库位编码")
    @PostMapping("/updateCheckStatus")
    public ResultVO updateCheckStatus(String compid, BigDecimal deductNum, String stICode,
                                      @RequestParam(defaultValue = "1") int isPassOrNot, String picturePath,
                                      String matCode, String stkCode, String notReason,
                                      HttpServletRequest request) throws ErpException {
        String token = request.getHeader("token");
        stockInServer.updateCheckStatus(token, compid, deductNum, stICode, isPassOrNot, picturePath, matCode, stkCode,
                notReason);
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
    @ApiOperation(value = "上传照片", httpMethod = "get", notes = "" +
            "compid 公司ID|stICode 过磅单号|image 图片")
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
    @ApiOperation(value = "删除照片", httpMethod = "get", notes = "" +
            "compid 公司ID|stICode 过磅单号|image 图片路径")
    @PostMapping("/deletePicture")
    public com.hntxrj.txerp.core.web.ResultVO deletePicture(String compid, String stICode,
                                                            String image) {
        return com.hntxrj.txerp.core.web.ResultVO.create(stockInServer.deleteCheckingImg(compid, stICode,
                image));
    }

    /**
     * 图片展示
     *
     * @param fileName 文件名称
     * @throws ErpException 异常处理
     */
    @ApiOperation(value = "图片展示", httpMethod = "get", notes = "" +
            "fileName 文件名称")
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
    @ApiOperation(value = "图片展示", httpMethod = "get", notes = "" +
            "fileName 文件名称")
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
    @ApiOperation(value = "根据公司ID获取材料信息", httpMethod = "post", notes = "" +
            "searchWords 搜索关键字|compid 公司ID")
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

    @ApiOperation(value = "根据公司ID获取库存", httpMethod = "post", notes = "" +
            "searchWords 搜索关键字|compid 公司ID")
    @PostMapping("/getStockByComId")
    public ResultVO getStockByComId(String compid, String searchWords,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stockInServer.getStockByComId(compid, searchWords, page, pageSize));

    }


}
