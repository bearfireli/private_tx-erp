package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.TaskListDao;
import com.hntxrj.txerp.dao.UserCompDao;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.util.CommonUtil;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务单相关方法
 * Created by 刘浩然 on 2017/8/2.
 */
@Component
@Scope("prototype")
public class TaskListDaoImpl implements TaskListDao {

    private final Procedure procedure;

    @Autowired
    public TaskListDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    @Resource
    private UserCompDao userCompDao;
    @Value("${app.spterp.erptype}")
    public Integer erpType;


    /**
     * 加载任务单列
     *
     * @param compid      企业id
     * @param taskId      任务单号
     * @param eppName     工程名称
     * @param builderName 施工单位
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param pageBean    分页对象
     * @return
     */
    @Override
    public JSONArray getTaskList(String compid, String taskId, String eppName, String builderName, Integer taskStatus, String placing, String beginTime, String endTime, PageBean pageBean, String opid) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 1));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), taskId));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), eppName));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), builderName));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), taskStatus));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), placing));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), endTime));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        int recordCount = 0;
        comeParam.add(new Param(12, ParamType.OUTPARAM.getType(), recordCount));
        comeParam.add(new Param(13, ParamType.OUTPARAM.getType(), opid));
        //TODO://第三方任务单
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_Query_PT_TASKPLANLIST_Thr", comeParam);
        } else {
            procedure.init("sp_Query_PT_TASKPLANLIST", comeParam);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            CommonUtil.getrecordCount(procedure, pageBean, 1);
        }
        return procedure.getResultArray();
    }


    /**
     * 加载任务单详情
     *
     * @param taskId 任务单ID
     * @return
     */
    @Override
    public JSONArray loadTaskDetail(String taskId, String compid) {
        List<Param> comeParam = new ArrayList<>();
        /* type=2 查详情 */
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 2));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        /*  任务单ID */
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), taskId));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), null));
        int recordCount = 0;
        comeParam.add(new Param(12, ParamType.OUTPARAM.getType(), recordCount));
        //TODO://第三方任务单详情
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType.equals(erpType)) {
            procedure.init("sp_Query_PT_TASKPLANLIST_Thr", comeParam);
        } else {
            procedure.init("sp_Query_PT_TASKPLANLIST", comeParam);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 根据工程名称或施工单位名称筛选任务单列表
     *
     * @param compid   企业id
     * @param eppName  工程名称
     * @param pageBean 分页对象
     * @return
     */
    @Override
    public JSONArray getTaskListByEppAndBuild(String compid, String eppName, PageBean pageBean, String compidOth) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 3));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), eppName));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        int recordCount = 0;
        comeParam.add(new Param(12, ParamType.OUTPARAM.getType(), recordCount));
//        List<Param> outParam = new ArrayList<Param>();
//        outParam.add(new Param(10,ParamType.OUTPARAM.getType(),recordCount));
        //TODO://第三方任务单详情
        Integer currErpType = userCompDao.findUserCompByCompid(compidOth).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_Query_PT_TASKPLANLIST_Thr", comeParam);
        } else {
            procedure.init("sp_Query_PT_TASKPLANLIST", comeParam);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            CommonUtil.getrecordCount(procedure, pageBean, 1);
        }

        return procedure.getResultArray();
    }

    /**
     * 任务单的  增,删,改
     *
     * @param Mark                     标识 0 插入 1 更新 2 删除
     * @param compid_1                 站别代号*
     * @param TaskId_2                 任务单号
     * @param CreateTime_3             创建时间
     * @param OpId_4                   操作员代号*
     * @param PreTime_5                预计时间*
     * @param TaskOverTime_6           任务结束时间
     * @param PreRemark_7              预计备注*
     * @param PreNum_8                 预计方量*
     * @param ContractUID_9            合同UID号*
     * @param TaskStatus_10            任务状态
     * @param VerifyStatus_11          审核标识
     * @param VerifyId_12              审核员代号
     * @param VerifyTime_13            审核时间
     * @param EPPCode_14               工程代号*
     * @param BuilderCode_15           施工单位代号*
     * @param FormulaCode_16           配比编号
     * @param Slump_17                 塌陷度*
     * @param FormulaStatus_18         配比标志
     * @param FormulaTime_19           配比更新时间
     * @param StgId_20                 标号(强度)*
     * @param Grade_21                 抗折等级*
     * @param Attribute_22             特性
     * @param StoneAsk_23              石料要求*
     * @param StoneDia_24              石子粒径*
     * @param CementVariety_25         水泥品种*
     * @param TechnicalRequirements_26 其他技术要求*
     * @param Distance_27              区间(距离)
     * @param TotalVehicleNum_28       总发车次数
     * @param TodayVehicleNum_29       当日发车次数
     * @param TotalNum_30              总运输方量
     * @param TodayNum_31              当日运输方量
     * @param OpenTime_32              开盘时间
     * @param Address_33               交货地址*
     * @param OverTime_34              完成时间
     * @param Placing_35               浇筑部位*
     * @param PlaceStyle_36            浇筑方式*
     * @param LinkMan_37               现场联系人*
     * @param StringLinkTel_38         现场联系电话*
     * @param IsJump_39                是否泵送
     * @param DefaultJump_40           默认泵车
     * @param IsTax_41                 含税否
     * @param LinkPipeStatus_42        接管状态
     * @param LinkPipeName_43          接管人
     * @param LinkPipeTime_44          接管时间
     * @param DownTime_45              下载时间
     * @param DownId_46                下载操作员代号
     * @param UpDown_47                下载标志
     * @param DeleteMark_48            删除标志
     * @param UpDownMark_49            网络下发标识
     * @param PT_TaskPrice             任务价格
     * @param strReturnValue           返回值
     * @param CContractCode_52         子合同号
     * @param PreCarNum_53             预计车数
     * @param IsExcess_54
     * @param TaskType_55              任务单类型 *
     * @return
     */
    @Override
    public JSONArray insertUpDelTaskList(Integer Mark, String compid_1, String TaskId_2, Date CreateTime_3, String OpId_4, String PreTime_5,
                                         String TaskOverTime_6, String PreRemark_7, Double PreNum_8, String ContractUID_9, Integer TaskStatus_10,
                                         Byte VerifyStatus_11, String VerifyId_12, String VerifyTime_13, String EPPCode_14, String BuilderCode_15,
                                         String FormulaCode_16, String Slump_17, Byte FormulaStatus_18, String FormulaTime_19, String StgId_20,
                                         String Grade_21, String Attribute_22, String StoneAsk_23, String StoneDia_24, String CementVariety_25,
                                         String TechnicalRequirements_26, Double Distance_27, Integer TotalVehicleNum_28, Integer TodayVehicleNum_29,
                                         Double TotalNum_30, Double TodayNum_31, String OpenTime_32, String Address_33, String OverTime_34,
                                         String Placing_35, String PlaceStyle_36, String LinkMan_37, String StringLinkTel_38, Byte IsJump_39,
                                         String DefaultJump_40, Byte IsTax_41, Integer LinkPipeStatus_42, String LinkPipeName_43,
                                         String LinkPipeTime_44, String DownTime_45, String DownId_46, Byte UpDown_47, Byte DeleteMark_48,
                                         Integer UpDownMark_49, String PT_TaskPrice, String strReturnValue, String CContractCode_52,
                                         Integer PreCarNum_53, Byte IsExcess_54, Integer TaskType_55) {
        List<Param> comeParam = new ArrayList<>();

        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 0));   //标识[int]
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid_1));   //站别代号[varchar](2)
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), "")); //任务单号[varchar](20)
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), new Date())); //创建时间[varchar](20)
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), OpId_4));  //操作员代号[varchar](10)
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), PreTime_5));  //预计时间[varchar](20)
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), new Date()));   //任务结束时间[varchar](20)
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), PreRemark_7));   //预计备注[varchar](100)
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), PreNum_8));     //预计方量[decimal](18,4)
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), ContractUID_9));  //合同UID号[varchar](40)
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), 0));   //任务状态[int]
        comeParam.add(new Param(12, ParamType.INPARAM.getType(), 0));   //审核标识[bit]
        comeParam.add(new Param(13, ParamType.INPARAM.getType(), ""));   //审核员代号[varchar](10)
        comeParam.add(new Param(14, ParamType.INPARAM.getType(), new Date()));    //审核时间[varchar](20)
        comeParam.add(new Param(15, ParamType.INPARAM.getType(), EPPCode_14));    //工程代号[varchar](20)
        comeParam.add(new Param(16, ParamType.INPARAM.getType(), BuilderCode_15));   //施工单位代号[varchar](20)
        comeParam.add(new Param(17, ParamType.INPARAM.getType(), ""));     //配比编号[varchar](20)
        comeParam.add(new Param(18, ParamType.INPARAM.getType(), Slump_17));     //塌陷度[varchar](10)
        comeParam.add(new Param(19, ParamType.INPARAM.getType(), 0));   //配比标志[bit]
        comeParam.add(new Param(20, ParamType.INPARAM.getType(), new Date()));    //配比更新时间[varchar](20)
        comeParam.add(new Param(21, ParamType.INPARAM.getType(), StgId_20));   //标号(强度)[varchar](30)
        comeParam.add(new Param(22, ParamType.INPARAM.getType(), Grade_21));  //抗折等级[varchar](10)
        comeParam.add(new Param(23, ParamType.INPARAM.getType(), ""));   //特性[varchar](10)
        comeParam.add(new Param(24, ParamType.INPARAM.getType(), StoneAsk_23));   //石料要求[varchar](10)
        comeParam.add(new Param(25, ParamType.INPARAM.getType(), StoneDia_24));    //石子粒径[varchar](10)
        comeParam.add(new Param(26, ParamType.INPARAM.getType(), CementVariety_25));   //水泥品种[varchar](10)
        comeParam.add(new Param(27, ParamType.INPARAM.getType(), TechnicalRequirements_26));  //其他技术要求[varchar](10)
        comeParam.add(new Param(28, ParamType.INPARAM.getType(), Distance_27));   //区间(距离)[decimal](18,4)
        comeParam.add(new Param(29, ParamType.INPARAM.getType(), 0));    //总发车次数[int]
        comeParam.add(new Param(30, ParamType.INPARAM.getType(), 0));    //当日发车次数[int]
        comeParam.add(new Param(31, ParamType.INPARAM.getType(), 0));   //总运输方量[decimal](18,4)
        comeParam.add(new Param(32, ParamType.INPARAM.getType(), 0));   //当日运输方量[decimal](18,4)
        comeParam.add(new Param(33, ParamType.INPARAM.getType(), new Date()));    //开盘时间[varchar](20)
        comeParam.add(new Param(34, ParamType.INPARAM.getType(), Address_33));     //交货地址[varchar](40)
        comeParam.add(new Param(35, ParamType.INPARAM.getType(), new Date()));    //完成时间[varchar](20)
        comeParam.add(new Param(36, ParamType.INPARAM.getType(), Placing_35));    //浇筑部位[varchar](120)
        comeParam.add(new Param(37, ParamType.INPARAM.getType(), PlaceStyle_36));  //浇筑方式[varchar](10)
        comeParam.add(new Param(38, ParamType.INPARAM.getType(), LinkMan_37));    //现场联系人[varchar](50)
        comeParam.add(new Param(39, ParamType.INPARAM.getType(), StringLinkTel_38));  //现场联系电话[varchar](50)
        comeParam.add(new Param(40, ParamType.INPARAM.getType(), 0));     //是否泵送[bit]
        comeParam.add(new Param(41, ParamType.INPARAM.getType(), 0));     //默认泵车[varchar](20)
        comeParam.add(new Param(42, ParamType.INPARAM.getType(), 0));     //含税否[bit]
        comeParam.add(new Param(43, ParamType.INPARAM.getType(), 1));    //接管状态[int]
        comeParam.add(new Param(44, ParamType.INPARAM.getType(), ""));    //接管人[varchar](10)
        comeParam.add(new Param(45, ParamType.INPARAM.getType(), new Date()));    //接管时间[varchar](20)
        comeParam.add(new Param(46, ParamType.INPARAM.getType(), new Date()));    //下载时间[varchar](20)
        comeParam.add(new Param(47, ParamType.INPARAM.getType(), ""));    //下载操作员代号[varchar](10)
        comeParam.add(new Param(48, ParamType.INPARAM.getType(), ""));    //下载标志[bit]
        comeParam.add(new Param(49, ParamType.INPARAM.getType(), 0));    //删除标志[bit]
        comeParam.add(new Param(50, ParamType.INPARAM.getType(), 0));    //网络下发标识[int]
        comeParam.add(new Param(51, ParamType.INPARAM.getType(), ""));    //加价项目[varchar](1000)
        comeParam.add(new Param(52, ParamType.INPARAM.getType(), null));     //返回值
        comeParam.add(new Param(53, ParamType.INPARAM.getType(), CContractCode_52));   //子合同号[varchar](20)
        comeParam.add(new Param(54, ParamType.INPARAM.getType(), PreCarNum_53));    //预计车数[int]
        comeParam.add(new Param(55, ParamType.INPARAM.getType(), 1));    //是否允许超量  [bit]
        comeParam.add(new Param(56, ParamType.INPARAM.getType(), TaskType_55));   //任务单类型[int]

        procedure.init("sp_insertUpDel_PT_TaskPlan", comeParam);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 任务单审核
     *
     * @param cmpid        站点ID
     * @param taskID       任务单号
     * @param empname      用户名
     * @param verifyStatus 审核状态 0 取消审核 1 审核
     * @return
     */
    @Override
    public JSONArray spVerifyTask(String cmpid, String taskID, String empname, Integer verifyStatus) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), cmpid));
        params.add(new Param(2, ParamType.INPARAM.getType(), taskID));
        params.add(new Param(3, ParamType.INPARAM.getType(), empname));
        params.add(new Param(4, ParamType.INPARAM.getType(), verifyStatus));
        params.add(new Param(5, ParamType.INPARAM.getType(), ""));
        /*List<Param> outParam = new ArrayList<Param>();
        String msg ="";
        outParam.add(new Param(5,ParamType.OUTPARAM.getType()," "));*/

        procedure.init("sp_Verify_Task", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        procedure.getOutParameters();
        return procedure.getResultArray();
    }


    /**
     * 修改任务单信息
     *
     * @param compid_1                 企业ID
     * @param TaskId_2                 任务单号
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
    @Override
    public JSONArray upTaskPlan(String compid_1, String TaskId_2, String OpId_4, String PreTime_5, String TaskOverTime_6, String PreRemark_7,
                                Double PreNum_8, String ContractUID_9, String CContractCode_52, String EPPCode_14, String BuilderCode_15,
                                String Slump_17, String StgId_20, String Grade_21, String Attribute_22, String StoneAsk_23, String StoneDia_24,
                                String CementVariety_25, String TechnicalRequirements_26, Double Distance_27, Integer TotalVehicleNum_28,
                                Integer TodayVehicleNum_29, Double TotalNum_30, Double TodayNum_31, String OpenTime_32, String Address_33,
                                String OverTime_34, String Placing_35, String PlaceStyle_36, String LinkMan_37, String LinkTel_38, byte IsJump_39,
                                String DefaultJump_40, byte IsTax_41, Integer LinkPipeStatus_42, String LinkPipeName_43, String DownTime_45,
                                String DownId_46, byte DeleteMark_48, Integer PreCarNum_53, byte IsExcess_54, Integer TaskType_55) {
        List<Param> comeParam = new ArrayList<Param>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 1));   //标识[int]
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid_1));   //站别代号[varchar](2)
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), TaskId_2)); //任务单号[varchar](20)
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), new Date())); //创建时间[varchar](20)
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), OpId_4));  //操作员代号[varchar](10)
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), PreTime_5));  //预计时间[varchar](20)
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), TaskOverTime_6));   //任务结束时间[varchar](20)
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), PreRemark_7));   //预计备注[varchar](100)
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), PreNum_8));     //预计方量[decimal](18,4)
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), ContractUID_9));  //合同UID号[varchar](40)
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), 0));   //任务状态[int]
        comeParam.add(new Param(12, ParamType.INPARAM.getType(), 0));   //审核标识[bit]
        comeParam.add(new Param(13, ParamType.INPARAM.getType(), ""));   //审核员代号[varchar](10)
        comeParam.add(new Param(14, ParamType.INPARAM.getType(), new Date()));    //审核时间[varchar](20)
        comeParam.add(new Param(15, ParamType.INPARAM.getType(), EPPCode_14));    //工程代号[varchar](20)
        comeParam.add(new Param(16, ParamType.INPARAM.getType(), BuilderCode_15));   //施工单位代号[varchar](20)
        comeParam.add(new Param(17, ParamType.INPARAM.getType(), ""));     //配比编号[varchar](20)
        comeParam.add(new Param(18, ParamType.INPARAM.getType(), Slump_17));     //塌陷度[varchar](10)
        comeParam.add(new Param(19, ParamType.INPARAM.getType(), 0));   //配比标志[bit]
        comeParam.add(new Param(20, ParamType.INPARAM.getType(), new Date()));    //配比更新时间[varchar](20)
        comeParam.add(new Param(21, ParamType.INPARAM.getType(), StgId_20));   //标号(强度)[varchar](30)
        comeParam.add(new Param(22, ParamType.INPARAM.getType(), Grade_21));  //抗折等级[varchar](10)
        comeParam.add(new Param(23, ParamType.INPARAM.getType(), Attribute_22));   //特性[varchar](10)
        comeParam.add(new Param(24, ParamType.INPARAM.getType(), StoneAsk_23));   //石料要求[varchar](10)
        comeParam.add(new Param(25, ParamType.INPARAM.getType(), StoneDia_24));    //石子粒径[varchar](10)
        comeParam.add(new Param(26, ParamType.INPARAM.getType(), CementVariety_25));   //水泥品种[varchar](10)
        comeParam.add(new Param(27, ParamType.INPARAM.getType(), TechnicalRequirements_26));  //其他技术要求[varchar](10)
        comeParam.add(new Param(28, ParamType.INPARAM.getType(), Distance_27));   //区间(距离)[decimal](18,4)
        comeParam.add(new Param(29, ParamType.INPARAM.getType(), TotalVehicleNum_28));    //总发车次数[int]
        comeParam.add(new Param(30, ParamType.INPARAM.getType(), TodayVehicleNum_29));    //当日发车次数[int]
        comeParam.add(new Param(31, ParamType.INPARAM.getType(), TotalNum_30));   //总运输方量[decimal](18,4)
        comeParam.add(new Param(32, ParamType.INPARAM.getType(), TodayNum_31));   //当日运输方量[decimal](18,4)
        comeParam.add(new Param(33, ParamType.INPARAM.getType(), OpenTime_32));    //开盘时间[varchar](20)
        comeParam.add(new Param(34, ParamType.INPARAM.getType(), Address_33));     //交货地址[varchar](40)
        comeParam.add(new Param(35, ParamType.INPARAM.getType(), OverTime_34));    //完成时间[varchar](20)
        comeParam.add(new Param(36, ParamType.INPARAM.getType(), Placing_35));    //浇筑部位[varchar](120)
        comeParam.add(new Param(37, ParamType.INPARAM.getType(), PlaceStyle_36));  //浇筑方式[varchar](10)
        comeParam.add(new Param(38, ParamType.INPARAM.getType(), LinkMan_37));    //现场联系人[varchar](50)
        comeParam.add(new Param(39, ParamType.INPARAM.getType(), LinkTel_38));  //现场联系电话[varchar](50)
        comeParam.add(new Param(40, ParamType.INPARAM.getType(), IsJump_39));     //是否泵送[bit]
        comeParam.add(new Param(41, ParamType.INPARAM.getType(), DefaultJump_40));     //默认泵车[varchar](20)
        comeParam.add(new Param(42, ParamType.INPARAM.getType(), IsTax_41));     //含税否[bit]
        comeParam.add(new Param(43, ParamType.INPARAM.getType(), LinkPipeStatus_42));    //接管状态[int]
        comeParam.add(new Param(44, ParamType.INPARAM.getType(), LinkPipeName_43));    //接管人[varchar](10)
        comeParam.add(new Param(45, ParamType.INPARAM.getType(), new Date()));    //接管时间[varchar](20)
        comeParam.add(new Param(46, ParamType.INPARAM.getType(), DownTime_45));    //下载时间[varchar](20)
        comeParam.add(new Param(47, ParamType.INPARAM.getType(), DownId_46));    //下载操作员代号[varchar](10)
        comeParam.add(new Param(48, ParamType.INPARAM.getType(), ""));    //下载标志[bit]
        comeParam.add(new Param(49, ParamType.INPARAM.getType(), DeleteMark_48));    //删除标志[bit]
        comeParam.add(new Param(50, ParamType.INPARAM.getType(), 0));    //网络下发标识[int]
        comeParam.add(new Param(51, ParamType.INPARAM.getType(), ""));    //加价项目[varchar](1000)
        comeParam.add(new Param(52, ParamType.INPARAM.getType(), null));     //返回值
        comeParam.add(new Param(53, ParamType.INPARAM.getType(), CContractCode_52));   //子合同号[varchar](20)
        comeParam.add(new Param(54, ParamType.INPARAM.getType(), PreCarNum_53));    //预计车数[int]
        comeParam.add(new Param(55, ParamType.INPARAM.getType(), IsExcess_54));    //IsExcess  [bit]
        comeParam.add(new Param(56, ParamType.INPARAM.getType(), TaskType_55));   //任务单类型[int]
        procedure.init("sp_insertUpDel_PT_TaskPlan", comeParam);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 解析剪辑版
     *
     * @param mark        标记
     * @param compid      企业ID
     * @param opid        操作员ID
     * @param eppName     工程名
     * @param builderName 施工单位名
     * @param linkMan     联系人
     * @param linkTel     联系电话
     * @return
     */
    @Override
    public JSONArray parseData(Integer mark, String compid, String opid, String eppName, String builderName, String linkMan, String linkTel) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opid));
        params.add(new Param(4, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(5, ParamType.INPARAM.getType(), builderName));
        params.add(new Param(6, ParamType.INPARAM.getType(), linkMan));
        params.add(new Param(7, ParamType.INPARAM.getType(), linkTel));
        params.add(new Param(8, ParamType.INPARAM.getType(), "2017-9-12 09:41:46"));
        procedure.init("sp_Query_ParseData", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

}
