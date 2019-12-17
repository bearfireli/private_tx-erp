package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface ContractMapper {

    List<ContractListVO> getContractList(String startTime, String endTime, String contractCode,
                                         String eppCode, String buildCode, String salesMan, String compId, String verifyStatus);


    ContractVO getContractDetail(String contractDetailCode, String contractUid, String compid);

    void verifyContract(String contractUid, String compid, String opId, String verifyTime, Integer verifyStatus);

    List<ContractGradePriceVO> getContractGradePrice(String contractUid, String contractDetailCode, String compid);

    List<ContractPriceMarkupVO> getContractPriceMarkupVO(String contractUid, String contractDetailCode, String compid);

    List<ContractPumpPriceVO> getContractPumpPrice(String contractUid, String contractDetailCode, String compid);

    List<ContractDistanceSetVO> getContractDistanceSet(String contractUid, String contractDetailCode, String compid);

    List<StgIdDropDown> getStgIdDropDown(String compid);

    List<PriceMarkupDropDown> getPriceMarkupDropDown(String compid);

    String getContractId(String compid);


    List<ContractPumperVO> getContractPumperPrice(String compid, String cContractCode, String contractUID);

    ContractDistanceVO getContractDistance(String compid, String contractUID, String cContractCode);

    /**
     * 插入泵车类价格数据
     *
     * @param compid
     * @param opid         操作员代号
     * @param contractUID  合同uid号
     * @param contractCode 子合同号
     * @param pumptype     泵车类别
     * @param pumPrice     泵送单价
     * @param tableExpense 台班费
     * @param createTime   创建时间
     * @return
     */
    Integer insertPumpTruck(String compid, String opid, String contractUID, String contractCode,
                            Integer pumptype, Double pumPrice, Double tableExpense
                         , String createTime);






    Integer saveContractDistance(String contractUID,String cContractCode,String compid,Double distance,
                                 String remarks,Integer recStatus,Double upDown,Integer upDownMark);

    /**
     * 查询泵车列表
     * @param compid
     * @return PumpTruckListVO列表
     */
    List<PumpTruckListVO> selectPumpTruckList(String compid,String builderName);


    Integer saveContractPriceMarkup(String compid, String contractUid, String contractDetailCode, String pPCode,
                                    String pPName,BigDecimal unitPrice, BigDecimal jumpPrice,
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

    void updateContractDistance(String contractUID, String cContractCode, String compid, Double distance, String remarks, Integer recStatus);

    Integer selectPumpTruck(String compid, Integer pumpType,String contractUID,String contractCode);

    /*修改合同泵车价格*/
    void updatePumpTruck(String compid, String opid, String contractUID, String contractCode, Integer pumptype, Double pumPrice, Double tableExpense, String createTime);

    String getContractPriceMarkup(String compid, String contractUid, String contractDetailCode, String pPCode);

    /*查询合同的审核状态*/
    Boolean getContractVerifyStatus(String contractUID, String cContractCode, String compid);


    String getContractUID(String compid, String code);

    List<ContractListVO> getBuildContractListByEppOrBuild(List<String> ccontractCodes, List<String> contractUIDList, String searchName);
}
