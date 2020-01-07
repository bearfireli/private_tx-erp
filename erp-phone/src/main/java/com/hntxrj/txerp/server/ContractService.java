package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.Adjunct;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ContractService {
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
     * @param EPPCode     工程编号
     * @param scaleName   业务员
     * @param Compid      企业id
     * @param currPage    当前页
     * @param pageSize    页Size
     * @param beginDate   合同执行时间
     * @param endDate     合同结束时间
     *                    //     * @param ContractUID 合同UUID
     * @param ContractId  合同编号
     * @param noOver      0全部，1未完成状态
     * @param erpType     用户类型 1为销售，2为司机
     */

    JSONArray spQuerySimpleContract(Integer type, String buildercode,
                                    String EPPCode, String scaleName,
                                    String Compid, Integer currPage,
                                    Integer pageSize, String beginDate,
                                    String endDate, String ContractUID,
                                    String ContractId, Integer ContractStatus,
                                    String likename, String opid, Integer noOver, Integer erpType);

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
    JSONArray spQuerySMBusinessGroup(Integer type, Integer currPage, Integer pageSize, String department,
                                     String salecName, String compid);


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
     * - --合同砼标号   -- --查询语句：  -- select * from SM_ContractGradePriceDetail  where  CContractCode =@子合同号
     * and ContractUID=@合同UID编号 and RecStatus = 1 order by PriceETime
     *
     * @param currPage      当前页
     * @param pageSize      页长度
     * @param cContractCode 合同编号
     * @param contractUID   合同的uuid
     * @param compid        企业
     * @return json
     */
    JSONArray spQuerySMContractGradePriceDetail(String compid, Integer currPage, Integer pageSize,
                                                String cContractCode, String contractUID);

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
    JSONArray spQuerySMContractPriceMarkup(Integer currPage, Integer pageSize, String cContractCode,
                                           String contractUID, String compid);


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
    JSONArray spQuerySMPumpPriceSet(Integer currPage, Integer pageSize, String cContractCode, String contractUID,
                                    String compid, String opid);


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
    JSONArray spQuerySMContractDistanceSet(Integer currPage, Integer pageSize, String cContractCode,
                                           String contractUID, String compid, String opid);

    /**
     * 价格执行方式下拉列表
     */
    JSONArray spQueryPriceDDPublicInfo();


    /**
     * 合同砼标号添加存储过程
     *
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
     * @param createTime      创建时间
     * @return jsonArray
     */
    JSONArray spinsertUpDelSMContractGradePriceDetail(Integer mark, String compid, String cContractCode,
                                                      String contractUID, String opid, String stgid,
                                                      Double notPumpPrice, Double pumpPrice, Double towerCranePrice,
                                                      Double priceDifference, String priceETime, String createTime);

    JSONArray spinsertUpDelSMContractPriceMarkup(Integer mark, String compid, String cContractCode, String contractUID,
                                                 String ppCode, String ppName, Double unitPrice, Double jumpPrice,
                                                 Double selfDiscPrice, Double towerCranePrice, Double otherPrice,
                                                 byte isDefault);


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
     */
    JSONArray spinsertUpDelSMContractDistanceSet(Integer mark, String contractUID, String ccontractCode,
                                                 String compid, Double distance, Integer recStatus, String remarks);


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
     * @param PageSize     页长度
     * @param mark         1  砼价格  2  特殊材料  3泵车车价格
     * @param stgid        线号
     * @param ppname       加价项目名称
     * @param pumptypename @return jsonArray
     */
    JSONArray spQuerySMStgidInfoPrice(String compid, Integer currPage, Integer PageSize, Integer mark,
                                      String stgid, String ppname, String pumptypename);


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
    JSONArray sp_updata_ContractIsEffective(String compid, String opid, String contractUID, Boolean verifyStatus,
                                            String ccontractcode, Integer verifytype);


    /**
     * 合同列表查询
     *
     * @param startTime    签订日期 开始时间
     * @param endTime      签订日期 结束时间
     * @param contractCode 合同编号
     * @param eppCode      工程代号
     * @param buildCode    施工单位代号
     * @param salesManCode     销售员代号
     * @param compId       企业代号
     * @param page         页码
     * @param pageSize     每页数量
     * @return 合同列表
     */
    PageVO<ContractListVO> getContractList(Long startTime, Long endTime, String contractCode,
                                           String eppCode, String buildCode, String salesManCode,
                                           String compId, String verifyStatus, Integer page, Integer pageSize);

    /**
     * 获取合同详情
     *
     * @param contractUid 合同uid
     * @param compid      企业id
     * @return 合同详情
     */
    ContractVO getContractDetail(String contractDetailCode, String contractUid, String compid);

    /**
     * 审核/取消审核 合同
     *
     * @param contractUid  合同代号
     * @param compid       企业id
     * @param opId         操作员代号
     * @param verifyStatus 审核状态
     */
    void verifyContract(String contractUid, String compid, String opId, Integer verifyStatus);

    /**
     * 获取合同砼价格列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 子合同代号
     * @return 砼价格列表
     */
    List<ContractGradePriceVO> getContractGradePrice(String contractUid, String contractDetailCode, String compid);


    /**
     * 获取合同特殊材料列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 子合同代号
     * @param compid             企业id
     * @return 合同特殊材料列表
     */
    List<ContractPriceMarkupVO> getContractPriceMarkup(String contractUid, String contractDetailCode, String compid);


    /**
     * 获取合同泵送价格列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 合同子合同号
     * @param compid             企业id
     * @return 合同泵送价格列表
     */
    List<ContractPumpPriceVO> getContractPumpPrice(String contractUid, String contractDetailCode, String compid);

    /**
     * 获取合同运距
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 合同子合同号
     * @param compid             企业id
     */
    List<ContractDistanceSetVO> getContractDistanceSet(String contractUid, String contractDetailCode, String compid);

    /**
     * 获取合同类别
     *
     * @return 合同类别下拉
     */
    List<DropDownVO> getContractTypeDropDown(String compid);

    /**
     * 合同价格执行方式
     *
     * @param compid 企业id
     * @return 合同价格执行方式下拉
     */
    List<DropDownVO> getPriceTypeDropDown(String compid);

    /**
     * 添加合同
     *
     * @param contractId   主合同号
     * @param salesman     业务员代号
     * @param signDate     签订日期
     * @param expiresDate  新版本到期时间
     * @param effectDate   老版本到期时间
     * @param contractType 合同类别
     * @param priceStyle   价格执行方式
     * @param eppCode      工程代号
     * @param builderCode  施工单位代号
     * @param contractNum  合同方量
     * @param preNum       预付方量
     * @param preMoney     预付金额
     * @param remarks      备注
     * @param compid       公司代号
     * @param address      交货地址
     */
    void addContract(String contractId, String salesman, Date signDate, Date expiresDate, Date effectDate,
                     Integer contractType, Integer priceStyle, String eppCode,
                     String builderCode, BigDecimal contractNum, BigDecimal preNum,
                     BigDecimal preMoney, String remarks, String compid, String address) throws ErpException;

    /**
     * 上传合同附件
     *
     * @param contractUid   合同uid
     * @param contractDetailCode 子合同代号
     * @param num           第几个文件
     * @param file          文件对象
     * @param compid        企业id
     * @return 保存后的该合同附件内容
     * @throws ErpException 上传失败
     */
    List<Adjunct> uploadAdjunct(String contractUid, String contractDetailCode,
                                Integer num, MultipartFile file, String compid) throws ErpException;


    /**
     * 删除某附件
     *
     * @param fileUid 上传后在服务器的文件名称（一个uid）
     * @return 删除后该合同的附件列表
     */
    List<Adjunct> delAdjunct(String fileUid);

    /**
     * 获取附件列表
     *
     * @param contractUid   合同uid
     * @param contractDetailCode 子合同代号
     * @param compid        企业id
     * @return 保存后的该合同附件内容
     */
    List<Adjunct> getAdjunct(String contractUid, String contractDetailCode, String compid);


    /**
     * 获取附件
     *
     * @param fileUid  文件uid
     * @param response http resp
     */
    void getAdjunctItem(String fileUid, HttpServletResponse response) throws ErpException;

    /**
     * 禁用合同（此接口还未启用）
     *
     * @param contractUid        主合同
     * @param contractDetailCode 子合同
     * @param compid             企业
     */
    void disableContract(String contractUid, String contractDetailCode, String compid);

    /**
     * 获取标号下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    List<StgIdDropDown> getContractStgIdDropDown(String compid);

    /**
     * 保存合同标号价格
     *
     * @param compid             企业id
     * @param contractUid        主合同号
     * @param contractDetailCode 子合同号
     * @param gradePriceVO         砼标号价格对象
     */
    void saveContractGradePrice(String compid, String contractUid,
                                String contractDetailCode, String gradePriceVO) throws ErpException;

    /**
     * 获取特殊材料下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    List<PriceMarkupDropDown> getPriceMarkupDropDown(String compid);

    /**
     * 保存合同加价项目
     *
     * @param compid             企业id
     * @param contractUid        主合同号
     * @param contractDetailCode 子合同号
     * @param priceMarkup        加价项目
     */
    void saveContractPriceMarkup(String compid, String contractUid, String contractDetailCode,
                                 String priceMarkup) throws ErpException;


    /**
     * 自动生成合同id
     *
     * @param compid 企业代号
     * @return contractId
     */
    String makeAutoContractId(String compid) throws ErpException;

    /**
     * @param compid        企业id
     * @param contractDetailCode 子合同号
     * @param contractUID   合同法UID
     *
     * @return ContractPumperVO
     */
    List<ContractPumperVO> getContractPumperPrice(String compid, String contractDetailCode, String contractUID) throws ErpException;


    /**
     * 合同运距
     *
     * @param compid        企业ID
     * @param contractUID   合同UID
     * @param contractDetailCode 子合同号
     * @return 站名 运输距离
     */
    ContractDistanceVO getContractDistance(String compid, String contractUID, String contractDetailCode) throws ErpException;


    /**
     * 添加合同运距
     *
     * @param contractUID   合同UID号
     * @param contractDetailCode 子合同号
     * @param compid        站别代号
     * @param distance      运输距离
     * @param recStatus     标志
     * @param remarks       备注
     * @param upDown        网络标识
     * @param upDownMark    上传标识
     * @return int
     */
    ResultVO saveContractDistance(String contractUID, String contractDetailCode, String compid, Double distance,
                                  String remarks,
                                  Integer recStatus, Double upDown,
                                  Integer upDownMark) throws ErpException;

    /**
     * 泵车类价格插入数据
     * @param compid       企业代码
     * @param opid         操作员代号
     * @param contractUID  合同uid号
     * @param contractDetailCode 子合同号
     * @param pumptype     泵车类别
     * @param pumPrice     泵送单价
     * @param tableExpense 台班费
     * @return Integer
     */
    ResultVO insertPumpTruck(String compid, String opid, String contractUID, String contractDetailCode,
                             Integer pumptype, Double pumPrice, Double tableExpense
    ) throws ErpException;

    /**
     * 泵车列表查询
     *
     * @param compid      企业代号
     * @param builderName 工程名称
     * @param page        页码
     * @param pageSize    每页数量
     * @return 列表查询
     */
    PageVO<PumpTruckListVO> selectPumpTruckList(String compid, Integer page, Integer pageSize, String builderName);

    /**
     * 添加任务单时根据工程名称或者施工单位查询合同列表
     *
     * @param compid     站别代号
     * @param searchName 搜索添加，可能是施工名称或者是施工单位
     * @param page       页码
     * @param pageSize   每页数量
     * @return 合同列表
     */
    PageVO<ContractListVO> getContractListByEppOrBuild(String compid, String searchName, Integer page, Integer pageSize);


    /**
     * 工地端添加任务单时根据施工方查询关联的合同列表
     *
     * @param buildId    施工方id
     * @param searchName 搜索添加，可能是施工名称或者是施工单位
     * @param page       页码
     * @param pageSize   每页数量
     * @return 合同列表
     */
    PageVO<ContractListVO> getBuildContractListByEppOrBuild(Integer buildId, String searchName, Integer page, Integer pageSize);
}
