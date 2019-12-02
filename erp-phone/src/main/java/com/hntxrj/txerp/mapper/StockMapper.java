package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {

    List<Map<String, Object>> getStockAll(String compid);

    List<StockVO> getStockByStirId(String compid, Integer stirId);

    List<StockVO> getPublicStockByStirId(String compid, Integer stirId);

    List<StirIdVO> getStirIds(String compid);



    /**
     /*原材料过磅统计  材料名称
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightMatNameVO> getWeightByMat(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);


    /**
     /*原材料过磅统计  车辆代号
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightVechicIdVO> getWeightByVechicId(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。供应商名（老版本）
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightSupNameVO> getWeightByStoName(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);


    /**
     /*原材料过磅统计。供应商名(新版本)
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightSupNameVO> getWeightByStoNameNew(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。入库库位
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightStorageVO> getWeightBySupName(String empName, String compid, String vehicleId, String stoName, String supName,String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。过磅员
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    List<WeightEmpNameVO> getWeightByEmpName(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);

    /**
     /*原材料过磅统计。综合信息
     * @param empName    过磅员
     * @param compid    企业id
     * @param vehicleId 车号
     * @param stoName  入库库位
     * @param supName    供货商
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */

    List<WeightSynthesizeVO> getSynthesizeByMat(String empName, String compid, String vehicleId, String stoName, String supName,String beginTime, String endTime, Integer page, Integer pageSize);
    /**
     /*原材料过磅统计合计入库量
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param empName    过磅员
     * @param page      页数
     * @param pageSize  每页数量
     * @param stoName  入库库位
     * @return 原材料统计汇总
     */
    List<WeightCloseVO> getWeightClose(String empName, String compid, String vehicleId,
                                       String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize);


    List<WeightMatParentNameVO> getWeightByMatParent(String compid,String beginTime,String endTime);

    List<WeightChartVO> getPieChartBySupName(String compid, String empName, String vehicleId, String stoName, String supName, String beginTime, String endTime);

    List<WeightChartVO> getHistogramByStoName(String compid, String empName, String vehicleId, String stoName, String supName, String beginTime, String endTime);


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
    List<WeightChartVO> getHistogramByMat(String compid, String empName, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer matType);
}
