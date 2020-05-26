package com.hntxrj.txerp.mapper;


import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {

    List<Map<String, Object>> getStockAll(String compid);

    // 根据用户的设置获取实时库存
    List<StockVO> getStockByStirId(String compid, Integer stirId, Integer stockAggregateIsShow);

    List<PublicStockVO> getPublicStockByStirId(String compid, Integer stirId);


    /**
     * 获取所有线号
     */
    List<StirIdVO> getStirIds(String compid);


    /**
     * /*原材料过磅统计  材料名称
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightMatNameVO> getWeightByMat(String empName, String compid, String vehicleId, String stoName,
                                         String supName, String beginTime, String endTime, Integer page,
                                         Integer pageSize);


    /**
     * /*原材料过磅统计  车辆代号
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightVechicIdVO> getWeightByVehicleId(String empName, String compid, String vehicleId, String stoName,
                                                String supName, String beginTime, String endTime,
                                                Integer page, Integer pageSize);

    /**
     * /*原材料过磅统计。供应商名（老版本）
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightSupNameVO> getWeightByStoName(String empName, String compid, String vehicleId, String stoName,
                                             String supName, String beginTime, String endTime,
                                             Integer page, Integer pageSize);


    /**
     * /*原材料过磅统计。供应商名(新版本)
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightSupNameVO> getWeightByStoNameNew(String empName, String compid, String vehicleId, String stoName,
                                                String supName, String beginTime, String endTime,
                                                Integer page, Integer pageSize);

    /**
     * /*原材料过磅统计。入库库位
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightStorageVO> getWeightBySupName(String empName, String compid, String vehicleId, String stoName,
                                             String supName, String beginTime, String endTime,
                                             Integer page, Integer pageSize);

    /**
     * /*原材料过磅统计。过磅员
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightEmpNameVO> getWeightByEmpName(String empName, String compid, String vehicleId, String stoName,
                                             String supName, String beginTime, String endTime,
                                             Integer page, Integer pageSize);

    /**
     * /*原材料过磅统计。综合信息
     *
     * @param empName   过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName   入库库位
     * @param supName   供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */

    List<WeightSynthesizeVO> getSynthesizeByMat(String empName, String compid, String vehicleId, String stoName,
                                                String supName, String beginTime, String endTime,
                                                Integer page, Integer pageSize);

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
    List<WeightCloseVO> getWeightClose(String empName, String compid, String vehicleId,
                                       String stoName, String supName, String beginTime, String endTime,
                                       Integer page, Integer pageSize);


    /**
     * 原材料过磅统计（根据材料名称统计）
     */
    List<WeightMatParentNameVO> getWeightByMatParent(String compid, String beginTime, String endTime);

    /**
     * 材料统计中的饼状图
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
    List<WeightChartVO> getPieChartBySupName(String compid, String empName, String vehicleId, String stoName,
                                             String supName, String beginTime, String endTime);


    /**
     * 材料统计中按照库位查询的柱状图
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
    List<WeightChartVO> getHistogramByStoName(String compid, String empName, String vehicleId, String stoName,
                                              String supName, String beginTime, String endTime);


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
     * @param matType   材料分类  1:骨料  2：粉料  3：其他
     */
    List<WeightChartVO> getHistogramByMat(String compid, String empName, String vehicleId, String stoName,
                                          String supName, String beginTime, String endTime, Integer matType);

    /**
     * 获取所有库位下拉集合
     *
     * @param compid  企业id
     * @param stoName 库位名称
     */
    List<StockVO> getStockList(String compid, String stoName);

    /**
     * 根据供应商查询此供应商供应的材料
     *
     * @param compid    企业id
     * @param supCode   供应商代号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<WeightMatParentNameVO> getMatNameBySupName(String compid, String supCode, String beginTime, String endTime);

    /**
     * 更新检验状态
     *
     * @param compid         公司id
     * @param deductNum      另扣量
     * @param stICode        过磅单号
     * @param isPassOrNot    是否合格
     * @param picturePath    图片路径
     * @param matCode        材料编码
     * @param stkCode        库位编码
     * @param inspector      检测人
     * @param inspectionTime 检测时间
     */
    void updateCheckStatus(String compid, BigDecimal deductNum, String stICode, int isPassOrNot, String picturePath,
                           String matCode, String stkCode, String notReason, String inspector, Date inspectionTime);

    /**
     * 根据公司ID获取库存
     *
     * @param compid 公司ID
     * @return 结果集列表
     */
    List<MaterialVO> getMatByComId(String compid, String searchWords);

    /**
     * 根据公司ID获取库存
     *
     * @param compid 公司ID
     * @return 结果集列表
     */
    List<StockVO> getStockByComId(String compid, String searchWords);

    /**
     * 通过 compid  stICode 查询材料过磅
     *
     * @param compid  公司id
     * @param stICode 过磅单号
     */
    StockInCheckVO getStockCheck(String compid, String stICode);

    /**
     * 根据线号获取所有的库位信息
     */
    List<StockSelectVO> getAllStockList(String compid, Integer stirId);

}
