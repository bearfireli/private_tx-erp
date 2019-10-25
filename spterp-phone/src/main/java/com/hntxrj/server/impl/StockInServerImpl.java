package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.dao.StockInDao;
import com.hntxrj.mapper.StockMapper;
import com.hntxrj.server.StockInServer;
import com.hntxrj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Scope("prototype")
public class StockInServerImpl implements StockInServer {
    private final StockInDao stockInDao;
    private final StockMapper stockInWeighmatNsMapper;
    @Autowired
    public StockInServerImpl(StockInDao stockInDao,StockMapper stockInWeighmatNsMapper) {
        this.stockInDao = stockInDao;
        this.stockInWeighmatNsMapper = stockInWeighmatNsMapper;
    }

    /**
     * 材料过磅统计
     * @param mark
     * @param sup_linkMan  联系人
     * @param mu 母公司
     * @param empName  过磅员
     * @param vehicleID 车号
     * @param for_name 运输商
     * @param matNS 入库库位
     * @param matNS 材料名称
     * @param sup_name 供货商
     * @param stI_status 记录状态
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param compid 当前登录用户的企业
     * @param opid  登录用户
     * @param currPage 当前页
     * @param pageSize 页长度
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerysql(String mark, String sup_linkMan, String mu, String empName, String vehicleID, String for_name, String sto_name, String matNS, String sup_name, Boolean stI_status, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize) {
        return stockInDao.sp_V_MP_StockInQuerysql(  mark,   sup_linkMan,   mu,   empName,   vehicleID,   for_name,   sto_name,   matNS,   sup_name,   stI_status,   beginTime,   endTime,   compid,   opid,   currPage,   pageSize);
    }

    /**
     * srqq 材料过磅  下拉
     * @param id  1 联系人下拉 MP_Supplier   2  过磅员查询 User_employee 3 运输商查询      这个表  MP_Forwarder 4 入库库位  MP_Stock 5 材料名称    V_MatCodeToName 6 供货商名称 MP_Supplier
     * @param sqlwhere 查询条件
     * @param compid
     *@param opid @return json
     */
    @Override
    public JSONArray sp_query_downsrq(String id, String sqlwhere, String compid, String opid,Integer PageSize, Integer currPage) {
        return stockInDao.sp_query_downsrq(id, sqlwhere, compid, opid, PageSize, currPage);


    }

    /**
     *  srqq  原材料过磅查询详情
     * @param mark  1 list表     2 详情
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param  compid    当前登录用户的企业
     * @param opid  登录用户
     * @param currPage
     * @param pageSize
     * @param sup_name   供货商
     * @param for_name  运输商
     * @param empName  过磅员
     * @param sto_name  入库库位
     * @param matNS   材料名称
     * @param vehicleID   车号
     * @param saletype  业务类别
     * @param stI_status 记录状态
     * @return  json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerylistsrq(String mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String sup_name, String for_name, String empName, String sto_name, String matNS, String vehicleID, Integer saletype, Integer stI_status,String sticode) {
        return stockInDao.sp_V_MP_StockInQuerylistsrq(  mark,   beginTime,   endTime,   compid,   opid,   currPage,   pageSize,   sup_name,   for_name,   empName,   sto_name,   matNS,   vehicleID,   saletype,   stI_status,sticode);
    }


    /**
     * 原材料统计汇总
     * @param mark 1原材林明细汇总  2原材林汇总统计  3 材料入库汇总   4 实时库存 查询
     * @param beginTime
     * @param endTime
     * @param compid
     * @param opid
     * @param currPage
     * @param pageSize
     * @param matname   材料名称
     * @param sup_name   供货商名称
     * @param vehicleID  车号
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQueryManNsrq(String mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String matname, String sup_name, String vehicleID) {
        return stockInDao.sp_V_MP_StockInQueryManNsrq(  mark,   beginTime,   endTime,   compid,   opid,   currPage,   pageSize,   matname,   sup_name,   vehicleID);
    }

    /**
     *srqq 搅拌车过磅查询
     * @param mark 1列表
     * @param beginTime  开始时间
     * @param endTime 结束时间
     * @param compid  企业
     * @param opid 用户
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param empname   过磅员
     * @param weightType  过磅类别
     * @param vehicleID 车号
     * @param eppname 业务员
     * @return
     */
    @Override
    public JSONArray sp_Query_VM_VehicleWeightListsrq(Integer mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String empname, Integer weightType, String vehicleID, String eppname) {
        return stockInDao.sp_Query_VM_VehicleWeightListsrq(mark,beginTime,endTime,compid,opid,currPage,pageSize,empname,weightType,vehicleID,eppname);
    }
















    /**
     /*原材料过磅统计  材料名称
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
    @Override
    public PageVO<WeightMatNameVO> getWeightByMat(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightMatNameVO> stockInWeighmatNsVOS = stockInWeighmatNsMapper.getWeightByMat(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightMatNameVO raw : stockInWeighmatNsVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);
            raw.setProportion(Double.valueOf(raw.getProportion()/1000));
        /*    if(raw.getProportion()!=0){
             String getProportion = String.valueOf(raw.getProportion());
             getProportion = getProportion.substring(0,getProportion.indexOf(".") +3);
             raw.setProportion(Double.parseDouble(getProportion));
            }*/

        }
        PageInfo<WeightMatNameVO> pageInfo = new PageInfo<>(stockInWeighmatNsVOS);
        PageVO<WeightMatNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     /*原材料过磅统计  车辆代号
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
    @Override
    public PageVO<WeightVechicIdVO> getWeightByVechicId(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightVechicIdVO> stockInWeighVechicIdVOS = stockInWeighmatNsMapper.getWeightByVechicId(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightVechicIdVO raw : stockInWeighVechicIdVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);
            raw.setProportion(Double.valueOf(raw.getProportion()/1000));
          /*  if(raw.getProportion()!=0){
                String getProportion = String.valueOf(raw.getProportion());
                getProportion = getProportion.substring(0,getProportion.indexOf(".") +3);
                raw.setProportion(Double.parseDouble(getProportion));
            }*/
        }
        PageInfo<WeightVechicIdVO> pageInfo = new PageInfo<>(stockInWeighVechicIdVOS);
        PageVO<WeightVechicIdVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
    /**
     /*原材料过磅统计。供应商名
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
    @Override
    public PageVO<WeightSupNameVO> getWeightByStoName(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightSupNameVO> stockInWeighSupNameVOS = stockInWeighmatNsMapper.getWeightByStoName(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightSupNameVO raw : stockInWeighSupNameVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);
            raw.setProportion(Double.valueOf(raw.getProportion()/1000));
            /*if(raw.getProportion()!=0){
                String getProportion = String.valueOf(raw.getProportion());
                getProportion = getProportion.substring(0,getProportion.indexOf(".") +3);
                raw.setProportion(Double.parseDouble(getProportion));
            }*/
        }
        PageInfo<WeightSupNameVO> pageInfo = new PageInfo<>(stockInWeighSupNameVOS);
        PageVO<WeightSupNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
    /**
     /*原材料过磅统计。入库库位
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
    @Override
    public PageVO<WeightStorageVO> getWeightBySupName(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightStorageVO> stockInWeighStorageVOS = stockInWeighmatNsMapper.getWeightBySupName(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightStorageVO raw : stockInWeighStorageVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);
            raw.setProportion(Double.valueOf(raw.getProportion()/1000));
            /*if(raw.getProportion()!=0){
                String getProportion = String.valueOf(raw.getProportion());
                getProportion = getProportion.substring(0,getProportion.indexOf(".") +3);
                raw.setProportion(Double.parseDouble(getProportion));
            }*/

        }
        PageInfo<WeightStorageVO> pageInfo = new PageInfo<>(stockInWeighStorageVOS);
        PageVO<WeightStorageVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     /*原材料过磅统计。过磅员
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
    @Override
    public PageVO<WeightEmpNameVO> getWeightByEmpName(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightEmpNameVO> stockInWeighEmpNameVOS = stockInWeighmatNsMapper.getWeightByEmpName(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightEmpNameVO raw : stockInWeighEmpNameVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);
            raw.setProportion(Double.valueOf(raw.getProportion()/1000));
           /* if(raw.getProportion()!=0){
                String getProportion = String.valueOf(raw.getProportion());
                getProportion = getProportion.substring(0,getProportion.indexOf(".") +3);
                raw.setProportion(Double.parseDouble(getProportion));
            }*/
        }
        PageInfo<WeightEmpNameVO> pageInfo = new PageInfo<>(stockInWeighEmpNameVOS);
        PageVO<WeightEmpNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     /*原材料过磅统计。综合信息
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

    @Override
    public PageVO<WeightSynthesizeVO> getSynthesizeByMat(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightSynthesizeVO> stockInWeighmessageVOS = stockInWeighmatNsMapper.getSynthesizeByMat(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        for (WeightSynthesizeVO raw : stockInWeighmessageVOS) {
            raw.setTlWeight(Double.valueOf(raw.getTlWeight())/1000);


        }

        PageInfo<WeightSynthesizeVO> pageInfo = new PageInfo<>(stockInWeighmessageVOS);
        PageVO<WeightSynthesizeVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
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
    @Override
    public PageVO<WeightCloseVO> getWeightClose(String empName, String compid, String vehicleId, String stoName, String supName, String beginTime, String endTime, Integer page, Integer pageSize) {
        List<WeightCloseVO> weightCloseVOS = stockInWeighmatNsMapper.getWeightClose(empName,compid,vehicleId,stoName,supName,beginTime,endTime,page,pageSize);
        PageInfo<WeightCloseVO> pageInfo = new PageInfo<>(weightCloseVOS);
        PageVO<WeightCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public List<WeightMatParentNameVO> getWeightByMatParent(String compid, String beginTime, String endTime) {

        List<WeightMatParentNameVO> weightMatParentNameVOList = stockInWeighmatNsMapper.getWeightByMatParent(compid,beginTime,endTime);
        for (WeightMatParentNameVO weightMatParentNameVO : weightMatParentNameVOList) {
            weightMatParentNameVO.setTlWeight(Double.valueOf(weightMatParentNameVO.getTlWeight())/1000);
        }

        return weightMatParentNameVOList;
    }

}
