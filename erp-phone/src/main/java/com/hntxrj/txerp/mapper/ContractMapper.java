package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface ContractMapper {

    /**
     * 合同列表查询
     *
     * @param startTime          签订日期 开始时间
     * @param endTime            签订日期 结束时间
     * @param contractDetailCode 合同编号
     * @param eppCode            工程代号
     * @param buildCode          施工单位代号
     * @param salesMan           销售员代号
     * @param compId             企业代号
     * @return 合同列表
     */
    List<ContractListVO> getContractList(String startTime, String endTime, String contractDetailCode,
                                         String eppCode, String buildCode, String salesMan, String compId, String verifyStatus);

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
    void verifyContract(String contractUid, String compid, String opId, String verifyTime, Integer verifyStatus);

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
    List<ContractPriceMarkupVO> getContractPriceMarkupVO(String contractUid, String contractDetailCode, String compid);

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
     * 获取标号下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    List<StgIdDropDown> getStgIdDropDown(String compid);

    /**
     * 获取特殊材料下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    List<PriceMarkupDropDown> getPriceMarkupDropDown(String compid);

    /**
     * 自动生成合同id
     *
     * @param compid 企业代号
     * @return contractId
     */
    String getContractId(String compid);


    /**
     * @param compid             企业id
     * @param contractDetailCode 子合同号
     * @param contractUID        合同法UID
     * @return ContractPumperVO
     */
    List<ContractPumperVO> getContractPumperPrice(String compid, String contractDetailCode, String contractUID);

    /**
     * 合同运距
     *
     * @param compid             企业ID
     * @param contractUID        合同UID
     * @param contractDetailCode 子合同号
     * @return 站名 运输距离
     */
    ContractDistanceVO getContractDistance(String compid, String contractUID, String contractDetailCode);

    /**
     * 插入泵车类价格数据
     *
     * @param compid             企业id
     * @param opid               操作员代号
     * @param contractUID        合同uid号
     * @param contractDetailCode 子合同号
     * @param pumptype           泵车类别
     * @param pumPrice           泵送单价
     * @param tableExpense       台班费
     * @param createTime         创建时间
     * @return
     */
    Integer insertPumpTruck(String compid, String opid, String contractUID, String contractDetailCode,
                            Integer pumptype, Double pumPrice, Double tableExpense
            , String createTime);


    /**
     * 添加合同运距
     *
     * @param contractUID        合同UID号
     * @param contractDetailCode 子合同号
     * @param compid             站别代号
     * @param distance           运输距离
     * @param recStatus          标志
     * @param remarks            备注
     * @param upDown             网络标识
     * @param upDownMark         上传标识
     * @return int
     */
    Integer saveContractDistance(String contractUID, String contractDetailCode, String compid, Double distance,
                                 String remarks, Integer recStatus, Double upDown, Integer upDownMark);

    /**
     * 查询泵车列表
     *
     * @param compid
     * @return PumpTruckListVO列表
     */
    List<PumpTruckListVO> selectPumpTruckList(String compid, String builderName);


    /**
     * 保存合同加价项目
     *
     * @param compid             企业id
     * @param contractUid        主合同号
     * @param contractDetailCode 子合同号
     * @param pPCode             加价项目代号
     * @param pPName             加价项目名称
     * @param unitPrice          价格
     * @param jumpPrice          泵送价
     * @param selfDiscPrice      自卸价
     * @param towerCranePrice    塔吊价
     * @param otherPrice         其他价格
     * @param isDefault          是否默认
     */
    Integer saveContractPriceMarkup(String compid, String contractUid, String contractDetailCode, String pPCode,
                                    String pPName, BigDecimal unitPrice, BigDecimal jumpPrice,
                                    BigDecimal selfDiscPrice, BigDecimal towerCranePrice,
                                    BigDecimal otherPrice, boolean isDefault);


    /**
     * 添加任务单时根据工程名称或者施工单位查询合同列表
     *
     * @param compid     站别代号
     * @param searchName 搜索添加，可能是施工名称或者是施工单位
     * @return 合同列表
     */
    List<ContractListVO> getContractListByEppOrBuild(String compid, String searchName);

    /**
     * 修改合同运距
     *
     * @param contractUID        主合同号
     * @param contractDetailCode 子合同号
     * @param compid             站别代号
     * @param distance           运距
     * @param remarks            备注
     * @param recStatus          启用状态
     */
    void updateContractDistance(String contractUID, String contractDetailCode, String compid, Double distance, String remarks, Integer recStatus);


    /**
     * 泵车价格查询
     *
     * @param compid             企业代号
     * @param pumpType           泵车类型
     * @param contractUID        主合同
     * @param contractDetailCode 子合同
     * @return 列表查询
     */
    Integer selectPumpTruck(String compid, Integer pumpType, String contractUID, String contractDetailCode);

    /**
     * 修改泵车价格
     *
     * @param compid             企业代号
     * @param opid               操作员id
     * @param contractUID        主合同
     * @param contractDetailCode 子合同
     * @param pumpType           泵车类型
     * @param pumPrice           泵车价格
     * @param tableExpense       台班费
     * @param createTime         创建时间
     */
    void updatePumpTruck(String compid, String opid, String contractUID, String contractDetailCode, Integer pumpType, Double pumPrice, Double tableExpense, String createTime);

    /**
     * 获取合同特殊材料
     *
     * @param compid             企业id
     * @param contractUid        合同uid
     * @param contractDetailCode 子合同代号
     * @param ppCode             特殊材料编号
     * @return 合同特殊材料
     */
    String getContractPriceMarkup(String compid, String contractUid, String contractDetailCode, String ppCode);

    /**
     * 获取合同审核状态
     *
     * @param compid             企业id
     * @param contractUID        合同uid
     * @param contractDetailCode 子合同代号
     */
    Boolean getContractVerifyStatus(String contractUID, String contractDetailCode, String compid);


    /**
     * 获取主合同号
     *
     * @param compid             企业id
     * @param contractDetailCode 子合同号
     */
    String getContractUID(String compid, String contractDetailCode);


    /**
     * 工地端添加任务单时根据施工方查询关联的合同列表
     *
     * @param contractDetailCodes 子合同集合
     * @param contractUIDList     主合同集合
     * @param searchName          搜索添加，可能是施工名称或者是施工单位
     * @return 合同列表
     */
    List<ContractListVO> getBuildContractListByEppOrBuild(List<String> contractDetailCodes, List<String> contractUIDList, String searchName);

    /**
     * 获取主合同信息
     */
    HashMap<String, String> getContractMaster(String compid, String contractId, String contractUid);

    /**
     * 获取子合同信息
     */
    HashMap<String, String> getContractDetailMap(String contractUid, String contractDetailCode);
}
