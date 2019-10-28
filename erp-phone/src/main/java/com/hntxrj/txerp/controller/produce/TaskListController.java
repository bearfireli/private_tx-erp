package com.hntxrj.txerp.controller.produce;

import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.TaskListServer;
import com.hntxrj.txerp.util.jdbc.GetMsg;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 任务单控制器
 * Created by 刘浩然 on 2017/8/2.
 */
@RestController
@RequestMapping("/produce")
@Slf4j
@Scope("prototype")
@EnableAsync
public class TaskListController {


    private final TaskListServer taskListServer;

    @Autowired
    public TaskListController(TaskListServer taskListServer) {
        this.taskListServer = taskListServer;
    }

    /**
     * 加载任务单列
     *
     * @param compid      企业id
     * @param taskId      任务单号
     * @param eppName     工程名称
     * @param builderName 施工单位
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param taskStatus  任务单状态
     * @param placing     浇筑部位
     * @param currPage    页码
     * @return
     * @throws Exception
     */
    @RequestMapping("/getTaskList") //接口测试地址 ：http://localhost:8080/spterp/produce/getTaskList?compid=01
    public JsonVo getTaskList(String compid, String taskId, String eppName, String builderName, String beginTime,
                              String endTime, String taskStatus, String placing, @RequestParam(value = "currPage", defaultValue = "0") Integer currPage, String opid) {

        /* 获取数据 */
        JsonVoAndPage vo = new JsonVoAndPage();

        vo.setCode(0);
        vo.setMsg("ok");
        //创建分页
        PageBean pb = new PageBean(10, currPage);
        //设置中条数
        pb.setRecordCount(0);//赋默认值
        //设置时间
//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
//        Date beginDate = null;
//        Date endDate = null;
        try {
            //时间不能为空
//            if ( beginTime != null && !beginTime.equals("")){
//                beginDate = new Date( sim.parse(beginTime).getTime() );
//            }
//            //时间不能为空
//            if ( endTime != null && !endTime.equals("")){
//                endDate = new Date( sim.parse(endTime).getTime() );
//            }
            //出来参数   为 ""时转化null
            {
                compid = compid == null || compid.equals("") ? null : compid;
                taskId = taskId == null || taskId.equals("") ? null : taskId;
                eppName = eppName == null || eppName.equals("") ? null : eppName;
                placing = placing == null || placing.equals("") ? null : placing;
                builderName = builderName == null || builderName.equals("") ? null : builderName;
                taskStatus = taskStatus == null || taskStatus.equals("") ? null : taskStatus;
//                beginDate = beginDate==null?null:beginDate;
//                endDate = endDate==null?null:endDate;
            }
            //调用dao

            vo.setData(taskListServer.getTaskList(compid, taskId, eppName, builderName, taskStatus == null ? null : Integer.parseInt(taskStatus), placing, beginTime, endTime, pb, opid));


            vo.setPageBean(pb);
        } catch (Exception e) {
            vo.setCode(1);
            vo.setMsg("error,Date Formate Error！");
            log.info("任务单列表", e);
        }
        return vo;
    }


    /**
     * 加载任务单详情
     *
     * @param taskId 任务单ID
     * @return
     */
    @RequestMapping("/loadTaskDetail") //接口测试地址 ：http://localhost:8080/spterp/produce/loadTaskDetail?taskId=P01170430015
    public JsonVo loadTaskDetail(String taskId, String compid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        //参数不能为空
        if (taskId != null && !taskId.equals("")) {
            vo.setData(taskListServer.loadTaskDetail(taskId, compid));
            vo.setCode(0);
            vo.setMsg("ok");
        } else {
            //为空直接返回
            vo.setCode(1);
            vo.setMsg("error,TaskId is Null!");
        }
        return vo;
    }


    /**
     * 增加任务单
     *
     * @param PreTime_5                预计时间
     * @param PreRemark_7              预计备注
     * @param PreNum_8                 预计方量
     * @param ContractUID_9            合同UID
     * @param EPPCode_14               工程代号
     * @param BuilderCode_15           施工单位代号
     * @param Slump_17                 塌陷度
     * @param StgId_20                 标号(强度)
     * @param Grade_21                 抗折等级
     * @param StoneAsk_23              石料要求Attribute_22
     * @param StoneDia_24              石子粒径
     * @param CementVariety_25         水泥品种
     * @param TechnicalRequirements_26 其他技术要求
     * @param Distance_27              区间(距离)
     * @param Address_33               交货地址
     * @param Placing_35               浇筑部位
     * @param PlaceStyle_36            浇筑方式
     * @param LinkMan_37               现场联系人
     * @param PreCarNum_53             预计车数
     * @param StringLinkTel_38         现场联系电话
     * @param TaskType_55              任务单类型
     * @param CContractCode_52         子合同号
     * @return
     */
    @RequestMapping(value = "/addTaskPlan", method = RequestMethod.POST)
    public JsonVo insertUpDelTaskList(String compid, String opid, String PreTime_5, String PreRemark_7,
                                                 Double PreNum_8, String ContractUID_9, String EPPCode_14,
                                                 String BuilderCode_15, String Slump_17, String StgId_20,
                                                 String Grade_21, String StoneAsk_23, String StoneDia_24,
                                                 String CementVariety_25, String TechnicalRequirements_26,
                                                 Double Distance_27, String Address_33, String Placing_35,
                                                 String PlaceStyle_36, String LinkMan_37, String StringLinkTel_38,
                                                 String CContractCode_52, Integer PreCarNum_53, Integer TaskType_55,
                                                 @RequestParam(defaultValue = "") String Attribute_22,
                                                 HttpServletRequest request) {
        JsonVo vo = new JsonVo();

        /*测试数据*/
        /*
        compid_1 = "01"; OpId_4 = "000"; TaskType_55 = 1; PreTime_5 = "2017-07-05 11:24:11";
        PreRemark_7 ="预计备注";  PreNum_8 = 58.8;  ContractUID_9 = "{AFEF0F1D-7907-4D50-8F2F-281792576B27}";
        EPPCode_14 = "201012009"; BuilderCode_15 = "KH0003"; Slump_17="强";  StgId_20="12+10";
        Grade_21 = "3";  StoneAsk_23="无";  StoneDia_24 = "100"; CementVariety_25 = "灰水泥";
        TechnicalRequirements_26 = "没有其他技术要求";  Address_33= "郑州中原区";  Placing_35= "底部";
        PlaceStyle_36 = "灌输";  LinkMan_37 = "王德"; StringLinkTel_38 = "15222222222";
        */

//        Map<String,String> map =new CookieUtils().getUserInfo(request);
        vo.setCode(1); //error
        if (ContractUID_9 == null || ContractUID_9.equals("")) {
            vo.setMsg("error,ContractUID is null");
        } else if (EPPCode_14 == null || EPPCode_14.equals("")) {
            vo.setMsg("error,EPPCode null");
        } else if (BuilderCode_15 == null || BuilderCode_15.equals("")) {
            vo.setMsg("error,BuilderCode null");
        } else if (Placing_35 == null || Placing_35.equals("")) {
            vo.setMsg("error,Placing null");
        } else if (StgId_20 == null || StgId_20.equals("")) {
            vo.setMsg("error,StgId null");
        } else if (Slump_17 == null || Slump_17.equals("")) {
            vo.setMsg("error,Slump null");
        } else if (PlaceStyle_36 == null || PlaceStyle_36.equals("")) {
            vo.setMsg("error,PlaceStyle null");
        }/*else if ( PreTime_5 == null || PreTime_5.equals("") ){
            vo.setMsg("error,PreTime null");
        }*/ else if (compid == null || compid.equals("")) {
            vo.setMsg("error, compid is null!");
        } else if (opid == null || opid.equals("")) {
            vo.setMsg("error, opid is null!");
        } else if (CContractCode_52 == null || CContractCode_52.equals("")) {
            vo.setMsg("error, CContractCode is null!");
        } else if (TaskType_55 == null || TaskType_55.equals("")) {
            vo.setMsg("error,TaskType null");
        } else {
            {
                //处理塌陷度
                Slump_17 = formatSlump(Slump_17);
                GetMsg.getJsonMsg(vo, taskListServer.insertUpDelTaskList(null, compid, null, null, opid, PreTime_5, null,
                        PreRemark_7, PreNum_8, ContractUID_9, null, null, null, null,
                        EPPCode_14, BuilderCode_15, null, Slump_17, null, null,
                        StgId_20, Grade_21, Attribute_22, StoneAsk_23, StoneDia_24, CementVariety_25, TechnicalRequirements_26, Distance_27,
                        null, null, null, null, null, Address_33,
                        null, Placing_35, PlaceStyle_36, LinkMan_37, StringLinkTel_38, null, null,
                        null, null, null, null, null, null,
                        null, null, null, null, null, CContractCode_52,
                        PreCarNum_53, null, TaskType_55));
            }
        }
        return vo;
    }


    /**
     * 根据任务单号和工程名称或施工单位名称筛选任务单列表
     *
     * @param name     名
     * @param currPage 当前页码
     * @return
     */
    @RequestMapping("/getTaskListByEppAndBuild")
    public JsonVoAndPage getTaskListByEppAndBuild(String name, @RequestParam(value = "currPage", defaultValue = "0") Integer currPage,
                                                  String compid) {

        JsonVoAndPage vo = new JsonVoAndPage();

        vo.setCode(0);
        vo.setMsg("ok");

        PageBean pageBean = new PageBean(10, currPage);
        String compidOth = compid;
        vo.setData(taskListServer.getTaskListByEppAndBuild(compid, name == null || "".equals(name) ? null : name, pageBean, compidOth));
        vo.setPageBean(pageBean);

        return vo;
    }


    /**
     * 任务单审核
     *
     * @param compid       用户所属企业ID
     * @param taskID       任务单号
     * @param empname      当前登录的用户名
     * @param verifyStatus 审核状态 0 取消审核 1 审核
     * @return
     */
    @RequestMapping("/spVerifyTask")
    public JsonVo spVerifyTask(String compid, String taskID, String empname, Integer verifyStatus) {
        JsonVo vo = new JsonVo();

        vo.setCode(1);
        if (compid == null || compid.equals("")) {
            vo.setMsg("error,compid is null ");
        } else if (taskID == null || taskID.equals("")) {
            vo.setMsg("error,taskID is null ");
        } else if (empname == null || empname.equals("")) {
            vo.setMsg("error,empname is null ");
        } else if (verifyStatus == null || verifyStatus > 1 || verifyStatus < 0) {
            vo.setMsg("error,verifyStatus is error ");
        } else {
            GetMsg.getJsonMsg(vo, taskListServer.spVerifyTask(compid, taskID, empname, verifyStatus));
        }
        return vo;
    }

    /**
     * 格式化塌陷度
     *
     * @param str
     * @return
     */
    public String formatSlump(String str) {

        String[] s = str.split("[±+-]");

        Pattern compile = Pattern.compile("\\d*([^0-9])\\d*");
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String c = matcher.group(1);

        for (int i = 0; i < s.length; i++) {
            s[i] = String.format("%1$-4s", s[i]);
            if (i > 0) {
                s[i] = c + s[i];
            }
        }
        return s[0] + s[1];
    }


    /**
     * 修改任务单
     *
     * @param compid                   企业ID
     * @param taskid                   任务单号
     * @param OpId_4                   操作员代号
     * @param PreTime_5                预计时间
     * @param TaskOverTime_6           任务结束时间
     * @param PreRemark_7              预计备注
     * @param PreNum_8                 预计方量
     * @param ContractUID_9            合同uid
     * @param CContractCode_52         子合同号
     * @param EPPCode_14               工程代号
     * @param BuilderCode_15           施工单位代号
     * @param Slump_17                 塌陷度
     * @param StgId_20                 标号（强度）
     * @param Grade_21                 抗折等级
     * @param Attribute_22             特性
     * @param StoneAsk_23              石料要求
     * @param StoneDia_24              石子粒径
     * @param CementVariety_25         水泥品种
     * @param TechnicalRequirements_26 其他技术要求
     * @param Distance_27              区间(距离)
     * @param TotalVehicleNum_28       总发车次数
     * @param TodayVehicleNum_29       当日发车次数
     * @param TotalNum_30              总运输方量
     * @param TodayNum_31              当日运输方量
     * @param OpenTime_32              开盘时间
     * @param Address_33               交货地址
     * @param OverTime_34              完成时间
     * @param Placing_35               浇筑部位
     * @param PlaceStyle_36            浇筑方式
     * @param LinkMan_37               现场联系人
     * @param LinkTel_38               现场联系电话
     * @param IsJump_39                是否泵送
     * @param DefaultJump_40           默认泵车
     * @param IsTax_41                 含税否
     * @param LinkPipeStatus_42        接管状态
     * @param LinkPipeName_43          接管人
     * @param DownTime_45              下载时间
     * @param DownId_46                下载操作员代号
     * @param DeleteMark_48            删除标记
     * @param PreCarNum_53             预计车数
     * @param IsExcess_54
     * @param TaskType_55              任务单类型
     * @return
     */
    @RequestMapping("/upTaskPlan")
    public JsonVo updateTaskPlan(String compid, String taskid, String OpId_4, String PreTime_5, String TaskOverTime_6, String PreRemark_7,
                                 Double PreNum_8, String ContractUID_9, String CContractCode_52, String EPPCode_14, String BuilderCode_15,
                                 String Slump_17, String StgId_20, String Grade_21, String Attribute_22, String StoneAsk_23, String StoneDia_24,
                                 String CementVariety_25, String TechnicalRequirements_26, Double Distance_27, Integer TotalVehicleNum_28,
                                 Integer TodayVehicleNum_29, Double TotalNum_30, Double TodayNum_31, String OpenTime_32, String Address_33,
                                 String OverTime_34, String Placing_35, String PlaceStyle_36, String LinkMan_37, String LinkTel_38, byte IsJump_39,
                                 String DefaultJump_40, byte IsTax_41, Integer LinkPipeStatus_42, String LinkPipeName_43, String DownTime_45,
                                 String DownId_46, byte DeleteMark_48, Integer PreCarNum_53, byte IsExcess_54, Integer TaskType_55) {
        JsonVo vo = new JsonVo();
        if (compid == null || compid.equals("")) {
            vo.setMsg("error,compid is null");
            vo.setCode(1);
        } else if (taskid == null || taskid.equals("")) {
            vo.setMsg("error,TaskId_2 is null");
            vo.setCode(1);
        } else {
            GetMsg.getJsonMsg(vo, taskListServer.upTaskPlan(compid, taskid, OpId_4, PreTime_5, TaskOverTime_6, PreRemark_7, PreNum_8,
                    ContractUID_9, CContractCode_52, EPPCode_14, BuilderCode_15, Slump_17, StgId_20, Grade_21, Attribute_22, StoneAsk_23,
                    StoneDia_24, CementVariety_25, TechnicalRequirements_26, Distance_27, TotalVehicleNum_28, TodayVehicleNum_29, TotalNum_30,
                    TodayNum_31, OpenTime_32, Address_33, OverTime_34, Placing_35, PlaceStyle_36, LinkMan_37, LinkTel_38, IsJump_39,
                    DefaultJump_40, IsTax_41, LinkPipeStatus_42, LinkPipeName_43, DownTime_45, DownId_46, DeleteMark_48, PreCarNum_53,
                    IsExcess_54, TaskType_55));
        }
        return vo;
    }


    /**
     * 解析 剪辑版 内容
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/parse.do", method = RequestMethod.POST)
    public JsonVo parseData(Integer mark, String compid, String opid, String data) {
        JsonVo vo = new JsonVo();

        String[] str = data.split("\\n");
        System.out.println(str.length);

        String builderName = "";
        String eppName = "";
        String placing = "";
        String Grade_21 = "";
        String Slump_17 = "";
        String preNum = "";
        String linkMan = "";
        String linkTel = "";


        for (String s : str) {
            if (s.contains("施工单位")) {
                builderName = s.split("[:：]")[1];
            } else if (s.contains("工程名称")) {
                eppName = s.split("[:：]")[1];
            } else if (s.contains("浇筑部位")) {
                placing = s.split("[:：]")[1];
            } else if (s.contains("强度等级")) {
                Grade_21 = s.split("[:：]")[1];
            } else if (s.contains("塌落度")) {
                Slump_17 = s.split("[:：]")[1];
            } else if (s.contains("预计方量")) {
                preNum = s.split("[:：]")[1];
            } else if (s.contains("联系人")) {
                linkMan = s.split("[:：]")[1];
            } else if (s.contains("联系电话")) {
                linkTel = s.split("[:：]")[1];
            }
        }

        if (mark == 1) {
            /* 添加工程名与施工单位 */
            vo.setCode(0);
            vo.setMsg("ok");

            builderName = builderName == null || "".equals(builderName) ? null : builderName;
            eppName = eppName == null || "".equals(eppName) ? null : eppName;

            vo.setData(taskListServer.parseData(mark, compid, opid, eppName, builderName, linkMan, linkTel));
        } else if (mark == 2) {
            vo.setCode(0);
            vo.setMsg("ok");

            Map<String, String> map = new HashMap<>();
            map.put("placing", placing);
            map.put("Grade_21", Grade_21);
            map.put("Slump_17", Slump_17);
            map.put("preNum", preNum);
            vo.setData(map);
        }
        return vo;
    }

}
