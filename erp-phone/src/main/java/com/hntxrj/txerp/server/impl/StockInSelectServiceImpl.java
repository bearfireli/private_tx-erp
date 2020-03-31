package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.StockInSelectMapper;
import com.hntxrj.txerp.server.StockInSelectService;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class StockInSelectServiceImpl implements StockInSelectService {
    private final StockInSelectMapper stockInMapper;

    @Autowired
    public StockInSelectServiceImpl(StockInSelectMapper stockInMapper) {
        this.stockInMapper = stockInMapper;

    }

    /**
     *原材料过磅查询
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @Override
    public PageVO<StockInSelectVO> getStockInDetails(String matName , String vehicleId, String supName, String compid, String beginTime, String endTime, Integer page, Integer pageSize, String saleType) {
        PageHelper.startPage(page, pageSize);
        List<StockInSelectVO> stockInDetailsVOS = stockInMapper.getStockInDetails(matName,vehicleId,supName,compid,beginTime,endTime,page,pageSize,saleType);
        PageInfo<StockInSelectVO> pageInfo = new PageInfo<>(stockInDetailsVOS);
        PageVO<StockInSelectVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
    /**
     /*原材料过磅查询结算重量
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param supName    供货商
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    @Override
    public PageVO<StockInSelectCloseVO> getStockInSelectClose(String matName , String vehicleId, String supName, String compid,
                                                              String beginTime, String endTime, Integer page,
                                                              Integer pageSize, String saleType) {
        List<StockInSelectCloseVO> stockInSelectCloseVOS = stockInMapper.getStockInSelectClose(matName,vehicleId,supName,compid,beginTime,endTime,page,pageSize,saleType);
        PageInfo<StockInSelectCloseVO> pageInfo = new PageInfo<>(stockInSelectCloseVOS);
        PageVO<StockInSelectCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     *原材料过磅修改语句
     * @param compid 站别代号
     * @param stICode   过磅单号
     * @param grWeight  毛重
     * @param deductNum 另扣量
     * @param taWeight  皮重
     * @param netWeight 净重
     * @param deduct 另扣率
     * @param clWeight 结算重量
     * @param remarks 备注
     * @return
     */
    public Integer updateStockInDate(String supCode,String stkCode,String saleType,String compid,String stICode,
                                     String grWeight,String deductNum,String taWeight,String netWeight,
                                     String deduct,String clWeight,String remarks,String firstTime,
                                     String secondTime,String supExFactory,String vehicleId,String matCode,
                                     String opid,String opids,String supOilNum,String stlTimes) {
        if (grWeight==null&&deductNum==null&&taWeight==null
                &&netWeight==null&&deduct==null&&clWeight==null
                &&remarks==null&&firstTime==null&&secondTime==null
                &&supExFactory==null&&supCode==null&&stkCode==null&&saleType==null&&vehicleId==null&&matCode==null&&
                opid==null&&opids==null&&supOilNum==null&&stlTimes==null){
            System.out.println("所有都为空");
            return null;
        }
        try {
            if (stICode==null){
                System.out.println("stiCODE+++++++是Null");
            }
            System.out.println("准备进行修改");
            System.out.println("supExFactory  :"+supExFactory);
            Integer rows=stockInMapper.updateStockInDate(supCode,stkCode,saleType,compid,stICode,grWeight,
                    deductNum,taWeight,netWeight,deduct,clWeight,remarks,firstTime,secondTime,supExFactory,
                    vehicleId,matCode,opid,opids,supOilNum,stlTimes);
            return rows;
        }catch (Exception e){
            try {
                e.printStackTrace();
                throw new ErpException(ErrEumn.ADJUNCT_UPDATE_ERROR);
            } catch (ErpException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取供货商一共多少种
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getSupName(String supName, String compid, Integer page,
                                                Integer pageSize) {
        System.out.println("supName :"+supName);
        List<ConsultSupplierVO> list=stockInMapper.getSupName(supName,compid,page,pageSize);
        PageInfo<ConsultSupplierVO> pageInfo=new PageInfo<>(list);
        PageVO<ConsultSupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return  pageVO;
    }
    /**
     * 获取库位一共多少种
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getStoNames(String supName, String compid, Integer page,
                                                 Integer pageSize) {
        List<ConsultSupplierVO> list=stockInMapper.getStoNames(supName,compid,page,pageSize);
        PageInfo<ConsultSupplierVO> pageInfo=new PageInfo<>(list);
        PageVO<ConsultSupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return  pageVO;
    }
    /**
     * 获取业务类别一共多少种
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getTypeName(Integer page,
                                                 Integer pageSize) {
        List<ConsultSupplierVO> list=stockInMapper.getTypeName(page,pageSize);
        PageInfo<ConsultSupplierVO> pageInfo=new PageInfo<>(list);
        PageVO<ConsultSupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return  pageVO;
    }

    /**
     * 获取材料名称一共多少种
     * @param supName
     * @param compid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getMaterialName(String supName, String compid, Integer page, Integer pageSize) {
        List<ConsultSupplierVO> list=stockInMapper.getMaterialName(supName,compid,page,pageSize);
        PageInfo<ConsultSupplierVO> pageInfo=new PageInfo<>(list);
        PageVO<ConsultSupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return  pageVO;
    }

    /**
     * 获取所有过磅员信息
     * @param supName
     * @param compid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getOverweightMation(String supName, String compid, Integer page, Integer pageSize) {
        List<ConsultSupplierVO> list=stockInMapper.getOverweightMation(supName,compid,page,pageSize);
        return  getEncapsulation(list);
    }

    /**
     * 获取所有车辆代号
     * @param supName
     * @param compid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<ConsultSupplierVO> getVehicleNumber(String supName, String compid, Integer page, Integer pageSize) {
        List<ConsultSupplierVO> list=stockInMapper.getVehicleNumber(supName, compid, page, pageSize);
        return getEncapsulation(list);
    }

    @Override
    public StockInSelectVO stockInListDetail(String stiCode, String compid) {
        return stockInMapper.stockInListDetail(stiCode, compid);
    }


    /**
     * 消除3行代码的重复
     * @param list
     * @return
     */
    private PageVO getEncapsulation(List list){
        PageInfo<ConsultSupplierVO> pageInfo=new PageInfo<>(list);
        PageVO<ConsultSupplierVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }




}
