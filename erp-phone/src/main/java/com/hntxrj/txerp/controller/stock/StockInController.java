package com.hntxrj.txerp.controller.stock;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.StockInServer;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stockin")
@Scope("prototype")
@EnableAsync
public class StockInController {

    private final StockInServer stockInServer;

    @Autowired
    public StockInController(StockInServer stockInServer) {
        this.stockInServer = stockInServer;
    }

    /**
     * 材料过磅统计
     *
     * @param mark
     * @param sup_LinkMan 联系人
     * @param mu          母公司
     * @param empName     过磅员
     * @param vehicleID   车号
     * @param for_Name    运输商
     * @param sto_Name    入库库位
     * @param matNS       材料名称
     * @param sup_Name    供货商
     * @param stI_Status  记录状态 0    1  是
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param compid      当前登录用户的企业
     * @param opid        登录用户
     * @param currPage    当前页
     * @param PageSize    页长度
     * @return json
     */
    @RequestMapping("/spVMPStockInQuerysql")
    public JsonVo sp_V_MP_StockInQuerysql(@RequestParam String mark,
                                          String sup_LinkMan,
                                          String mu,
                                          String empName,
                                          String vehicleID,
                                          String for_Name,
                                          String sto_Name,
                                          String matNS,
                                          String sup_Name,
                                          Boolean stI_Status,
                                          @RequestParam String beginTime,
                                          @RequestParam String endTime,
                                          @RequestParam String compid,
                                          String opid,
                                          @RequestParam Integer currPage,
                                          @RequestParam Integer PageSize) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(sup_LinkMan)) {
            sup_LinkMan = null;
        }
        if (StringUtils.isEmpty(mu)) {
            mu = null;
        }
        if (StringUtils.isEmpty(empName)) {
            empName = null;
        }
        if (StringUtils.isEmpty(vehicleID)) {
            vehicleID = null;
        }
        if (StringUtils.isEmpty(for_Name)) {
            for_Name = null;
        }
        if (StringUtils.isEmpty(sto_Name)) {
            sto_Name = null;
        }
        if (StringUtils.isEmpty(matNS)) {
            matNS = null;
        }
        if (StringUtils.isEmpty(opid)) {
            opid = null;
        }
        //获取数据集
        JSONArray jsonArray = stockInServer.sp_V_MP_StockInQuerysql(mark, sup_LinkMan, mu, empName, vehicleID, for_Name, sto_Name, matNS, sup_Name, stI_Status, beginTime, endTime, compid, opid, currPage, PageSize);
        PageBean pageBean = new PageBean(PageSize, currPage);
        //数据集不为空时返回
        if (jsonArray != null && jsonArray.size() > 0) {
            //分页的总天数
            String str = jsonArray.getJSONArray(1).get(0).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setPageBean(pageBean);
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setPageBean(pageBean);
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }

    /**
     * srqq 材料过磅  下拉
     *
     * @param id       1 联系人下拉 MP_Supplier   2  过磅员查询 User_employee 3 运输商查询      这个表  MP_Forwarder 4 入库库位  MP_Stock 5 材料名称    V_MatCodeToName 6 供货商名称 MP_Supplier
     * @param sqlwhere 查询条件
     * @return json
     */
    @RequestMapping("/spquerydownsrq.do")
    public JsonVoAndPage spquerydownsrq(String id,
                                        String sqlwhere,
                                        @RequestParam String compid,
                                        @RequestParam String opid,
                                        Integer PageSize,
                                        Integer currPage) {
        if (StringUtils.isEmpty(sqlwhere)) {
            sqlwhere = null;
        }
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        JSONArray jsonArray = stockInServer.sp_query_downsrq(id, sqlwhere, compid,
                opid, PageSize, currPage);


        try {
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(1).get(0);
            PageBean pageBean = new PageBean(PageSize, currPage);
            String recordCount = jsonObject.getString("recordCount");
            pageBean.setRecordCount(Integer.parseInt(recordCount));
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        } catch (Exception e) {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }


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
     * @param PageSize   页长度
     * @param Sup_Name   供货商
     * @param for_Name   运输商
     * @param empName    过磅员
     * @param sto_Name   入库库位
     * @param matNS      材料名称
     * @param VehicleID  车号
     * @param saletype   业务类别
     * @param stI_Status 记录状态
     * @param sticode    详情过磅code
     * @return json
     */
    @RequestMapping("/spVMPStockInQuerylistsrq")
    public JsonVo sp_V_MP_StockInQuerylistsrq(@RequestParam String mark,
                                              String beginTime,
                                              String endTime,
                                              @RequestParam String compid,
                                              @RequestParam String opid,
                                              Integer currPage,
                                              Integer PageSize,
                                              String Sup_Name,
                                              String for_Name,
                                              String empName,
                                              String sto_Name,
                                              String matNS,
                                              String VehicleID,
                                              Integer saletype,
                                              @RequestParam(defaultValue = "1") Integer stI_Status,
                                              String sticode
    ) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(empName)) {
            empName = null;
        }
        if (StringUtils.isEmpty(Sup_Name)) {
            Sup_Name = null;
        }
        if (StringUtils.isEmpty(for_Name)) {
            for_Name = null;
        }
        if (StringUtils.isEmpty(sto_Name)) {
            sto_Name = null;
        }
        if (StringUtils.isEmpty(matNS)) {
            matNS = null;
        }
        if (StringUtils.isEmpty(VehicleID)) {
            VehicleID = null;
        }
        if (StringUtils.isEmpty(sticode)) {
            sticode = null;
        }
        JSONArray jsonArray = stockInServer.sp_V_MP_StockInQuerylistsrq(mark, beginTime, endTime, compid, opid, currPage, PageSize, Sup_Name, for_Name, empName, sto_Name, matNS, VehicleID, saletype, stI_Status, sticode);

        //分页类
        PageBean pageBean = new PageBean(PageSize, currPage);

        if (Integer.parseInt(mark) == 1) {
            //数据集不为空时返回
            if (jsonArray != null && jsonArray.size() > 0) {
                //分页的总天数
                String str = jsonArray.getJSONArray(1).get(0).toString();
                str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if (str.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(str));
                }
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg("ok");

                jsonVoAndPage.setPageBean(pageBean);
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            } else {
                jsonVoAndPage.setCode(1);
                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setPageBean(pageBean);
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            }
        } else {
            if (jsonArray != null && jsonArray.size() > 0) {
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            } else {
                jsonVoAndPage.setCode(1);
                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;

            }
        }

    }


    /**
     * 原材料统计汇总
     *
     * @param mark      1原材林明细汇总  2原材林汇总统计  3 材料入库汇总   4 实时库存 查询
     * @param beginTime
     * @param endTime
     * @param compid
     * @param opid
     * @param currPage
     * @param PageSize
     * @param matname   材料名称
     * @param sup_name  供货商名称
     * @param vehicleID 车号
     * @return json
     */
    @RequestMapping("/spVMPStockInQueryManNsrq")
    public JsonVo sp_V_MP_StockInQueryManNsrq(@RequestParam String mark,
                                              @RequestParam String beginTime,
                                              @RequestParam String endTime,
                                              @RequestParam String compid,
                                              @RequestParam String opid,
                                              @RequestParam Integer currPage,
                                              @RequestParam Integer PageSize,

                                              String matname,
                                              String sup_name,
                                              String vehicleID
    ) {

        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(matname)) {
            matname = null;
        }
        if (StringUtils.isEmpty(sup_name)) {
            sup_name = null;
        }
        if (StringUtils.isEmpty(vehicleID)) {
            vehicleID = null;
        }


        JSONArray jsonArray = stockInServer.sp_V_MP_StockInQueryManNsrq(mark, beginTime, endTime, compid, opid, currPage, PageSize, matname, sup_name, vehicleID);

        //分页类
        PageBean pageBean = new PageBean(PageSize, currPage);

        //数据集不为空时返回
        if (jsonArray != null && jsonArray.size() > 0) {


            //分页的总天数
            String str = jsonArray.getJSONArray(1).get(0).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }


            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");

            jsonVoAndPage.setPageBean(pageBean);
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setPageBean(pageBean);
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }

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
     * @param PageSize   页长度
     * @param empnameb   过磅员
     * @param WeightType 过磅类别
     * @param VehicleID  车号
     * @param EppName    工程名称
     * @return json
     */
    @RequestMapping("/spQueryVMVehicleWeightListsrq")
    public JsonVo sp_Query_VM_VehicleWeightListsrq(@RequestParam Integer mark,
                                                   @RequestParam String beginTime,
                                                   @RequestParam String endTime,
                                                   @RequestParam String compid,
                                                   @RequestParam String opid,
                                                   @RequestParam Integer currPage,
                                                   @RequestParam Integer PageSize,

                                                   String empnameb,
                                                   Integer WeightType,
                                                   String VehicleID,
                                                   String EppName
    ) {

        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(empnameb)) {
            empnameb = null;
        }
        if (StringUtils.isEmpty(VehicleID)) {
            VehicleID = null;
        }
        if (StringUtils.isEmpty(EppName)) {
            EppName = null;
        }
        //调用dao
        JSONArray jsonArray = stockInServer.sp_Query_VM_VehicleWeightListsrq(mark, beginTime, endTime, compid, opid, currPage, PageSize, empnameb, WeightType, VehicleID, EppName);

        //分页类
        PageBean pageBean = new PageBean(PageSize, currPage);

        //数据集不为空时返回
        if (jsonArray != null && jsonArray.size() > 0) {
            //分页的总天数
            String str = jsonArray.getJSONArray(1).get(0).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setPageBean(pageBean);
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } else {
            //jsonArray  为null 返回错误提示
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }
}
