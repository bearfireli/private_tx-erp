package com.hntxrj.dao;


import com.alibaba.fastjson.JSONArray;

public interface ContractDao {

    /**
     * 添加修改删除工程名称
     *
     * @param mark         操作标识 0 插入 1 更新 2 删除
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
    JSONArray contractDateInsertUpDel(int mark,
                                      String compid,
                                      String opId,
                                      String eppCode,
                                      String CreateTime_1,
                                      String EPPName_2,
                                      String Address_3,
                                      String LinkMan_4,
                                      String LinkTel_5,
                                      String Remarks_6,
                                      Byte RecStatus_7,
                                      String shortName_8);


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
     * @return 返回JSONArray
     */
    JSONArray insertSimpleContractSave(Integer mark,
                                       String ContractId_1,
                                       String ContractUID_2,
                                       String SignDate3,
                                       String ExpiresDate_4,
                                       String Salesman_5,
                                       int ContractType_6,
                                       int PriceStyle_7,
                                       Double ContractNum_8,
                                       Double PreNum_9,
                                       Double PreMoney_10,
                                       String EPPCode_11,
                                       String BuilderCode_12,
                                       String Remarks_13,
                                       int IsEffective_14,
                                       String OpId_15,
                                       String compid);

    /**
     * 简易合同列表  spQuerySimpleContract
     *
     * @param type        转态  1 位列表  2 为详情
     * @param buildercode 施工单位
     * @param eppCode     工程名称
     * @param scaleName   业务员
     * @param compid      企业id
     * @param currPage    当前页
     * @param pageSize    页Size
     * @param beginDate   合同执行时间
     * @param endDate     合同结束时间
     *                    //     * @param ContractUID 合同UUID
     * @param ContractId  合同编号
     * @param noOver      0全部，1未完成状态
     * @param erpType     用户类型 1为销售，2为司机
     * @return
     */
    JSONArray spQuerySimpleContract(Integer type, String buildercode,
                                    String eppCode, String scaleName,
                                    String compid, Integer currPage,
                                    Integer pageSize, String beginDate,
                                    String endDate, String ContractUID,
                                    String ContractId, Integer ContractStatus,
                                    String likename, String opid,
                                    Integer noOver, Integer erpType);

    /**
     * //简易合同详情
     *
     * @param compid      站ID
     * @param contractUID uuid
     * @return 返回JsonVo
     */
    JSONArray spQuerySimpleContractRem(String compid, String contractUID);


    /**
     * 业务员下拉列表
     *
     * @param currPage  当前页
     * @param pageSize  页长度
     * @param salecName 业务员名称
     * @param compid    企业
     * @return json
     */
    JSONArray spQuerySMBusinessGroup(Integer type, Integer currPage, Integer pageSize, String department, String salecName, String compid);

    /**
     * 合同类型下拉列表
     *
     * @param type     位1
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param compid   企业
     * @return jsonArray
     */
    JSONArray spQueryDD_PublicInfo(Integer type, Integer currPage, Integer pageSize, String compid);

    /**
     * - --合同砼标号   -- --查询语句：  -- select * from SM_ContractGradePriceDetail  where  CContractCode =@子合同号  and ContractUID=@合同UID编号 and RecStatus = 1 order by PriceETime
     *
     * @param currPage       当前页
     * @param pageSize       页长度
     * @param cContractCode  合同编号
     * @param cContractCode1 合同的uuid
     * @param compid         企业
     * @return json
     */
    JSONArray spQuerySMContractGradePriceDetail(String compid, Integer currPage, Integer pageSize, String cContractCode, String cContractCode1);

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
    JSONArray spQuerySMContractPriceMarkup(Integer currPage, Integer pageSize, String cContractCode, String contractUID, String compid);

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
    JSONArray spQuerySMPumpPriceSet(Integer currPage, Integer pageSize, String cContractCode, String contractUID, String compid, String opid);

    /**
     * 合同运距查询语句： -select * from SM_ContractDistanceSet  where  CContractCode =@子合同号  and ContractUID=@合同UID编号 and RecStatus = 1 order by compid
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 子合同编号
     * @param contractUID   主合同编号
     * @param compid        企業
     * @param opid          用户
     * @return jsonVo
     */
    JSONArray spQuerySMContractDistanceSet(Integer currPage, Integer pageSize, String cContractCode, String contractUID, String compid, String opid);

    /**
     * 价格执行方式下拉
     *
     * @return
     */
    JSONArray spQueryPriceDDPublicInfo();

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
    JSONArray spinsertUpDelSMContractGradePriceDetail(Integer mark, String compid, String cContractCode, String contractUID, String opid, String stgid, Double notPumpPrice, Double pumpPrice, Double towerCranePrice, Double priceDifference, String priceETime, String cContractCode1);


    JSONArray spinsertUpDelSMContractPriceMarkup(Integer mark, String compid, String cContractCode, String contractUID, String ppCode, String ppName, Double unitPrice, Double jumpPrice, Double selfDiscPrice, Double towerCranePrice, Double otherPrice, byte isDefault);

//    JSONArray sp_insertUpDel_SM_PumpPriceSet(Integer mark, String compid, String ccontractCode, String contractUID, Integer pumpType, Double pumpPrice, Double tableExpense, String opid);

    /**
     * 合同运距添加
     *
     * @param mark          --操作标识 0 插入 1 更新 2 删除
     * @param contractUID   主合同uuid
     * @param ccontractCode 子合同编号
     *                      //     * @param compid 企业ID
     * @param distance      运输距离
     *                      //     * @param recStatus 标志 0未启用 1启用(0无效1有效)
     * @param remarks       备注
     * @return
     */
    JSONArray spinsertUpDelSMContractDistanceSet(Integer mark, String contractUID, String ccontractCode, String compid, Double distance, Integer recStatus, String remarks);


    /**
     * -搅拌楼  线号
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     * @return jsonarray
     */
    JSONArray spQueryDDStirInfoSet(String compid, Integer currPage, Integer pageSize);


    /**
     * 砼价格  特殊材料   泵车车价格全查
     *
     * @param compid       企业
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param mark         1  砼价格  2  特殊材料  3泵车车价格
     * @param stgid         线号
     * @param ppname
     * @param pumptypename @return jsonArray
     */
    JSONArray spQuerySMStgidInfoPrice(String compid, Integer currPage, Integer pageSize, Integer mark, String stgid,
                                      String ppname, String pumptypename);


    /**
     * 保存砼编号   ,特殊材料,  运距
     *
     * @param compid 企业
     * @param opid   用户
     * @param status 1 保存砼编号 2 材料 3 运距
     * @param json   传递来的json
     * @return jsonarray
     */
    JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json);


    JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json, Integer mark);

    /**
     * 根据UUID 查Code
     *
     * @param compid        qiye
     * @param contractUID_2 合同uuid
     * @return jsonarray
     */
    JSONArray findCContractCode(String compid, String contractUID_2);


    /**
     * 合同审核
     *
     * @param compid       企业
     * @param opid         用户
     * @param contractUID  企业uuid
     * @param verifyStatus 审核状态
     * @return json
     */
    JSONArray sp_updata_ContractIsEffective(String compid, String opid, String contractUID, Boolean verifyStatus, String ccontractcode, Integer verifytype);
}
