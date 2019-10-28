package com.hntxrj.txerp.dao;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;

import java.util.Date;

/**
 * Created by 刘浩然 on 2017/8/2.
 */
public interface TaskListDao {
    /**
     * 获取任务单列表
     *
     * @param compid      企业id
     * @param taskId      任务单号
     * @param eppName     工程名称
     * @param builderName 施工单位
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param pageBean    分页对象
     * @return 任务单数组
     */
    JSONArray getTaskList(String compid, String taskId,
                          String eppName, String builderName,
                          Integer taskStatus, String placing,
                          String beginTime, String endTime,
                          PageBean pageBean,
                          String opid);

    /**
     *   根据工程名称或施工单位名称筛选任务单列表
     *
     * @param compid      企业id
     * @param eppName     工程名称
     * @param pageBean    分页对象
     * @return 任务单数组
     */
    JSONArray getTaskListByEppAndBuild(String compid,
                          String eppName,
                          PageBean pageBean,
                                       String compidOth);


    /**
     *  加载任务单详情
     * @param taskId 任务单ID
     * @return
     */
    JSONArray loadTaskDetail( String taskId, String compid);

    /**
     *   任务单的 增，删，改
     * @param Mark  标识 0 插入 1 更新 2 删除
     * @param compid_1   站别代号
     * @param TaskId_2   任务单号
     * @param CreateTime_3   创建时间
     * @param OpId_4    操作员代号
     * @param PreTime_5     预计时间
     * @param TaskOverTime_6    任务结束时间
     * @param PreRemark_7       预计备注
     * @param PreNum_8      预计方量
     * @param ContractUID_9     合同UID号
     * @param TaskStatus_10     任务状态
     * @param VerifyStatus_11       审核标识
     * @param VerifyId_12       审核员代号
     * @param VerifyTime_13     审核时间
     * @param EPPCode_14        工程代号
     * @param BuilderCode_15        施工单位代号
     * @param FormulaCode_16    配比编号
     * @param Slump_17      塌陷度
     * @param FormulaStatus_18      配比标志
     * @param FormulaTime_19        配比更新时间
     * @param StgId_20      标号(强度)
     * @param Grade_21      抗折等级
     * @param Attribute_22      特性
     * @param StoneAsk_23       石料要求
     * @param StoneDia_24       石子粒径
     * @param CementVariety_25      水泥品种
     * @param TechnicalRequirements_26      其他技术要求
     * @param Distance_27       区间(距离)
     * @param TotalVehicleNum_28        总发车次数
     * @param TodayVehicleNum_29        当日发车次数
     * @param TotalNum_30       总运输方量
     * @param TodayNum_31       当日运输方量
     * @param OpenTime_32       开盘时间
     * @param Address_33        交货地址
     * @param OverTime_34       完成时间
     * @param Placing_35        浇筑部位
     * @param PlaceStyle_36     浇筑方式
     * @param LinkMan_37        现场联系人
     * @param StringLinkTel_38      现场联系电话
     * @param IsJump_39         是否泵送
     * @param DefaultJump_40        默认泵车
     * @param IsTax_41      含税否
     * @param LinkPipeStatus_42     接管状态
     * @param LinkPipeName_43       接管人
     * @param LinkPipeTime_44       接管时间
     * @param DownTime_45       下载时间
     * @param DownId_46     下载操作员代号
     * @param UpDown_47     下载标志
     * @param DeleteMark_48     删除标志
     * @param UpDownMark_49     网络下发标识
     * @param PT_TaskPrice
     * @param strReturnValue
     * @param CContractCode_52
     * @param PreCarNum_53
     * @param IsExcess_54
     * @param TaskType_55
     * @return
     */
    JSONArray insertUpDelTaskList(Integer Mark,
                                  String compid_1,
                                  String TaskId_2,
                                  Date CreateTime_3,
                                  String OpId_4,
                                  String PreTime_5,
                                  String TaskOverTime_6,
                                  String PreRemark_7,
                                  Double PreNum_8,
                                  String ContractUID_9,
                                  Integer TaskStatus_10,
                                  Byte VerifyStatus_11,
                                  String VerifyId_12,
                                  String VerifyTime_13,
                                  String EPPCode_14,
                                  String BuilderCode_15,
                                  String FormulaCode_16,
                                  String Slump_17,
                                  Byte FormulaStatus_18,
                                  String FormulaTime_19,
                                  String StgId_20,
                                  String Grade_21,
                                  String Attribute_22,
                                  String StoneAsk_23,
                                  String StoneDia_24,
                                  String CementVariety_25,
                                  String TechnicalRequirements_26,
                                  Double Distance_27,
                                  Integer TotalVehicleNum_28,
                                  Integer TodayVehicleNum_29,
                                  Double TotalNum_30,
                                  Double TodayNum_31,
                                  String OpenTime_32,
                                  String Address_33,
                                  String OverTime_34,
                                  String Placing_35,
                                  String PlaceStyle_36,
                                  String LinkMan_37,
                                  String StringLinkTel_38,
                                  Byte IsJump_39,
                                  String DefaultJump_40,
                                  Byte IsTax_41,
                                  Integer LinkPipeStatus_42,
                                  String LinkPipeName_43,
                                  String LinkPipeTime_44,
                                  String DownTime_45,
                                  String DownId_46,
                                  Byte UpDown_47,
                                  Byte DeleteMark_48,
                                  Integer UpDownMark_49,
                                  String PT_TaskPrice,
                                  String strReturnValue,
                                  String CContractCode_52,
                                  Integer PreCarNum_53,
                                  Byte IsExcess_54,
                                  Integer TaskType_55);
/*
    @compId varchar(20),--企业编号
            @TaskId varchar(20),--任务单号
            @UserName varchar(20),--审核人员名称
    @VerifyStatus int,  --审核状态 0 取消审核 1 审核
    @strReturnValue as nvarchar(100) output ---返回信息*/

    /**
     *    任务单审核
     * @param cmpid  站点ID
     * @param taskID   任务单号
     * @param empname    用户名
     * @param verifyStatus  审核状态 0 取消审核 1 审核
     * @return
     */
    JSONArray spVerifyTask(String cmpid,String taskID,String empname,Integer verifyStatus);



    /**
     *   修改任务单信息
     * @param compid_1  企业ID
     * @param TaskId_2  任务单号
     * @param OpId_4   操作员代号
     * @param PreTime_5  预计时间
     * @param TaskOverTime_6   任务结束时间
     * @param PreRemark_7   预计备注
     * @param PreNum_8    预计方量
     * @param ContractUID_9    合同uid
     * @param CContractCode_52   子合同号
     * @param EPPCode_14   工程代号
     * @param BuilderCode_15    施工单位代号
     * @param Slump_17    塌陷度
     * @param StgId_20    标号（强度）
     * @param Grade_21   抗折等级
     * @param Attribute_22   特性
     * @param StoneAsk_23  石料要求
     * @param StoneDia_24   石子粒径
     * @param CementVariety_25   水泥品种
     * @param TechnicalRequirements_26   其他技术要求
     * @param Distance_27    区间(距离)
     * @param TotalVehicleNum_28     总发车次数
     * @param TodayVehicleNum_29    当日发车次数
     * @param TotalNum_30   总运输方量
     * @param TodayNum_31    当日运输方量
     * @param OpenTime_32   开盘时间
     * @param Address_33    交货地址
     * @param OverTime_34   完成时间
     * @param Placing_35    浇筑部位
     * @param PlaceStyle_36   浇筑方式
     * @param LinkMan_37    现场联系人
     * @param LinkTel_38    现场联系电话
     * @param IsJump_39     是否泵送
     * @param DefaultJump_40    默认泵车
     * @param IsTax_41      含税否
     * @param LinkPipeStatus_42     接管状态
     * @param LinkPipeName_43    接管人
     * @param DownTime_45    下载时间
     * @param DownId_46     下载操作员代号
     * @param DeleteMark_48     删除标记
     * @param PreCarNum_53    预计车数
     * @param IsExcess_54
     * @param TaskType_55   任务单类型
     * @return
     */
    JSONArray upTaskPlan(String compid_1, String TaskId_2, String OpId_4, String PreTime_5, String TaskOverTime_6, String PreRemark_7,
                         Double PreNum_8, String ContractUID_9, String CContractCode_52, String EPPCode_14, String BuilderCode_15,
                         String Slump_17, String StgId_20, String Grade_21, String Attribute_22, String StoneAsk_23, String StoneDia_24,
                         String CementVariety_25, String TechnicalRequirements_26, Double Distance_27, Integer TotalVehicleNum_28,
                         Integer TodayVehicleNum_29, Double TotalNum_30, Double TodayNum_31, String OpenTime_32, String Address_33,
                         String OverTime_34, String Placing_35, String PlaceStyle_36, String LinkMan_37, String LinkTel_38, byte IsJump_39,
                         String DefaultJump_40, byte IsTax_41, Integer LinkPipeStatus_42, String LinkPipeName_43, String DownTime_45,
                         String DownId_46, byte DeleteMark_48, Integer PreCarNum_53, byte IsExcess_54, Integer TaskType_55);


    /**
     *   解析剪辑版
     * @param mark 标记
     * @param compid  企业ID
     * @param opid   操作员ID
     * @param eppName  工程名
     * @param builderName  施工单位名
     * @param linkMan  联系人
     * @param linkTel  联系电话
     * @return
     */
    JSONArray parseData(Integer mark, String compid,String opid, String eppName, String builderName,String linkMan,String linkTel);
}
