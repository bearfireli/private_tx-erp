package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.StockInSelectService;
import com.hntxrj.txerp.server.StockInServer;
import com.hntxrj.txerp.server.StockInCollectService;
import com.hntxrj.txerp.vo.ResultVO;
import com.hntxrj.txerp.vo.WeightMatParentNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stockIn")
public class StockInApi {
    private final StockInSelectService stockInService;
    private final StockInCollectService stoclInCollectService;
    private final StockInServer stockInServer;

    @Autowired
    public StockInApi(StockInSelectService stockInService, StockInCollectService stoclInCollectService,
                      StockInServer stockInServer) {

        this.stockInService = stockInService;
        this.stoclInCollectService = stoclInCollectService;
        this.stockInServer = stockInServer;
    }

    /**
     * /*原材料过磅查询
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
    @PostMapping("/getStockInDetails")
    public ResultVO getStockInDetails(String matName, String vehicleId, String supName, String compid, Long beginTime,
                                      Long endTime, Integer page, Integer pageSize, String saleType) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInService.getStockInDetails(matName, vehicleId, supName, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, "".equals(saleType) ? null : saleType));
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInService.getStockInSelectClose(matName, vehicleId, supName, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, "".equals(saleType) ? null : saleType));
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
     * @param MatName   材料名称
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @PostMapping("/getMatDetailsList")
    public ResultVO getMatDetailsList(String vehicleId, String supName, String matSpecs, String compid, Long beginTime,
                                      Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stoclInCollectService.getMatDetailsList(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * /*原材料明细汇总合计入库量
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
    public ResultVO getStockInCollectClose(String vehicleId, String supName, String matSpecs, String compid, Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stoclInCollectService.getStockInCollectClose(vehicleId, supName, matSpecs, compid,
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
    public ResultVO getMatStatistics(String vehicleId, String supName, String matSpecs, String compid, Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stoclInCollectService.getMatStatistics(vehicleId, supName, matSpecs, compid,
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
    public ResultVO getMatStatisticsClose(String vehicleId, String supName, String matSpecs, String compid, Long beginTime, Long endTime, Integer page, Integer pageSize, String MatName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stoclInCollectService.getMatStatisticsClose(vehicleId, supName, matSpecs, compid,
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
    public ResultVO getMaterialCount(String vehicleId, String supName, String matSpecs, String compid, Long beginTime, Long endTime,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize, String MatName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stoclInCollectService.getMaterialCount(vehicleId, supName, matSpecs, compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize, MatName));
    }

    /**
     * /*原材料过磅统计  材料名称
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
                                  String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                       String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByVechicId(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * /*原材料过磅统计。供应商名
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
    @PostMapping("/getWeightByStoName")
    public ResultVO getWeighByStoName(String empName, String compid, String vehicleId,
                                      String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightByStoName(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
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
                                      String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                      String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                       String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                   String stoName, String supName, Long beginTime, Long endTime, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(stockInServer.getWeightClose(empName, compid, vehicleId, stoName, supName,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    @PostMapping("/getWeightByMatParent")
    public ResultVO getWeightByMatParent(String compid,
                                         Long beginTime,
                                         Long endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<WeightMatParentNameVO> weightByMatParent = stockInServer.getWeightByMatParent(compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)));
        return ResultVO.create(weightByMatParent);
    }
}