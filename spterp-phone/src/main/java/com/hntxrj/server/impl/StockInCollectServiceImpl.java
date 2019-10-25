package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.mapper.StockInCollectMapper;
import com.hntxrj.server.StockInCollectService;
import com.hntxrj.vo.*;
import com.hntxrj.vo.MaterialCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class StockInCollectServiceImpl implements StockInCollectService {
    private final StockInCollectMapper stoclinCollectMapper;

    @Autowired
    public StockInCollectServiceImpl(StockInCollectMapper stoclinCollectMapper) {
        this.stoclinCollectMapper = stoclinCollectMapper;
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

    @Override
    public PageVO<StockInCollectVO> getMatDetailsList(String vehicleId, String supName,String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName) {
        PageHelper.startPage(page, pageSize);
        List<StockInCollectVO> stockInCollectDetailVOS = stoclinCollectMapper.getMatDetailsList(vehicleId, supName, matSpecs,compid, beginTime, endTime, page, pageSize, MatName);
        for (StockInCollectVO raw : stockInCollectDetailVOS) {
            raw.setClWeight(Double.valueOf(raw.getClWeight()));

        }
        PageInfo<StockInCollectVO> pageInfo = new PageInfo<>(stockInCollectDetailVOS);
        PageVO<StockInCollectVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<StockInCollectCloseVO> getStockInCollectClose(String vehicleId, String supName,String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName) {
        List<StockInCollectCloseVO> stockInCollectCloseVOS = stoclinCollectMapper.getStockInCollectClose(vehicleId, supName, matSpecs,compid, beginTime, endTime, page, pageSize, MatName);
        PageInfo<StockInCollectCloseVO> pageInfo = new PageInfo<>(stockInCollectCloseVOS);
        PageVO<StockInCollectCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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

    @Override
    public PageVO<StockInMatStatisticsVO> getMatStatistics(String vehicleId, String supName,String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName) {
        PageHelper.startPage(page, pageSize);
        List<StockInMatStatisticsVO> stockInTabulateVOS = stoclinCollectMapper.getMatStatistics(vehicleId, supName,matSpecs, compid, beginTime, endTime, page, pageSize, MatName);
        for (StockInMatStatisticsVO raw : stockInTabulateVOS) {
            raw.setClWeight(Double.valueOf(raw.getClWeight()));

        }
        PageInfo<StockInMatStatisticsVO> pageInfo = new PageInfo<>(stockInTabulateVOS);
        PageVO<StockInMatStatisticsVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<StockInMatStatisticsCloseVO> getMatStatisticsClose(String vehicleId, String supName,String matSpecs, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String MatName) {
        List<StockInMatStatisticsCloseVO> stockInTabulateCloseVOS = stoclinCollectMapper.getMatStatisticsClose(vehicleId, supName, matSpecs,compid, beginTime, endTime, page, pageSize, MatName);
        PageInfo<StockInMatStatisticsCloseVO> pageInfo = new PageInfo<>(stockInTabulateCloseVOS);
        PageVO<StockInMatStatisticsCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<MaterialCountVO> getMaterialCount(String vehicleId, String supName,String matSpecs,
                                                    String compid, String beginTime,
                                                    String endTime, Integer page,
                                                    Integer pageSize, String MatName) {
        PageHelper.startPage(page, pageSize);
        List<MaterialCountVO> StockInTabulateCloseVOS =
                stoclinCollectMapper.getMaterialCount(vehicleId, supName,matSpecs, compid,
                        beginTime, endTime, MatName);
        for (MaterialCountVO raw : StockInTabulateCloseVOS) {
            raw.setClWeight(Double.valueOf(raw.getClWeight())/1000);
            raw.setConWeight(Double.valueOf(raw.getConWeight())/1000);
            raw.setStoCurqty(Double.valueOf(raw.getStoCurqty())/1000);

        }
        PageInfo<MaterialCountVO> pageInfo = new PageInfo<>(StockInTabulateCloseVOS);
        PageVO<MaterialCountVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
}
