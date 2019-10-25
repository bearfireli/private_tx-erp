package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.ContractDao;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ContractDaoImp implements ContractDao {

    @Autowired
    private Procedure procedure;

    /**
     * 添加修改删除工程名称
     *
     * @param Mark         操作标识 0 插入 1 更新 2 删除
     * @param compid       站点
     * @param opId         操作员
     * @param eppCode      工程编码
     * @param CreateTime_1 添加时间
     * @param EPPName_2    工程名称
     * @param Address_3    地址
     * @param LinkMan_4    联系人
     * @param LinkTel_5    联系电话
     * @param Remarks_6    简介
     * @param RecStatus_7  状态 默认为1    where RecStatus = 1
     * @param shortName_8  施工单位简称
     * @return 返回jsonArray
     */
    @Override
    public JSONArray contractDateInsertUpDel(int Mark, String compid, String opId, String eppCode,
                                             String CreateTime_1, String EPPName_2, String Address_3,
                                             String LinkMan_4, String LinkTel_5, String Remarks_6,
                                             Byte RecStatus_7, String shortName_8) {
        List<Param> params = new ArrayList<>();

        params.add(new Param(1, ParamType.INPARAM.getType(), Mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opId));
        params.add(new Param(4, ParamType.INPARAM.getType(), eppCode));
        params.add(new Param(5, ParamType.INPARAM.getType(), CreateTime_1));
        params.add(new Param(6, ParamType.INPARAM.getType(), EPPName_2));
        params.add(new Param(7, ParamType.INPARAM.getType(), Address_3));
        params.add(new Param(8, ParamType.INPARAM.getType(), LinkMan_4));
        params.add(new Param(9, ParamType.INPARAM.getType(), LinkTel_5));
        params.add(new Param(10, ParamType.INPARAM.getType(), Remarks_6));
        params.add(new Param(11, ParamType.INPARAM.getType(), RecStatus_7));
        params.add(new Param(12, ParamType.INPARAM.getType(), shortName_8));
        params.add(new Param(13, ParamType.OUTPARAM.getType(), null));

        procedure.init("sp_insertUpDel_SM_EPPInfo", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 简易合同的存储过程
     *
     * @param ContractId_1   合同编号
     * @param ContractUID_2  合同唯一编号
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
     * @param OpId_15        -操作员
     * @return 放回JSOnArray()
     */
    @Override
    public JSONArray insertSimpleContractSave(
            Integer mark, String ContractId_1,
            String ContractUID_2, String SignDate3,
            String ExpiresDate_4, String Salesman_5,
            int ContractType_6, int PriceStyle_7,
            Double ContractNum_8, Double PreNum_9,
            Double PreMoney_10, String EPPCode_11,
            String BuilderCode_12, String Remarks_13,
            int IsEffective_14, String OpId_15,
            String compid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), ContractId_1));
        params.add(new Param(3, ParamType.INPARAM.getType(), ContractUID_2));
        params.add(new Param(4, ParamType.INPARAM.getType(), SignDate3));
        params.add(new Param(5, ParamType.INPARAM.getType(), ExpiresDate_4));
        params.add(new Param(6, ParamType.INPARAM.getType(), Salesman_5));
        params.add(new Param(7, ParamType.INPARAM.getType(), ContractType_6));
        params.add(new Param(8, ParamType.INPARAM.getType(), PriceStyle_7));
        params.add(new Param(9, ParamType.INPARAM.getType(), ContractNum_8));
        params.add(new Param(10, ParamType.INPARAM.getType(), PreNum_9));
        params.add(new Param(11, ParamType.INPARAM.getType(), PreMoney_10));
        params.add(new Param(12, ParamType.INPARAM.getType(), EPPCode_11));
        params.add(new Param(13, ParamType.INPARAM.getType(), BuilderCode_12));
        params.add(new Param(14, ParamType.INPARAM.getType(), Remarks_13));
        params.add(new Param(15, ParamType.INPARAM.getType(), IsEffective_14));
        params.add(new Param(16, ParamType.INPARAM.getType(), OpId_15));
        String str = "";
        List<Param> outparams = new ArrayList<>();
        params.add(new Param(17, ParamType.OUTPARAM.getType(), str));
        params.add(new Param(18, ParamType.INPARAM.getType(), compid));
        procedure.init("sp_insert_SM_SimpleContractSave", params, outparams);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 简易合同列表  spQuerySimpleContract
     *
     * @param type        转态  1 位列表  2 为详情
     * @param buildercode 施工单位
     * @param eppCode     工程编号
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
    @Override
    public JSONArray spQuerySimpleContract(Integer type, String buildercode,
                                           String eppCode, String scaleName,
                                           String compid, Integer currPage,
                                           Integer pageSize, String beginDate,
                                           String endDate, String ContractUID,
                                           String ContractId, Integer ContractStatus,
                                           String likename, String opid,
                                           Integer noOver, Integer erpType) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), type));
        params.add(new Param(2, ParamType.INPARAM.getType(), buildercode));
        params.add(new Param(3, ParamType.INPARAM.getType(), eppCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), scaleName));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));
        params.add(new Param(6, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(7, ParamType.INPARAM.getType(), pageSize));
        int recordCount = 0;
        params.add(new Param(8, ParamType.OUTPARAM.getType(), recordCount));
        params.add(new Param(9, ParamType.INPARAM.getType(), beginDate));
        params.add(new Param(10, ParamType.INPARAM.getType(), endDate));
        params.add(new Param(11, ParamType.INPARAM.getType(), ContractUID));
        params.add(new Param(12, ParamType.INPARAM.getType(), ContractId));
        params.add(new Param(13, ParamType.INPARAM.getType(), ContractStatus));
        params.add(new Param(14, ParamType.INPARAM.getType(), likename));
        params.add(new Param(15, ParamType.INPARAM.getType(), opid));
        params.add(new Param(16, ParamType.INPARAM.getType(), noOver));
        procedure.init("sp_Query_SimpleContract", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * //简易合同详情
     *
     * @param compid      站ID
     * @param contractUID uuid
     * @return 返回JsonVo
     */
    @Override
    public JSONArray spQuerySimpleContractRem(String compid, String contractUID) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 2));
        params.add(new Param(2, ParamType.INPARAM.getType(), null));
        params.add(new Param(3, ParamType.INPARAM.getType(), null));
        params.add(new Param(4, ParamType.INPARAM.getType(), null));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));
        params.add(new Param(6, ParamType.INPARAM.getType(), 0));
        params.add(new Param(7, ParamType.INPARAM.getType(), 0));
        params.add(new Param(8, ParamType.OUTPARAM.getType(), 0));
        params.add(new Param(9, ParamType.INPARAM.getType(), null));
        params.add(new Param(10, ParamType.INPARAM.getType(), null));
        params.add(new Param(11, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(12, ParamType.INPARAM.getType(), null));
        params.add(new Param(13, ParamType.INPARAM.getType(), null));
        params.add(new Param(14, ParamType.INPARAM.getType(), null));
        procedure.init("sp_Query_SimpleContract", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 业务员下拉列表
     *
     * @param currPage  当前页
     * @param pageSize  页长度
     * @param salecName 业务员名称
     * @param compid    企业
     * @return json
     */
    @Override
    public JSONArray spQuerySMBusinessGroup(Integer type, Integer currPage, Integer pageSize, String department,
                                            String salecName, String compid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), type));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), department));
        params.add(new Param(5, ParamType.INPARAM.getType(), salecName));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_Query_SM_BusinessGroup", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 合同类型下拉列表
     *
     * @param type     位1
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param compid   企业
     * @return jsonArray
     */
    @Override
    public JSONArray spQueryDD_PublicInfo(Integer type, Integer currPage, Integer pageSize, String compid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), type));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), "46"));
        params.add(new Param(5, ParamType.INPARAM.getType(), 1));
//        params.add(new Param(6,ParamType.INPARAM.getType(),compid));
        procedure.init("sp_Query_DD_PublicInfo", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * - --合同砼标号   -- --查询语句：  -- select * from SM_ContractGradePriceDetail  where
     * CContractCode =@子合同号  and ContractUID=@合同UID编号 and RecStatus = 1 order by PriceETime
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 合同编号
     * @param contractUID   合同的uuid
     * @param compid        企业
     * @return json
     */
    @Override
    public JSONArray spQuerySMContractGradePriceDetail(String compid, Integer currPage, Integer pageSize,
                                                       String cContractCode, String contractUID) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(5, ParamType.INPARAM.getType(), contractUID));
        procedure.init("sp_Query_SM_ContractGradePriceDetail", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 合同特殊材料查询语句
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 子合同编号
     * @param contractUID   主合同编号
     * @param compid        企业
     * @return jsonVo
     */
    @Override
    public JSONArray spQuerySMContractPriceMarkup(Integer currPage, Integer pageSize, String cContractCode,
                                                  String contractUID, String compid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(3, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));


        procedure.init("sp_Query_SM_ContractPriceMarkup", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }


    /**
     * 合同泵车价格查询语句
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 子合同编号
     * @param contractUID   主合同编号
     * @param compid        企业
     * @param opid          yonghu
     * @return jsonVo
     */
    @Override
    public JSONArray spQuerySMPumpPriceSet(Integer currPage, Integer pageSize, String cContractCode,
                                           String contractUID, String compid, String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(3, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));


        procedure.init("sp_Query_SM_PumpPriceSet", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }

    /**
     * 合同运距查询语句： -select * from SM_ContractDistanceSet  where  CContractCode =@子合同号
     * and ContractUID=@合同UID编号 and RecStatus = 1 order by compid
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 子合同编号
     * @param contractUID   主合同编号
     * @param compid        企業
     * @param opid          用户
     * @return jsonVo
     */
    @Override
    public JSONArray spQuerySMContractDistanceSet(Integer currPage, Integer pageSize, String cContractCode,
                                                  String contractUID, String compid, String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(3, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));


        procedure.init("sp_Query_SM_ContractDistanceSet", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }


    /**
     * 价格执行方式下拉列表
     *
     * @return
     */
    @Override
    public JSONArray spQueryPriceDDPublicInfo() {

        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), null));
        procedure.init("sp_QueryPrice_DD_PublicInfo", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 合同砼标号添加存储过程
     *
     * @param mark
     * @param compid          企业
     * @param cContractCode   合同编号
     * @param contractUID     合同uuid
     * @param opid            用户
     * @param stgid           线号
     * @param notPumpPrice    非泵价格
     * @param pumpPrice       泵送价格
     * @param towerCranePrice 塔吊价
     * @param priceDifference 差价
     * @param priceETime      价格开始执行时间
     * @param cContractCode1  创建时间
     * @return jsonArray
     */
    @Override
    public JSONArray spinsertUpDelSMContractGradePriceDetail(Integer mark, String compid, String cContractCode,
                                                             String contractUID, String opid, String stgid,
                                                             Double notPumpPrice, Double pumpPrice,
                                                             Double towerCranePrice, Double priceDifference,
                                                             String priceETime, String cContractCode1) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(5, ParamType.INPARAM.getType(), opid));
        params.add(new Param(6, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(7, ParamType.INPARAM.getType(), notPumpPrice));
        params.add(new Param(8, ParamType.INPARAM.getType(), pumpPrice));
        params.add(new Param(9, ParamType.INPARAM.getType(), towerCranePrice));
        params.add(new Param(10, ParamType.INPARAM.getType(), priceDifference));
        params.add(new Param(11, ParamType.INPARAM.getType(), priceETime));
        params.add(new Param(12, ParamType.INPARAM.getType(), cContractCode1));
        params.add(new Param(13, ParamType.OUTPARAM.getType(), null));
        procedure.init("sp_insertUpDel_SM_ContractGradePriceDetail", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    //特殊材料价格添加
    @Override
    public JSONArray spinsertUpDelSMContractPriceMarkup(Integer mark, String compid, String cContractCode,
                                                        String contractUID, String ppCode, String ppName,
                                                        Double unitPrice, Double jumpPrice, Double selfDiscPrice,
                                                        Double towerCranePrice, Double otherPrice, byte isDefault) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), cContractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(5, ParamType.INPARAM.getType(), ppCode));
        params.add(new Param(6, ParamType.INPARAM.getType(), ppName));
        params.add(new Param(7, ParamType.INPARAM.getType(), unitPrice));
        params.add(new Param(8, ParamType.INPARAM.getType(), jumpPrice));
        params.add(new Param(9, ParamType.INPARAM.getType(), selfDiscPrice));
        params.add(new Param(10, ParamType.INPARAM.getType(), towerCranePrice));
        params.add(new Param(11, ParamType.INPARAM.getType(), otherPrice));
        params.add(new Param(12, ParamType.INPARAM.getType(), isDefault));
        params.add(new Param(13, ParamType.OUTPARAM.getType(), null));
        procedure.init("sp_insertUpDel_SM_ContractPriceMarkup", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

//    /**
//     * 3合同泵车价格添加存储过程
//     * @param mark
//     * @param compid
//     * @param ccontractCode
//     * @param contractUID
//     * @param pumpType
//     * @param pumpPrice
//     * @param tableExpense
//     * @param opid
//     * @return
//     */
//    @Override
//    public JSONArray sp_insertUpDel_SM_PumpPriceSet(Integer mark, String compid, String ccontractCode,
//    String contractUID, Integer pumpType, Double pumpPrice, Double tableExpense, String opid) {
//        List<Param> params =new ArrayList<>();
//        params.add(new Param(1,ParamType.INPARAM.getType(),mark));
//        params.add(new Param(2,ParamType.INPARAM.getType(),compid));
//        params.add(new Param(3,ParamType.INPARAM.getType(),ccontractCode));
//        params.add(new Param(4,ParamType.INPARAM.getType(),contractUID));
//        params.add(new Param(5,ParamType.INPARAM.getType(),pumpType));
//        params.add(new Param(6,ParamType.INPARAM.getType(),pumpPrice));
//        params.add(new Param(7,ParamType.INPARAM.getType(),tableExpense));
//        params.add(new Param(8,ParamType.INPARAM.getType(),opid));
//        params.add(new Param(9,ParamType.OUTPARAM.getType(),null));
//        Procedure procedure =new Procedure("sp_insertUpDel_SM_PumpPriceSet",params);
//
//        try {
//            procedure.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return procedure.getResultArray();
//    }

    /**
     * 合同运距添加
     *
     * @param mark          --操作标识 0 插入 1 更新 2 删除
     * @param contractUID   主合同uuid
     * @param ccontractCode 子合同编号
     * @param compid        企业ID
     * @param distance      运输距离
     * @param recStatus     标志 0未启用 1启用(0无效1有效)
     * @param remarks       备注
     * @return jsonarray
     */
    @Override
    public JSONArray spinsertUpDelSMContractDistanceSet(Integer mark, String contractUID, String ccontractCode,
                                                        String compid, Double distance, Integer recStatus, String remarks) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(3, ParamType.INPARAM.getType(), ccontractCode));
        params.add(new Param(4, ParamType.INPARAM.getType(), compid));
        params.add(new Param(5, ParamType.INPARAM.getType(), distance));
        params.add(new Param(6, ParamType.INPARAM.getType(), recStatus));
        params.add(new Param(7, ParamType.INPARAM.getType(), remarks));
//       params.add(new Param(8,ParamType.OUTPARAM.getType(),null));

        params.add(new Param(8, ParamType.OUTPARAM.getType(), null));
        procedure.init("sp_insertUpDel_SM_ContractDistanceSet", params);


        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
//     List<Param> outParameters = procedure.getOutParameters();
//        System.out.println(outParameters);
        return procedure.getResultArray();
    }

    /**
     * -搅拌楼  线号
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     * @return jsonarray
     */
    @Override
    public JSONArray spQueryDDStirInfoSet(String compid, Integer currPage, Integer pageSize) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        procedure.init("sp_Query_DD_StirInfoSet", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 砼价格  特殊材料   泵车车价格全查
     *
     * @param compid       企业
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param mark         1  砼价格  2  特殊材料  3泵车车价格
     * @param stgid
     * @param ppname
     * @param pumptypename @return jsonArray
     */
    @Override
    public JSONArray spQuerySMStgidInfoPrice(String compid, Integer currPage, Integer pageSize, Integer mark,
                                             String stgid, String ppname, String pumptypename) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), mark));
        params.add(new Param(5, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(6, ParamType.INPARAM.getType(), ppname));
        params.add(new Param(7, ParamType.INPARAM.getType(), pumptypename));
        procedure.init("sp_Query_SM_StgidInfoPrice", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    @Override
    public JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json) {
        return sp_QueryLIST_Json(compid, opid, status, json, 0);
    }


    /**
     * 保存砼编号   ,特殊材料,  运距
     *
     * @param compid 企业
     * @param opid   用户
     * @param status 1 保存砼编号 2 材料 3 运距
     * @param json   传递来的json
     * @return jsonarray
     */
    @Override
    public JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json, Integer mark) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), opid));
        params.add(new Param(3, ParamType.INPARAM.getType(), status));
        params.add(new Param(4, ParamType.INPARAM.getType(), json));
        params.add(new Param(5, ParamType.INPARAM.getType(), mark));

        procedure.init("sp_QueryLIST_Json", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 根据UUID 查Code
     *
     * @param compid        qiye
     * @param contractUID_2 合同uuid
     * @return jsonarray
     */
    @Override
    public JSONArray findCContractCode(String compid, String contractUID_2) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), contractUID_2));

        procedure.init("sp_findCContractCode", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 合同审核
     *
     * @param compid        企业
     * @param opid          用户
     * @param contractUID   企业uuid
     * @param verifyStatus  审核状态
     * @param ccontractcode 子合同编号
     * @param verifytype    审核次数
     * @return json
     */
    @Override
    public JSONArray sp_updata_ContractIsEffective(String compid, String opid, String contractUID, Boolean verifyStatus,
                                                   String ccontractcode, Integer verifytype) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), opid));
        params.add(new Param(3, ParamType.INPARAM.getType(), contractUID));
        params.add(new Param(4, ParamType.INPARAM.getType(), verifyStatus));
        params.add(new Param(5, ParamType.INPARAM.getType(), ccontractcode));

        procedure.init("sp_updata_ContractIsEffective", params);


//        params.add(new Param(1,ParamType.INPARAM.getType(),verifytype));
//        params.add(new Param(2,ParamType.INPARAM.getType(),contractUID));
//        params.add(new Param(3,ParamType.INPARAM.getType(),ccontractcode));
//        params.add(new Param(4,ParamType.INPARAM.getType(),verifyStatus));
//        params.add(new Param(5,ParamType.INPARAM.getType(),opid));


        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
