package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;

public interface StockInCollectService {
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
    PageVO<StockInCollectVO> getMatDetailsList(String vehicleId, String supName, String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName);

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
    PageVO<StockInCollectCloseVO> getStockInCollectClose(String vehicleId, String supName, String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName);

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
    PageVO<StockInMatStatisticsVO> getMatStatistics(String vehicleId, String supName, String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName);

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
    PageVO<StockInMatStatisticsCloseVO> getMatStatisticsClose(String vehicleId, String supName, String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName);

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
    PageVO<MaterialCountVO> getMaterialCount(String vehicleId, String supName,String matSpecs, String compid,
                                             String beginTime, String endTime, Integer page,
                                             Integer pageSize, String MatName);
}
