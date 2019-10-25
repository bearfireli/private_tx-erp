package com.hntxrj.mapper;

import com.hntxrj.entity.ContractMaster;
import com.hntxrj.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface ContractMapper {

    List<ContractListVO> getContractList(String startTime, String endTime, String contractCode,
                                         String eppCode, String buildCode, String salesMan, String compId,String verifyStatus);


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
}
