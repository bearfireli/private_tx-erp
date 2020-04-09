package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.dao.StockInDao;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.server.StockInServer;
import com.hntxrj.txerp.vo.*;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Scope("prototype")
public class StockInServerImpl implements StockInServer {
    private final StockInDao stockInDao;
    private final StockMapper stockInWeighmatNsMapper;

    @Value("${app.checking.imgFilePath}")
    private String checkingImageFilePath;
    @Value("${app.cloud.host}")
    private String url;

    @Autowired
    public StockInServerImpl(StockInDao stockInDao, StockMapper stockInWeighmatNsMapper) {
        this.stockInDao = stockInDao;
        this.stockInWeighmatNsMapper = stockInWeighmatNsMapper;
    }

    /**
     * 材料过磅统计
     *
     * @param mark        备注
     * @param sup_linkMan 联系人
     * @param mu          母公司
     * @param empName     过磅员
     * @param vehicleID   车号
     * @param for_name    运输商
     * @param matNS       入库库位
     * @param sup_name    供货商
     * @param stI_status  记录状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param compid      当前登录用户的企业
     * @param opid        登录用户
     * @param currPage    当前页
     * @param pageSize    页长度
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerysql(String mark, String sup_linkMan, String mu, String empName,
                                             String vehicleID, String for_name, String sto_name, String matNS,
                                             String sup_name, Boolean stI_status, String beginTime,
                                             String endTime, String compid, String opid, Integer currPage,
                                             Integer pageSize) {
        return stockInDao.sp_V_MP_StockInQuerysql(mark, sup_linkMan, mu, empName, vehicleID, for_name, sto_name,
                matNS, sup_name, stI_status, beginTime, endTime, compid, opid, currPage, pageSize);
    }

    /**
     * srqq 材料过磅  下拉
     *
     * @param id       1 联系人下拉 MP_Supplier   2  过磅员查询 User_employee 3 运输商查询
     *                 这个表  MP_Forwarder 4 入库库位  MP_Stock 5 材料名称    V_MatCodeToName 6 供货商名称 MP_Supplier
     * @param sqlwhere 查询条件
     * @param compid   企业id
     * @param opid     @return json
     */
    @Override
    public JSONArray sp_query_downsrq(String id, String sqlwhere, String compid, String opid, Integer PageSize,
                                      Integer currPage) {
        return stockInDao.sp_query_downsrq(id, sqlwhere, compid, opid, PageSize, currPage);


    }

    /**
     * srqq  原材料过磅查询详情
     *
     * @param mark       1 list表     2 详情
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param compid     当前登录用户的企业
     * @param opid       登录用户
     * @param currPage   当前页
     * @param pageSize   每页大小
     * @param sup_name   供货商
     * @param for_name   运输商
     * @param empName    过磅员
     * @param sto_name   入库库位
     * @param matNS      材料名称
     * @param vehicleID  车号
     * @param saletype   业务类别
     * @param stI_status 记录状态
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerylistsrq(String mark, String beginTime, String endTime, String compid,
                                                 String opid, Integer currPage, Integer pageSize, String sup_name,
                                                 String for_name, String empName, String sto_name, String matNS,
                                                 String vehicleID, Integer saletype, Integer stI_status,
                                                 String sticode) {
        return stockInDao.sp_V_MP_StockInQuerylistsrq(mark, beginTime, endTime, compid, opid, currPage,
                pageSize, sup_name, for_name, empName, sto_name, matNS, vehicleID, saletype, stI_status, sticode);
    }


    /**
     * 原材料统计汇总
     *
     * @param mark      1原材林明细汇总  2原材林汇总统计  3 材料入库汇总   4 实时库存 查询
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param compid    企业id
     * @param opid      操作员id
     * @param currPage  当前页
     * @param pageSize  每页数量
     * @param matname   材料名称
     * @param sup_name  供货商名称
     * @param vehicleID 车号
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQueryManNsrq(String mark, String beginTime, String endTime, String compid,
                                                 String opid, Integer currPage, Integer pageSize, String matname,
                                                 String sup_name, String vehicleID) {
        return stockInDao.sp_V_MP_StockInQueryManNsrq(mark, beginTime, endTime, compid, opid, currPage, pageSize,
                matname, sup_name, vehicleID);
    }

    /**
     * srqq 搅拌车过磅查询
     *
     * @param mark       1列表
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param compid     企业
     * @param opid       用户
     * @param currPage   当前页
     * @param pageSize   页长度
     * @param empname    过磅员
     * @param weightType 过磅类别
     * @param vehicleID  车号
     * @param eppname    业务员
     */
    @Override
    public JSONArray sp_Query_VM_VehicleWeightListsrq(Integer mark, String beginTime, String endTime, String compid,
                                                      String opid, Integer currPage, Integer pageSize, String empname,
                                                      Integer weightType, String vehicleID, String eppname) {
        return stockInDao.sp_Query_VM_VehicleWeightListsrq(mark, beginTime, endTime, compid, opid, currPage, pageSize,
                empname, weightType, vehicleID, eppname);
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
    @Override
    public PageVO<WeightMatNameVO> getWeightByMat(String empName, String compid, String vehicleId,
                                                  String stoName, String supName, String beginTime,
                                                  String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightMatNameVO> stockInWeighmatNsVOS = stockInWeighmatNsMapper.getWeightByMat(empName, compid, vehicleId,
                stoName, supName, beginTime, endTime, page, pageSize);
        for (WeightMatNameVO raw : stockInWeighmatNsVOS) {
            if (!"G".equals(raw.getMatParentCode()) && !"S".equals(raw.getMatParentCode())) {
                raw.setProportion(0.0);
            }

        }
        PageInfo<WeightMatNameVO> pageInfo = new PageInfo<>(stockInWeighmatNsVOS);
        PageVO<WeightMatNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<WeightVechicIdVO> getWeightByVehicleId(String empName, String compid, String vehicleId,
                                                         String stoName, String supName, String beginTime,
                                                         String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightVechicIdVO> stockInWeighVehicleIdVOS = stockInWeighmatNsMapper.getWeightByVehicleId(empName,
                compid, vehicleId, stoName, supName, beginTime, endTime, page, pageSize);
        PageInfo<WeightVechicIdVO> pageInfo = new PageInfo<>(stockInWeighVehicleIdVOS);
        PageVO<WeightVechicIdVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<WeightSupNameVO> getWeightByStoName(String empName, String compid, String vehicleId,
                                                      String stoName, String supName, String isNewVersion,
                                                      String beginTime, String endTime, Integer page,
                                                      Integer pageSize) {
        List<WeightSupNameVO> stockInWeighSupNameVOS;
        if ("1".equals(isNewVersion)) {
            //说明是新版本材料统计
            PageHelper.startPage(page, pageSize);
            stockInWeighSupNameVOS = stockInWeighmatNsMapper.getWeightByStoNameNew(empName, compid, vehicleId,
                    stoName, supName, beginTime, endTime, page, pageSize);
            //根据供应商名称查询此供应商供应的材料名称
            for (WeightSupNameVO stockInWeighSupNameVO : stockInWeighSupNameVOS) {
                List<WeightMatParentNameVO> matNameBySupName = stockInWeighmatNsMapper.getMatNameBySupName(compid,
                        stockInWeighSupNameVO.getSupCode(),
                        beginTime, endTime);
                stockInWeighSupNameVO.setMatNameVOList(matNameBySupName);
            }
        } else {
            PageHelper.startPage(page, pageSize);
            stockInWeighSupNameVOS = stockInWeighmatNsMapper.getWeightByStoName(empName, compid, vehicleId,
                    stoName, supName, beginTime, endTime, page, pageSize);
        }

        PageInfo<WeightSupNameVO> pageInfo = new PageInfo<>(stockInWeighSupNameVOS);
        PageVO<WeightSupNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<WeightStorageVO> getWeightBySupName(String empName, String compid, String vehicleId,
                                                      String stoName, String supName, String beginTime,
                                                      String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightStorageVO> stockInWeighStorageVOS = stockInWeighmatNsMapper.getWeightBySupName(empName,
                compid, vehicleId, stoName, supName, beginTime, endTime, page, pageSize);
        PageInfo<WeightStorageVO> pageInfo = new PageInfo<>(stockInWeighStorageVOS);
        PageVO<WeightStorageVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<WeightEmpNameVO> getWeightByEmpName(String empName, String compid, String vehicleId,
                                                      String stoName, String supName, String beginTime,
                                                      String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightEmpNameVO> stockInWeighEmpNameVOS = stockInWeighmatNsMapper.getWeightByEmpName(empName,
                compid, vehicleId, stoName, supName, beginTime, endTime, page, pageSize);
        PageInfo<WeightEmpNameVO> pageInfo = new PageInfo<>(stockInWeighEmpNameVOS);
        PageVO<WeightEmpNameVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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

    @Override
    public PageVO<WeightSynthesizeVO> getSynthesizeByMat(String empName, String compid, String vehicleId,
                                                         String stoName, String supName, String beginTime,
                                                         String endTime, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WeightSynthesizeVO> stockInWeighmessageVOS = stockInWeighmatNsMapper.getSynthesizeByMat(empName,
                compid, vehicleId, stoName, supName, beginTime, endTime, page, pageSize);

        PageInfo<WeightSynthesizeVO> pageInfo = new PageInfo<>(stockInWeighmessageVOS);
        PageVO<WeightSynthesizeVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
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
    @Override
    public PageVO<WeightCloseVO> getWeightClose(String empName, String compid, String vehicleId, String stoName,
                                                String supName, String beginTime, String endTime, Integer page,
                                                Integer pageSize) {
        List<WeightCloseVO> weightCloseVOS = stockInWeighmatNsMapper.getWeightClose(empName, compid, vehicleId,
                stoName, supName, beginTime, endTime, page, pageSize);
        PageInfo<WeightCloseVO> pageInfo = new PageInfo<>(weightCloseVOS);
        PageVO<WeightCloseVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public List<WeightMatParentNameVO> getWeightByMatParent(String compid, String beginTime, String endTime) {
        return stockInWeighmatNsMapper.getWeightByMatParent(compid,
                beginTime, endTime);
    }

    @Override
    public List<WeightChartVO> getHistogramByMat(String compid, String empName, String vehicleId, String stoName,
                                                 String supName, String beginTime, String endTime, Integer matType) {
        return stockInWeighmatNsMapper.getHistogramByMat(compid, empName, vehicleId, stoName, supName,
                beginTime, endTime, matType);
    }

    @Override
    public List<WeightChartVO> getPieChartBySupName(String compid, String empName, String vehicleId, String stoName,
                                                    String supName, String beginTime, String endTime) {
        return stockInWeighmatNsMapper.getPieChartBySupName(compid, empName, vehicleId, stoName, supName,
                beginTime, endTime);

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
    @Override
    public List<WeightChartVO> getHistogramByStoName(String compid, String empName, String vehicleId, String stoName,
                                                     String supName, String beginTime, String endTime) {
        return stockInWeighmatNsMapper.getHistogramByStoName(compid, empName, vehicleId, stoName, supName,
                beginTime, endTime);
    }

    /**
     * 上传检验照片
     *
     * @return 路径
     */
    @Override
    public String uploadCheckingImg(String compid, String stICode, MultipartFile image) throws ErpException {
        String fileName = UUID.randomUUID().toString();
        File dir = new File(checkingImageFilePath);
        dir.mkdirs();
        File file = new File(checkingImageFilePath + fileName);
        try {
            Boolean b = file.createNewFile();
            System.out.println(b);
            IOUtils.copy(image.getInputStream(), new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
        }

        StockInCheckVO stockInCheckVO = stockInWeighmatNsMapper.getStockCheck(compid, stICode);
        stockInCheckVO.setCompid(compid);
        String picturePath = stockInCheckVO.getPicturePath();
        if (picturePath == null || picturePath.equals("")) {
            stockInCheckVO.setPicturePath(fileName);
        } else {
            stockInCheckVO.setPicturePath(stockInCheckVO.getPicturePath() + "#" + fileName);
        }
        stockInWeighmatNsMapper.updateCheckStatus(compid, null, stICode, stockInCheckVO.getIsPassOrNot(),
                stockInCheckVO.getPicturePath(), stockInCheckVO.getMatCode(), stockInCheckVO.getStkCode(),
                stockInCheckVO.getNotReason(), null, null);


        return fileName;
    }

    /**
     * 下载图片
     *
     * @param fileName 文件名称
     */
    @Override
    public void downloadPicture(String fileName, HttpServletResponse response) throws ErpException {
        File file = new File(checkingImageFilePath + fileName);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        try {
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.DOWNLOAD_FILE_ERROR);
        }
    }

    /**
     * 更新检验状态
     *
     * @param compid      公司id
     * @param deductNum   另扣量
     * @param stICode     过磅单号
     * @param isPassOrNot 是否合格
     * @param picturePath 图片路径
     * @param matCode     材料编码
     * @param stkCode     库位编码
     */
    @Override
    public void updateCheckStatus(String token, String compid, BigDecimal deductNum, String stICode, int isPassOrNot,
                                  String picturePath, String matCode, String stkCode,
                                  String notReason) throws ErpException {

        String inspector = "";
        Date inspectionTime = new Date();

        // 调用okhttp请求，根据token获取用户信息
        JSONObject jsonObject = tokenGetUser(token);
        if (jsonObject == null) {
            throw new ErpException(ErrEumn.MATERIAL_CHECK_ERROR);
        }
        JSONObject data = (JSONObject) jsonObject.get("data");
        if (data != null) {
            inspector = (String) data.get("username");
        }
        stockInWeighmatNsMapper.updateCheckStatus(compid, deductNum, stICode, isPassOrNot, picturePath,
                matCode, stkCode, notReason, inspector, inspectionTime);
    }

    /**
     * 根据公司ID获取库存
     *
     * @param compid 公司ID
     * @return 结果集列表
     */
    @Override
    public PageVO<StockVO> getStockByComId(String compid, String searchWords, Integer page, Integer pageSize) {
        PageVO<StockVO> pageVO = new PageVO<>();
        PageInfo<StockVO> pageInfo = new PageInfo<>(stockInWeighmatNsMapper.getStockByComId(compid, searchWords));
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 根据公司ID获取库存
     *
     * @param compid 公司ID
     * @return 结果集列表
     */
    @Override
    public PageVO<MaterialVO> getMatByComId(String compid, String searchWords, Integer page, Integer pageSize) {
        PageVO<MaterialVO> pageVO = new PageVO<>();
        PageInfo<MaterialVO> pageInfo = new PageInfo<>(stockInWeighmatNsMapper.getMatByComId(compid, searchWords));
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * 通过 compid  stICode 查询材料过磅
     *
     * @param compid  公司id
     * @param stICode 过磅单号
     */
    @Override
    public StockInCheckVO getStockCheck(String compid, String stICode) {


        return stockInWeighmatNsMapper.getStockCheck(compid, stICode);
    }

    @Override
    public StockInCheckVO deleteCheckingImg(String compid, String stICode, String image) {

        StockInCheckVO stockInCheckVO = stockInWeighmatNsMapper.getStockCheck(compid, stICode);
        stockInCheckVO.setCompid(compid);
        String[] paths = stockInCheckVO.getPicturePath().split("#");
        StringBuilder newPath = new StringBuilder();
        if (paths.length > 0) {
            for (String path : paths
            ) {
                if (!path.equals(image)) {
                    newPath.append(path).append("#");
                }
            }
        }
        stockInWeighmatNsMapper.updateCheckStatus(compid, null, stICode, stockInCheckVO.getIsPassOrNot(),
                newPath.toString(), stockInCheckVO.getMatCode(), stockInCheckVO.getStkCode(),
                stockInCheckVO.getNotReason(), null, null);

        return stockInCheckVO;
    }


    private JSONObject tokenGetUser(String token) throws ErpException {

        JSONObject resultJSON = null;
        OkHttpClient client = new OkHttpClient();


        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();
        Request request = new Request.Builder()
                .url(url + "/user/tokenGetUser")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                resultJSON = JSONObject.parseObject(result);
            }

            return resultJSON;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.MATERIAL_CHECK_ERROR);
        }

    }

}
