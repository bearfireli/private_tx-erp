package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.ConsultSupplierVO;
import com.hntxrj.txerp.vo.StockInSelectCloseVO;
import com.hntxrj.txerp.vo.StockInSelectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockInSelectMapper {
    /**
     /*原材料过磅查询 （老版本  4.2.1+1.apk 之前版本）
     * @param vehicleId 车号
     * @param supName    供货商
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */

    List<StockInSelectVO> getStockInDetails(String matName , String vehicleId, String supName, String compid,
                                            String beginTime, String endTime, Integer page, Integer pageSize,
                                            String saleType);
    /**
     /*原材料过磅查询 （新版本  4.2.1+1.apk 之后版本）
     * @param vehicleId 车号
     * @param supName    供货商
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */

    List<StockInSelectVO> getStockInList(String matName , String vehicleId, String supName, String compid,
                                            String beginTime, String endTime, Integer page, Integer pageSize,
                                            String saleType);
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
    List<StockInSelectCloseVO> getStockInSelectClose(String matName , String vehicleId, String supName, String compid,
                                                     String beginTime, String endTime, Integer page, Integer pageSize,
                                                     String saleType);

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
    Integer updateStockInDate(String supCode,String stkCode,String saleType,String compid,String stICode,
                              String grWeight,String deductNum,String taWeight,String netWeight,
                              String deduct,String clWeight,String remarks,String firstTime,
                              String secondTime,String supExFactory,String vehicleId,String matCode,String opid,
                              String opids,String supOilNum,String stlTimes);

    /**
     * 获取供货商一共多少种
     * @return
     */
    List<ConsultSupplierVO> getSupName(String supName, String compid, Integer page,
                                       Integer pageSize);

    /**
     * 获取库位一共多少种
     * @return
     */
    List<ConsultSupplierVO> getStoNames(String supName, String compid, Integer page,
                                        Integer pageSize);

    /**
     * 获取业务类别一共多少种
     * @return
     */
    List<ConsultSupplierVO> getTypeName(Integer page,
                                        Integer pageSize);

    /**
     * 获取材料名称
     * @param supName
     * @param compid
     * @param page
     * @param pageSize
     * @return
     */
    List<ConsultSupplierVO> getMaterialName(String supName, String compid,
                                            Integer page, Integer pageSize);


    /**
     * 获取过磅员信息
     * @param supName 筛选信息
     * @param compid 站别代号
     * @param page
     * @param pageSize
     * @return
     */
    List<ConsultSupplierVO> getOverweightMation(String supName, String compid,
                                            Integer page, Integer pageSize);

    /**
     * 获取所有车辆代号
     * @param supName //模糊查询条件
     * @param compid  //站别代号
     * @param page     //当前页
     * @param pageSize  //分页的页数
     * @return
     */
    List<ConsultSupplierVO> getVehicleNumber(String supName, String compid,
                                             Integer page, Integer pageSize);

    StockInSelectVO stockInListDetail(String stiCode, String compid);
}
