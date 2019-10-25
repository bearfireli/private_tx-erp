package com.hntxrj.controller.sell;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.ContractService;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.CallableStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/contract")
@Scope("prototype")
@EnableAsync
@Slf4j
public class ContractController {

    private final ContractService cs;
    private final JdbcTemplate jdbcTemplat;

    @Autowired
    public ContractController(ContractService cs, JdbcTemplate jdbcTemplat) {
        this.cs = cs;
        this.jdbcTemplat = jdbcTemplat;
    }

    /**
     * 添加修改删除工程名称
     *
     * @param mark       操作标识 0 插入 1 更新 2 删除
     * @param compid
     * @param opid       操作员
     *                   //     * @param eppCode 工程编码
     *                   //     * @param CreateTime_1 添加时间
     * @param EPPName2   工程名称
     * @param Address3   地址
     * @param LinkMan4   联系人
     * @param LinkTel5   联系电话
     * @param Remarks6   简介
     * @param RecStatus7 状态 默认为1    where RecStatus = 1
     * @param shortName8 简称
     * @return 返回jsonVo
     */
    @RequestMapping(value = "/spinsertUpDelSMEPPInfo")
    public JsonVo contractDateInsertUpDel(@RequestParam(value = "mark", defaultValue = "0") Integer mark,
                                          @RequestParam String compid,
                                          @RequestParam String opid,
                                          String EPPName2,
                                          String Address3,
                                          String LinkMan4,
                                          String eppCode,
                                          String LinkTel5,
                                          String Remarks6,
                                          @RequestParam(value = "RecStatus7", defaultValue = "1") Integer RecStatus7,
                                          String shortName8, HttpServletRequest request, HttpServletResponse response) {
        JsonVo jsonVo = new JsonVo();
        JSONArray js = null;
        byte b = 0;
        try {
            //数据转成byte
            b = (byte) RecStatus7.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //产生个当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);
        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVo.setCode(9);
            jsonVo.setMsg("请登录");
            return jsonVo;
        }
        //eppCode     都是数据库生成的
        js = cs.contractDateInsertUpDel(mark, compid, opid, eppCode, format, EPPName2, Address3, LinkMan4, LinkTel5, Remarks6, b, shortName8);
        //判断数据的返回提示
        if (js != null && js.size() > 0) {
            JSONObject o = JSONObject.parseObject(JSON.toJSONString(js.getJSONArray(0).get(0)));
            String result = o.getString("result");
            if (result.contains("成功")) {
                jsonVo.setCode(0);
                jsonVo.setMsg(result);
            } else {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
            }
            System.out.println("结果 >>> " + jsonVo.getCode());
            jsonVo.setData(js);
            return jsonVo;
        } else {
            //添加没有提示 是出错了
            jsonVo.setCode(1);
            jsonVo.setMsg("result null");
            return jsonVo;
        }
    }

    /**
     * 合同审核
     *
     * @param compid        企业
     * @param opid          用户
     * @param ContractUID   企业uuid
     * @param ccontractcode 子合同号
     * @param VerifyStatus  审核状态
     * @return
     */
    @RequestMapping(value = "/spupdataContractIsEffective")
    public JsonVo sp_updata_ContractIsEffective(String compid,
                                                String opid,
                                                @RequestParam String ContractUID,
                                                @RequestParam Boolean VerifyStatus,
                                                @RequestParam String ccontractcode, Integer verifytype

    ) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        JSONArray jsonArray = cs.sp_updata_ContractIsEffective(compid, opid, ContractUID, VerifyStatus, ccontractcode, verifytype);

        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject o = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = o.getString("result");
            if (result.contains("成功")) {
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg(result);
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;

            } else {
                jsonVoAndPage.setCode(1);
                jsonVoAndPage.setMsg(result);
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            }
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null 数据出错");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }

    }


    /**
     * 简易合同的     spinsertSMSimpleContractSave
     *
     * @param ContractId_1   合同编号
     * @param SignDate3      签订日期
     * @param ExpiresDate_4  到期日期
     * @param Salesman_5     业务员
     * @param ContractType_6 合同类别
     * @param PriceStyle_7   价格执行方式
     * @param ContractNum_8  合同方量
     * @param PreNum_9       预付方量
     * @param PreMoney_10    预付金额
     * @param EPPCode_11     工程代号
     * @param BuilderCode_12 施工单位代号
     * @param Remarks_13     备注
     * @param IsEffective_14 是否立即生效 0 不是，1 是
     * @return 返回jsonVo
     */
    @RequestMapping(value = "/spinsertSMSimpleContractSave")
    public JsonVo insertSimpleContractSave(Integer mark,
                                           String ContractId_1,
                                           @RequestParam(required = false) String ContractUID_2,
                                           String SignDate3,
                                           String ExpiresDate_4,
                                           String Salesman_5,
                                           Integer ContractType_6,
                                           Integer PriceStyle_7,
                                           Double ContractNum_8,
                                           Double PreNum_9,
                                           Double PreMoney_10,
                                           String EPPCode_11,
                                           String BuilderCode_12,
                                           String Remarks_13,
                                           @RequestParam(defaultValue = "0") Integer IsEffective_14,
                                           String compid,
                                           String opid,
                                           HttpServletRequest request, HttpServletResponse response) {

        JsonVo vo = new JsonVoAndPage();


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            vo.setCode(9);
            vo.setMsg("请登录");
            return vo;
        }


        //返回合同uuid
        if (mark == 0) {
            ContractUID_2 = "{" + UUID.randomUUID().toString() + "}";
        }

        JSONArray jsonArray = cs.insertSimpleContractSave(mark, ContractId_1, ContractUID_2, SignDate3, ExpiresDate_4, Salesman_5, ContractType_6, PriceStyle_7, ContractNum_8, PreNum_9, PreMoney_10, EPPCode_11, BuilderCode_12, Remarks_13, IsEffective_14, opid, compid);
        //判断jsonarray
        if (jsonArray != null && jsonArray.size() > 0) {

            System.out.println(ContractUID_2);

            Map<String, JSONArray> map = new HashMap<String, JSONArray>();

            JSONObject o = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = o.getString("result");

            if (result.contains("成功")) {
                JSONArray jsonArray1 = cs.findCContractCode(compid, ContractUID_2);

                map.put("map", jsonArray1);

                jsonArray.add(map);
                vo.setCode(0);
                vo.setMsg(result);
                vo.setData(jsonArray);
                return vo;

            } else {

                vo.setCode(1);
                vo.setMsg(result);
                vo.setData(jsonArray);
                return vo;

            }


        } else {
            vo.setCode(1);
            vo.setMsg("result null");
            return vo;
        }


    }


    /**
     * 简易合同列表  存储过程  sp_Query_SimpleContract
     *
     * @param type        转态  1 位列表  2 为详情
     * @param buildercode 施工单位
     * @param EPPCode     工程编号
     * @param scaleName   业务员
     * @param compid      企业id
     * @param currPage    当前页
     * @param pageSize    页Size
     * @param beginDate   合同执行时间
     * @param endDate     合同结束时间
     *                    //     * @param ContractUID 合同UUID
     * @param ContractId  合同编号
     * @return
     */
    @RequestMapping(value = "/spQuerySimpleContract")
    public JsonVo spQuerySimpleContract(
            @RequestParam(value = "type", defaultValue = "1") Integer type,
            String buildercode, //判断的是BuiderName
            String EPPCode,  //判断的是EPPName
            String scaleName,
            String compid,
            String opid,
            @RequestParam(value = "currPage", defaultValue = "0") Integer currPage,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            String beginDate,
            String endDate,
            Integer ContractStatus,
//                                                   String ContractUID,  详情另外的方法不用传此字段
            String ContractId,
            String likename,
            @RequestParam(value = "delete", defaultValue = "0") Integer noOver,
            HttpServletRequest request,
            Integer erpType //用户类型 1为销售，2为司机
    ) {


        JsonVoAndPage js = new JsonVoAndPage();
        //防止参数为空
        if (StringUtils.isEmpty(buildercode)) {
            buildercode = null;
        }
        if (StringUtils.isEmpty(EPPCode)) {
            EPPCode = null;
        }
        if (StringUtils.isEmpty(scaleName)) {
            scaleName = null;
        }
        if (StringUtils.isEmpty(beginDate)) {
            beginDate = null;
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = null;
        }
        if (StringUtils.isEmpty(ContractId)) {
            ContractId = null;
        }


        //必须要有compid  和opid  才能继续
        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            js.setCode(9);
            js.setMsg("请登录");
            return js;
        }


        PageBean pageBean = new PageBean(pageSize, currPage);


        try {
            //加载合同列表
            JSONArray j = cs.spQuerySimpleContract(1, buildercode,
                    EPPCode, scaleName, compid,
                    currPage, pageSize, beginDate,
                    endDate, null,
                    ContractId, ContractStatus,
                    likename, opid, noOver, erpType);


            //区总页数
            String str = j.getJSONArray(1).get(0).toString();
            System.out.println(str);

            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            //通过正则匹配总页数
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }

            js.setCode(0);
            js.setMsg("ok");
            js.setData(j);
            js.setPageBean(pageBean);
            return js;


        } catch (Exception e) {
            e.printStackTrace();
            js.setCode(1);
            js.setMsg("查询失败");
            js.setPageBean(new PageBean(pageSize, currPage));
//            js.setData(null);
            return js;
        }

    }


    /**
     * //简易合同详情
     *
     * @param compid      站ID
     * @param ContractUID 合同id 合同UUID 前台将{F1C64634-5B50-453F-9842-D30EF7E89B2A}格式的编号用Base64 编码传到后台
     * @return 返回JsonVo
     */
    @RequestMapping("/spQuerySimpleContractRem")
    public JsonVo spQuerySimpleContractRem(@RequestParam String compid, @RequestParam String ContractUID) {
        JsonVoAndPage jsonVo = new JsonVoAndPage();
        JSONArray jsonArray = cs.spQuerySimpleContractRem(compid, ContractUID);

        //判断jsonArray
        if (jsonArray != null && jsonArray.size() > 0) {
            jsonVo.setCode(0);
            jsonVo.setData(jsonArray);
            jsonVo.setMsg("ok");

            return jsonVo;

        } else {
            jsonVo.setCode(1);
            jsonVo.setData(jsonArray);
            jsonVo.setMsg("result  null");

            return jsonVo;
        }

    }

    /**
     * 业务员下拉列表
     *
     * @param currPage  当前页
     * @param PageSize  页长度
     * @param salecName 业务员名称
     * @param compid    企业
     * @param opid      登录用户
     * @return json
     */
    @RequestMapping(value = "/spQuerySMBusinessGroup.do")
    public JsonVo spQuerySMBusinessGroup(Integer currPage,
                                         Integer PageSize,
                                         String salecName,
                                         String compid,
                                         String opid) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        PageBean pageBean = new PageBean(PageSize, currPage);

        try {
            JSONArray j = cs.spQuerySMBusinessGroup(1, currPage, PageSize, "0", salecName, compid);

            if (j != null && j.size() > 0) {


                String str = j.getJSONArray(1).get(0).toString();
                System.out.println(str);
                str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if (str.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(str));
                }

                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setData(j);
                jsonVoAndPage.setPageBean(pageBean);
                return jsonVoAndPage;
            } else {
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setData(j);
                jsonVoAndPage.setMsg("result null");
                jsonVoAndPage.setPageBean(pageBean);
                return jsonVoAndPage;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();

            jsonVoAndPage.setCode(1);

            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }

    /**
     * 合同类型下拉列表
     *
     * @param compid 企业
     * @param opid   用户
     * @return json
     */
    @RequestMapping(value = "/spQueryDDPublicInfo.do")
    public JsonVo spQueryDDPublicInfo(String compid, String opid) {
        JsonVoAndPage jsonVoAndPage = null;

        JSONArray jsonArray = cs.spQueryDD_PublicInfo(1, 0, 100, compid);
        if (jsonArray != null & jsonArray.size() > 0) {


            jsonVoAndPage = new JsonVoAndPage();
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }

    /**
     * - --合同砼标号   -- --查询语句：  -- select * from SM_ContractGradePriceDetail  where  CContractCode =@子合同号  and ContractUID=@合同UID编号 and RecStatus = 1 order by PriceETime
     *
     * @param currPage      当前页
     * @param PageSize      页长度
     * @param CContractCode 合同编号
     * @param ContractUID   合同的uuid
     * @param compid        企业
     * @param opid          用户
     * @param request       请求
     * @return json
     */
    @RequestMapping(value = "/spQuerySMContractGradePriceDetail")
    public JsonVo spQuerySMContractGradePriceDetail(@RequestParam(defaultValue = "0") Integer currPage,
                                                    @RequestParam(defaultValue = "100") Integer PageSize,
                                                    String CContractCode,
                                                    String ContractUID,
                                                    String compid,
                                                    String opid,
                                                    HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }


        JSONArray jsonArray = cs.spQuerySMContractGradePriceDetail(compid, currPage, PageSize, CContractCode, ContractUID);


        PageBean pageBean = new PageBean(PageSize, currPage);


        if (jsonArray != null && jsonArray.size() > 0) {


            if (jsonArray != null) {
                //取统计的总条数
                if (jsonArray.size() >= 2) {

                    String s = jsonArray.getJSONArray(1).get(0).toString();
                    System.out.println(s);
                    String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                    if (substring.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(substring));
                    }
                }

            }
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);
            System.out.println("spQuerySMContractGradePriceDetail");
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }


    /**
     * 合同特殊材料查询语句
     *
     * @param currPage      当前页
     * @param PageSize      页长度
     * @param CContractCode 子合同编号
     * @param ContractUID   主合同编号
     * @return jsonVo
     */
    @RequestMapping(value = "/spQuerySMContractPriceMarkup")
    public JsonVo spQuerySMContractPriceMarkup(Integer currPage,
                                               Integer PageSize,
                                               String CContractCode,
                                               String ContractUID,
                                               String compid,
                                               String opid,
                                               HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        JSONArray jsonArray = cs.spQuerySMContractPriceMarkup(currPage, PageSize, CContractCode, ContractUID, compid);


        PageBean pageBean = new PageBean(PageSize, currPage);


        if (jsonArray != null && jsonArray.size() > 0) {


            //取统计的总条数
            if (jsonArray != null) {
                //取统计的总条数
                if (jsonArray.size() >= 2) {

                    String s = jsonArray.getJSONArray(1).get(0).toString();
                    System.out.println(s);
                    String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                    if (substring.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(substring));
                    }
                }

            }

            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);

            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }


    /**
     * 合同泵车价格查询语句
     *
     * @param currPage      当前页
     * @param PageSize      页长度
     * @param CContractCode 子合同编号
     * @param ContractUID   主合同编号
     * @return jsonVo
     */
    @RequestMapping(value = "/spQuerySMPumpPriceSet")
    public JsonVo spQuerySMPumpPriceSet(Integer currPage, Integer PageSize, String CContractCode, String ContractUID, String compid, String opid) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }
        JSONArray jsonArray = cs.spQuerySMPumpPriceSet(currPage, PageSize, CContractCode, ContractUID, compid, opid);


        PageBean pageBean = new PageBean(PageSize, currPage);


        if (jsonArray != null && jsonArray.size() > 0) {


            //取统计的总条数
            if (jsonArray != null) {
                //取统计的总条数
                if (jsonArray.size() >= 2) {

                    String s = jsonArray.getJSONArray(1).get(0).toString();
                    System.out.println(s);
                    String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                    if (substring.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(substring));
                    }
                }

            }


            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);

            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }


    /**
     * 合同运距查询语句： -select * from SM_ContractDistanceSet  where  CContractCode =@子合同号  and ContractUID=@合同UID编号 and RecStatus = 1 order by compid
     *
     * @param currPage      当前页
     * @param PageSize      页长度
     * @param CContractCode 子合同编号
     * @param ContractUID   主合同编号
     * @return jsonVo
     */
    @RequestMapping(value = "/spQuerySMContractDistanceSet")
    public JsonVo spQuerySMContractDistanceSet(Integer currPage, Integer PageSize, String CContractCode, String ContractUID, String compid, String opid) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        JSONArray jsonArray = cs.spQuerySMContractDistanceSet(currPage, PageSize, CContractCode, ContractUID, compid, opid);


        PageBean pageBean = new PageBean(PageSize, currPage);

        if (jsonArray != null && jsonArray.size() > 0) {


            //取统计的总条数
            if (jsonArray.size() >= 2) {

                String s = jsonArray.getJSONArray(1).get(0).toString();
                System.out.println(s);
                String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                if (substring.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(substring));
                }
            }


            jsonVoAndPage.setCode(0);

            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }

//
//    /**
//     * 砼标号List 存储
//     * @param status 1 存砼  ,2材料  3 合同泵车价格
//     * @param jsonstr
//     * @return
//     */
//    @RequestMapping(value = "/spinsertUpDelSMContractGradePriceDetailList")
//    public JsonVo spinsertUpDelSMContractGradePriceDetailLis(Integer status,String jsonstr){
//
//
//        JsonVoAndPage jsonVoAndPage =new JsonVoAndPage();
//
//        JSONArray jsonArray = JSONArray.fromObject(jsonstr);
//        List<JSONArray> list =new ArrayList();
//
//        if (status==1&& jsonArray.size()>=0 && jsonArray!=null   ){
//
//
//            for (int i = 0; i < jsonArray.size(); i++) {
//
//                JSONObject o =(JSONObject) jsonArray.get(i);
//                Integer mark = Integer.parseInt(o.getString("mark"));
//                String CContractCode = o.getString("CContractCode");
//                String ContractUID = o.getString("ContractUID");
//                String stgid = o.getString("stgid");
//                Double NotPumpPrice = Double.parseDouble(o.getString("NotPumpPrice"));
//                Double PumpPrice =Double.parseDouble( o.getString("PumpPrice"));
//                Double TowerCranePrice =Double.parseDouble( o.getString("TowerCranePrice"));
//                Double PriceDifference = Double.parseDouble(o.getString("PriceDifference"));
//                String PriceETime = o.getString("PriceETime");
//                String CreateTime = o.getString("CreateTime");
//
//             JSONArray jsonArray1=cs.spinsertUpDelSMContractGradePriceDetail(mark,"01",CContractCode,ContractUID,"0225",stgid,NotPumpPrice,PumpPrice,TowerCranePrice,PriceDifference,PriceETime,CreateTime);
//                list.add(jsonArray1);
//
//
//            }
//
//        }
//
//
//        //特殊材料添加
//        if (status==2&& jsonArray.size()>=0 && jsonArray!=null   ){
//
//
//            for (int i = 0; i < jsonArray.size(); i++) {
//
//                JSONObject o =(JSONObject) jsonArray.get(i);
//                Integer mark = Integer.parseInt(o.getString("mark"));
//                String cContractCode = o.getString("cContractCode");
//                String ContractUID = o.getString("ContractUID");
//                String PPName = o.getString("PPName");
//                Double UnitPrice = Double.parseDouble(o.getString("UnitPrice"));
//                Double JumpPrice =Double.parseDouble( o.getString("JumpPrice"));
//                Double SelfDiscPrice =Double.parseDouble( o.getString("SelfDiscPrice"));
//                Double TowerCranePrice = Double.parseDouble(o.getString("TowerCranePrice"));
//                Double OtherPrice = Double.parseDouble(o.getString("OtherPrice"));
//                Byte IsDefault = Byte.parseByte(o.getString("IsDefault"));
//
//                JSONArray jsonArray1 =cs.spinsertUpDelSMContractPriceMarkup(mark,compid,cContractCode,ContractUID,null,PPName,UnitPrice,JumpPrice,SelfDiscPrice,TowerCranePrice,OtherPrice,IsDefault);
//                list.add(jsonArray1);
//
//
//            }
//
//        }
//
//
//        //合同泵车价格
//        if (status==3&& jsonArray.size()>=0 && jsonArray!=null   ){
//
//
//            for (int i = 0; i < jsonArray.size(); i++) {
//
//                JSONObject o =(JSONObject) jsonArray.get(i);
//                Integer mark = Integer.parseInt(o.getString("mark"));
//                String ccontractCode = o.getString("ccontractCode");
//                String ContractUID = o.getString("ContractUID");
//                Integer PumpType = Integer.parseInt(o.getString("PumpType"));
//                Double PumpPrice = Double.parseDouble(o.getString("PumpPrice"));
//                Double TableExpense =Double.parseDouble( o.getString("TableExpense"));
//
//                JSONArray  jsonArray1 =cs.sp_insertUpDel_SM_PumpPriceSet(mark,compid,ccontractCode,ContractUID,PumpType,PumpPrice,TableExpense,opid);
//                list.add(jsonArray1);
//
//
//            }
//
//        }
//
//
//        jsonVoAndPage.setCode(0);
//        jsonVoAndPage.setMsg("ok");
//        jsonVoAndPage.setData(JSONArray.fromObject(list));
//
//
//        return jsonVoAndPage;
//    }


    /**
     * 合同砼标号添加存储过程
     *
     * @param mark            //   * @param compid  企业
     * @param CContractCode
     * @param ContractUID     //    * @param opid 用户
     * @param stgid           标号名称
     * @param NotPumpPrice    非泵价格
     * @param PumpPrice       泵送价格
     * @param TowerCranePrice 塔吊价
     * @param PriceDifference 差价
     * @param PriceETime      价格开始执行时间
     *                        //     * @param CreateTime  创建时间
     * @return json
     */
    @RequestMapping(value = "/spinsertUpDelSMContractGradePriceDetail")
    public JsonVo spinsertUpDelSMContractGradePriceDetail(Integer mark,
                                                          String CContractCode,
                                                          String ContractUID,
                                                          String stgid,
                                                          Double NotPumpPrice,
                                                          Double PumpPrice,
                                                          Double TowerCranePrice,
                                                          Double PriceDifference,
                                                          String PriceETime,
                                                          String CreateTime,
                                                          String compid,
                                                          String opid,
                                                          HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        JSONArray jsonArray = cs.spinsertUpDelSMContractGradePriceDetail(mark, compid, CContractCode, ContractUID, opid, stgid, NotPumpPrice, PumpPrice, TowerCranePrice, PriceDifference, PriceETime, CreateTime);

        if (jsonArray != null && jsonArray.size() > 0) {

            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setCode(0);


            return jsonVoAndPage;

        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }


    /**
     * 价格执行方式下拉列表
     * //     * @param type  状态
     * //     * @param currPage  当前页
     * //     * @param PageSize  页长度
     *
     * @return 返回jSonVo
     */
    @RequestMapping(value = "/spQueryPriceDDPublicInfo.do")
    public JsonVo spQueryPriceDDPublicInfo() {
        JsonVoAndPage jsonVoAndPage = null;

        JSONArray jsonArray = cs.spQueryPriceDDPublicInfo();
        jsonVoAndPage = new JsonVoAndPage();


        if (jsonArray != null && jsonArray.size() > 0) {


            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        }
    }


//    /**
//     *合同特殊材料添加
//     * @param mark --操作标识 0 插入 1 更新 2 删除
//     * @param cContractCode 子合同id
//     * @param ContractUID 主合同id
////     * @param PPCode  材料id   必须唯一才能添加成功
//     * @param PPName    材料名称  可以重复添加????
//     * @param UnitPrice  价格
//     * @param JumpPrice
//     * @param SelfDiscPrice
//     * @param TowerCranePrice  塔吊价
//     * @param OtherPrice  其他价格
//     * @param IsDefault
//     * @return
//     */
//    @RequestMapping(value = "/spinsertUpDelSMContractPriceMarkup")
//    public  JsonVo spinsertUpDelSMContractPriceMarkup(Integer mark,
//                                                      String cContractCode,
//                                                      String ContractUID,
//                                                      String PPName,
//                                                      Double UnitPrice,
//                                                      Double JumpPrice,
//                                                      Double SelfDiscPrice,
//                                                      Double TowerCranePrice,
//                                                      Double OtherPrice ,
//                                                      @RequestParam(value = "RecStatus7",defaultValue = "1") Integer IsDefault,
//                                                      String compid,
//                                                      String opid,
//                                                      HttpServletRequest request ){
//        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
//        byte b = 0;
//        b = (byte) IsDefault.intValue();
//
//
//
//        if (StringUtils.isEmpty(compid)&&StringUtils.isEmpty(opid)){
//            jsonVoAndPage.setCode(9);
//            jsonVoAndPage.setMsg("请登录");
//            return jsonVoAndPage;
//        }
//
////        String  PPCode = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//
//        JSONArray jsonArray =cs.spinsertUpDelSMContractPriceMarkup(mark,compid,cContractCode,ContractUID,null,PPName,UnitPrice,JumpPrice,SelfDiscPrice,TowerCranePrice,OtherPrice,b);
//
//
//        jsonVoAndPage.setCode(0);
//        jsonVoAndPage.setMsg("ok");
//        jsonVoAndPage.setData(jsonArray);
//
//        return jsonVoAndPage;
//    }
//


//    /**
//     * 3合同泵车价格添加存储过程：sp_insertUpDel_SM_PumpPriceSet
//     * @param mark   操作 0插人  ,1修改, 2 删除
////     * @param compid 企业id
//     * @param cContractCode   子合同编号
//     * @param ContractUID  主合同编号
//     * @param PumpType 泵车类别
//     * @param PumpPrice 泵送单价
//     * @param TableExpense 台班费
////     * @param opid
//     * @return
//     */
//    @RequestMapping(value = "/spinsertUpDelSMPumpPriceSet")
//    public JsonVo spinsertUpDelSMPumpPriceSet(Integer mark, String cContractCode,String ContractUID,Integer PumpType ,Double PumpPrice,Double TableExpense,HttpServletRequest request ){
//        JsonVoAndPage jsonVoAndPage = null;
//
//            jsonVoAndPage = new JsonVoAndPage();
//            if (StringUtils.isEmpty(cContractCode)){
//                cContractCode ="";
//            }
//

//        if (StringUtils.isEmpty(compid)&&StringUtils.isEmpty(opid)){
//            jsonVoAndPage.setCode(9);
//            jsonVoAndPage.setMsg("请登录");
//            return jsonVoAndPage;
//        }
//
//        try {
//            JSONArray jsonArray =cs.sp_insertUpDel_SM_PumpPriceSet(mark,compid,cContractCode,ContractUID,PumpType,PumpPrice,TableExpense,opid);
//            jsonVoAndPage.setCode(0);
//            jsonVoAndPage.setMsg("ok");
//            jsonVoAndPage.setData(jsonArray);
//        } catch (Exception e) {
//            jsonVoAndPage.setCode(9);
//            jsonVoAndPage.setMsg("project");
//            e.printStackTrace();
//        }
//        return  jsonVoAndPage;
//    }
//


    /**
     * 合同运距添加
     *
     * @param mark          --操作标识 0 插入 1 更新 2 删除
     * @param ContractUID   主合同uuid
     * @param ccontractCode 子合同编号
     *                      //     * @param compid 企业ID
     * @param Distance      运输距离
     *                      //     * @param recStatus 标志 0未启用 1启用(0无效1有效)
     * @param Remarks       备注
     * @param request
     * @return
     */
    @RequestMapping(value = "/spinsertUpDelSMContractDistanceSet")
    public JsonVo spinsertUpDelSMContractDistanceSet(Integer mark,
                                                     String ContractUID,
                                                     String ccontractCode,
                                                     Double Distance,
                                                     String compid,
                                                     String opid,
                                                     String compid_1,
                                                     String Remarks, HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        //默认1  启用
        Integer recStatus = 1;


        JSONArray jsonArray = cs.spinsertUpDelSMContractDistanceSet(mark, ContractUID, ccontractCode, compid, Distance, recStatus, Remarks);


        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject o = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = o.getString("result");

            if (result.contains("成功")) {

                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg(result);
                jsonVoAndPage.setData(jsonArray);

            } else {
                jsonVoAndPage.setCode(1);
                jsonVoAndPage.setMsg(result);
                jsonVoAndPage.setData(jsonArray);
            }

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            return jsonVoAndPage;
        }

    }


    /**
     * //--搅拌楼  线号
     *
     * @param currPage
     * @param PageSize
     * @return
     */
    @RequestMapping(value = "/spQueryDDStirInfoSet.do")
    public JsonVo spQueryDDStirInfoSet(Integer currPage,
                                       Integer PageSize,
                                       String compid,
                                       String opid,
                                       HttpServletRequest request) {

        JsonVoAndPage jsonVoAndPage = null;


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }


        JSONArray jsonArray = cs.spQueryDDStirInfoSet(compid, currPage, PageSize);

        PageBean pageBean = new PageBean(PageSize, currPage);
        if (jsonArray != null && jsonArray.size() > 0) {


            //取统计的总条数
            if (jsonArray.size() >= 2) {

                String s = jsonArray.getJSONArray(1).get(0).toString();
                System.out.println(s);
                String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                if (substring.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(substring));
                }
            }

            jsonVoAndPage = new JsonVoAndPage();
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);

            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }


    /**
     * 砼价格  特殊材料   泵车车价格全查
     *
     * @param currPage 当前页
     * @param PageSize 页长度
     * @param mark     1  砼价格  2  特殊材料  3泵车车价格
     * @param compid   企业
     * @param opid     用户
     * @param request
     * @return json
     */


//    @stgid  varchar(30) ,
//    @grade   varchar(30)  ,
//    @PPName  VARCHAR(30)  ,
//    @PumpTypeName varchar(30)
    @RequestMapping(value = "/spQuerySMStgidInfoPrice")
    public JsonVo spQuerySMStgidInfoPrice(Integer currPage,
                                          Integer PageSize,
                                          Integer mark,
                                          String compid,
                                          String opid,
                                          String stgid,
                                          String ppname,
                                          String pumptypename,
                                          HttpServletRequest request) {

        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (StringUtils.isEmpty(stgid)) {
            stgid = null;
        }
        if (StringUtils.isEmpty(ppname)) {
            ppname = null;
        }
        if (StringUtils.isEmpty(pumptypename)) {
            pumptypename = null;
        }


        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        JSONArray jsonArray = cs.spQuerySMStgidInfoPrice(compid, currPage, PageSize, mark, stgid, ppname, pumptypename);


        PageBean pageBean = new PageBean(PageSize, currPage);
        if (jsonArray != null && jsonArray.size() > 0) {


            //取统计的总条数
            if (jsonArray.size() >= 2) {

                String s = jsonArray.getJSONArray(1).get(0).toString();
                System.out.println(s);
                String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                if (substring.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(substring));
                }
            }

            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("0k");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);


            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("result null");

            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        }
    }


    /**
     * 保存砼编号   ,特殊材料,  运距
     *
     * @param stat 1 保存砼编号 2 材料 3 运距
     * @param str  json
     * @return json
     */
    @RequestMapping(value = "/spQueryLISTJson")
    public JsonVo sp_QueryLIST_Json(String compid, String opid, Integer stat, String str,
                                    @RequestParam(defaultValue = "0") Integer mark, HttpServletRequest request) { //@RequestBody JSON json,

        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        JSONArray jsonArray;
        if (stat == 1) {
            // 处理时间。
            JSONArray strArray = JSONArray.parseArray(str);
            JSONArray sendArray = new JSONArray();
            for (Object obj : strArray) {
                JSONObject json = JSONObject.parseObject(String.valueOf(obj));
                json.put("CreateTime", json.getString("CreateTime")
                        .replace("T", " ").replace("Z", "").substring(0, 19));
                json.put("PriceETime", json.getString("PriceETime")
                        .replace("T", " ").replace("Z", "").substring(0, 19));
                sendArray.add(json);
            }
            jsonArray = cs.sp_QueryLIST_Json(compid, opid, stat, String.valueOf(sendArray), mark);
        } else {
            jsonArray = cs.sp_QueryLIST_Json(compid, opid, stat, str, mark);

        }


        log.info("【spQueryLISTJson】result={}", jsonArray);
        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject o = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = o.getString("result");

            //成功
            if (!jsonArray.toString().contains("失败") &&
                    !jsonArray.toString().contains("重复") &&
                    !jsonArray.toString().contains("存在")) {
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg(result);
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;

            } else {
                //有个别失败
                jsonVoAndPage.setCode(1);
                if (jsonArray.toString().contains("成功")) {

                    StringBuilder strs = new StringBuilder();
//                    JSONArray jsonArray1 = jsonArray.getJSONArray(0);
                    for (Object o1 : jsonArray) {
                        JSONArray o11 = (JSONArray) o1;

                        if (!o11.toString().contains("成功")) {

                            JSONObject jsonObject = (JSONObject) o11.get(0);
                            strs.append(jsonObject.get("result").toString());
                        }
                    }
                    jsonVoAndPage.setMsg(strs.toString());
                } else {

                    //全部失败
                    jsonVoAndPage.setMsg("全部保存失败");
                }

                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            }


        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            return jsonVoAndPage;
        }

    }


    @RequestMapping(value = "disableContractDetail")
    public JsonVoAndPage disableContractDetail(String compid, String contractUid,
                                               String ccontractCode, String openid) {
        String sql = "{call p_disable_SM_ContractDetail (?,?,?,?)}";
        CallableStatementCreator csc = connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, compid);
            cs.setString(2, contractUid);
            cs.setString(3, ccontractCode);
            cs.setString(4, openid);
            return cs;
        };

        jdbcTemplat.execute(csc, callableStatement -> {
            System.out.println("成功");
            return null;
        });
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        jsonVoAndPage.setCode(1);
        jsonVoAndPage.setMsg("禁用合同成功");
        return jsonVoAndPage;
    }


}
