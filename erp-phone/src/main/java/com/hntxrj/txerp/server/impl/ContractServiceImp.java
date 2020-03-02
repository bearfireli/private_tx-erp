package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.ContractDao;
import com.hntxrj.txerp.im.MsgService;
import com.hntxrj.txerp.mapper.*;
import com.hntxrj.txerp.repository.ContractGradePriceDetailRepository;
import com.hntxrj.txerp.server.ContractService;
import com.hntxrj.txerp.entity.Adjunct;
import com.hntxrj.txerp.entity.ContractDetail;
import com.hntxrj.txerp.entity.ContractGradePriceDetail;
import com.hntxrj.txerp.entity.ContractMaster;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.util.EntityTools;
import com.hntxrj.txerp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Scope("prototype")
@Slf4j
public class ContractServiceImp implements ContractService {

    private final ContractDao dao;

    private final ContractMapper contractMapper;

    private final PublicInfoMapper publicInfoMapper;

    private final ContractMasterMapper contractMasterMapper;
    private final ContractDetailMapper contractDetailMapper;

    private final AdjunctMapper adjunctMapper;

    private final ContractGradePriceDetailRepository contractGradePriceDetailRepository;

    private final ConstructionMapper constructionMapper;

    private final MsgService msgService;
    private final MsgMapper msgMapper;

    @Value("${app.spterp.contractAdjunctPath}")
    private String contractAdjunctPath;

    @Autowired
    public ContractServiceImp(ContractDao dao, ContractMapper contractMapper, PublicInfoMapper publicInfoMapper,
                              ContractMasterMapper contractMasterMapper, ContractDetailMapper contractDetailMapper,
                              AdjunctMapper adjunctMapper,
                              ContractGradePriceDetailRepository contractGradePriceDetailRepository,
                              ConstructionMapper constructionMapper, MsgService msgService, MsgMapper msgMapper) {
        this.dao = dao;
        this.contractMapper = contractMapper;
        this.publicInfoMapper = publicInfoMapper;
        this.contractMasterMapper = contractMasterMapper;
        this.contractDetailMapper = contractDetailMapper;
        this.adjunctMapper = adjunctMapper;
        this.contractGradePriceDetailRepository = contractGradePriceDetailRepository;
        this.constructionMapper = constructionMapper;
        this.msgService = msgService;
        this.msgMapper = msgMapper;
    }


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
    @Override
    public JSONArray contractDateInsertUpDel(int mark, String compid, String opId, String eppCode,
                                             String CreateTime_1, String EPPName_2, String Address_3,
                                             String LinkMan_4, String LinkTel_5, String Remarks_6, Byte RecStatus_7,
                                             String shortName_8) {
        return dao.contractDateInsertUpDel(mark, compid, opId, eppCode, CreateTime_1, EPPName_2, Address_3,
                LinkMan_4, LinkTel_5, Remarks_6, RecStatus_7, shortName_8);

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
     * @return 返回JSONArray
     */
    @Override
    public JSONArray insertSimpleContractSave(Integer mark, String ContractId_1, String ContractUID_2,
                                              String SignDate3, String ExpiresDate_4, String Salesman_5,
                                              int ContractType_6, int PriceStyle_7, Double ContractNum_8,
                                              Double PreNum_9, Double PreMoney_10, String EPPCode_11,
                                              String BuilderCode_12, String Remarks_13, int IsEffective_14,
                                              String OpId_15, String compid) {
        return dao.insertSimpleContractSave(mark, ContractId_1, ContractUID_2, SignDate3, ExpiresDate_4,
                Salesman_5, ContractType_6, PriceStyle_7, ContractNum_8, PreNum_9, PreMoney_10, EPPCode_11,
                BuilderCode_12, Remarks_13, IsEffective_14, OpId_15, compid);
    }


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
     * @param ContractUID 合同UUID
     * @param ContractId  合同编号
     */
    @Override
    public JSONArray spQuerySimpleContract(Integer type, String buildercode,
                                           String EPPCode, String scaleName,
                                           String Compid, Integer currPage,
                                           Integer pageSize, String beginDate,
                                           String endDate, String ContractUID,
                                           String ContractId, Integer ContractStatus,
                                           String likename, String opid, Integer noOver, Integer erpType) {
        return dao.spQuerySimpleContract(
                type, buildercode, EPPCode, scaleName,
                Compid, currPage, pageSize, beginDate,
                endDate, ContractUID, ContractId,
                ContractStatus, likename, opid, noOver, erpType);
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
        return dao.spQuerySimpleContractRem(compid, contractUID);
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
        return dao.spQuerySMBusinessGroup(type, currPage, pageSize, department, salecName, compid);
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
        return dao.spQueryDD_PublicInfo(type, currPage, pageSize, compid);
    }

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
    @Override
    public JSONArray spQuerySMContractGradePriceDetail(String compid, Integer currPage, Integer pageSize,
                                                       String cContractCode, String contractUID) {
        return dao.spQuerySMContractGradePriceDetail(compid, currPage, pageSize, cContractCode, contractUID);
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
        return dao.spQuerySMContractPriceMarkup(currPage, pageSize, cContractCode, contractUID, compid);
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
        return dao.spQuerySMPumpPriceSet(currPage, pageSize, cContractCode, contractUID, compid, opid);
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
        return dao.spQuerySMContractDistanceSet(currPage, pageSize, cContractCode, contractUID, compid, opid);
    }

    /**
     * spQueryPriceDDPublicInfo
     */
    @Override
    public JSONArray spQueryPriceDDPublicInfo() {
        return dao.spQueryPriceDDPublicInfo();
    }


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
    @Override
    public JSONArray spinsertUpDelSMContractGradePriceDetail(Integer mark, String compid, String cContractCode,
                                                             String contractUID, String opid, String stgid,
                                                             Double notPumpPrice, Double pumpPrice,
                                                             Double towerCranePrice, Double priceDifference,
                                                             String priceETime, String createTime) {
        return dao.spinsertUpDelSMContractGradePriceDetail(mark, compid, cContractCode, contractUID, opid,
                stgid, notPumpPrice, pumpPrice, towerCranePrice, priceDifference, priceETime, createTime);
    }

    @Override
    public JSONArray spinsertUpDelSMContractPriceMarkup(Integer mark, String compid, String cContractCode,
                                                        String contractUID, String ppCode, String ppName,
                                                        Double unitPrice, Double jumpPrice, Double selfDiscPrice,
                                                        Double towerCranePrice, Double otherPrice, byte isDefault) {
        return dao.spinsertUpDelSMContractPriceMarkup(mark, compid, cContractCode, contractUID, ppCode,
                ppName, unitPrice, jumpPrice, selfDiscPrice, towerCranePrice, otherPrice, isDefault);
    }



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
    @Override
    public JSONArray spinsertUpDelSMContractDistanceSet(Integer mark, String contractUID, String ccontractCode,
                                                        String compid, Double distance, Integer recStatus,
                                                        String remarks) {
        return dao.spinsertUpDelSMContractDistanceSet(mark, contractUID, ccontractCode, compid, distance,
                recStatus, remarks);
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
        return dao.spQueryDDStirInfoSet(compid, currPage, pageSize);
    }


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
    @Override
    public JSONArray spQuerySMStgidInfoPrice(String compid, Integer currPage, Integer PageSize, Integer mark,
                                             String stgid, String ppname, String pumptypename) {
        return dao.spQuerySMStgidInfoPrice(compid, currPage, PageSize, mark, stgid, ppname, pumptypename);
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
    public JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json) {
        return dao.sp_QueryLIST_Json(compid, opid, status, json);
    }

    @Override
    public JSONArray sp_QueryLIST_Json(String compid, String opid, Integer status, String json, Integer mark) {
        return dao.sp_QueryLIST_Json(compid, opid, status, json, mark);
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
        return dao.findCContractCode(compid, contractUID_2);
    }


    /**
     * 合同审核
     *
     * @param compid       企业
     * @param opid         用户
     * @param contractUID  企业uuid
     * @param verifyStatus 审核状态
     * @return json
     */
    @Override
    public JSONArray sp_updata_ContractIsEffective(String compid, String opid, String contractUID,
                                                   Boolean verifyStatus, String ccontractcode, Integer verifytype) {
        return dao.sp_updata_ContractIsEffective(compid, opid, contractUID, verifyStatus, ccontractcode, verifytype);
    }

    @Override
    public PageVO<ContractListVO> getContractList(Long startTime, Long endTime, String contractCode,
                                                  String eppCode, String buildCode, String salesMan, String compId,
                                                  String verifyStatus, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize, "SignDate desc");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = startTime == null ? null : sdf.format(new Date(startTime));
        String endTimeStr = endTime == null ? null : sdf.format(new Date(endTime));
        log.info("【合同列表】startTime:{}, endTime:{}, contractCode:{}, " +
                        "eppCode:{}, buildCode:{}, salesMan:{}, compid:{},verifyStatus:{} page:{}, pageSize:{}",
                startTimeStr, endTimeStr, contractCode, eppCode, buildCode, salesMan, compId, verifyStatus,
                page, pageSize);

        List<ContractListVO> contractListVOList = contractMapper.getContractList(
                startTimeStr, endTimeStr, contractCode, eppCode, buildCode, salesMan, compId, verifyStatus);
        PageInfo<ContractListVO> pageInfo = new PageInfo<>(contractListVOList);
        PageVO<ContractListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public ContractVO getContractDetail(String contractDetailCode, String contractUid, String compid) {
        return contractMapper.getContractDetail(contractDetailCode, contractUid, compid);
    }

    @Override
    public void verifyContract(String contractUid, String compid,
                               String opId, Integer verifyStatus) throws ErpException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        contractMapper.verifyContract(contractUid, compid, opId, sdf.format(new Date()), verifyStatus);
        int typeId = 3;
        List<RecipientVO> recipoentList = msgMapper.getRecipoentList(compid,typeId);
        for (RecipientVO r : recipoentList) {
            SendmsgVO sendmsgVO = new SendmsgVO();
            sendmsgVO.setSyncOtherMachine(2);
            sendmsgVO.setToAccount(r.getPhone());
            sendmsgVO.setMsgLifeTime(7);
            String  msgContent ="合同号：["+contractUid+"]已审核.";
            sendmsgVO.setMsgContent(msgContent);
            msgService.sendMsg(sendmsgVO);
        }
    }

    @Override
    public List<ContractGradePriceVO> getContractGradePrice(
            String contractUid, String contractDetailCode, String compid) {
        return contractMapper.getContractGradePrice(contractUid, contractDetailCode, compid);
    }

    @Override
    public List<ContractPriceMarkupVO> getContractPriceMarkup(String contractUid,
                                                              String contractDetailCode, String compid) {
        return contractMapper.getContractPriceMarkupVO(contractUid, contractDetailCode, compid);
    }

    @Override
    public List<ContractPumpPriceVO> getContractPumpPrice(String contractUid, String contractDetailCode,
                                                          String compid) {
        return contractMapper.getContractPumpPrice(contractUid, contractDetailCode, compid);
    }

    @Override
    public List<ContractDistanceSetVO> getContractDistanceSet(String contractUid, String contractDetailCode,
                                                              String compid) {
        return contractMapper.getContractDistanceSet(contractUid, contractDetailCode, compid);
    }

    @Override
    public List<DropDownVO> getContractTypeDropDown(String compid) {
        return publicInfoMapper.getDropDown(46, compid);
    }

    @Override
    public List<DropDownVO> getPriceTypeDropDown(String compid) {
        return publicInfoMapper.getDropDown(47, compid);
    }

    @Override
    public void addContract(String contractId, String salesman, Date signDate, Date expiresDate, Date effectDate,
                            Integer contractType, Integer priceStyle, String eppCode,
                            String builderCode, BigDecimal contractNum, BigDecimal preNum,
                            BigDecimal preMoney, String remarks, String compid, String address, String linkMan,
                            String linkTel, String opid) throws ErpException {
        if (compid == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_COMPID);
        }
        if (contractId == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_CONTRACTID);
        }

        if (eppCode == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_EPPCODE);
        }
        if (builderCode == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_BUILDERCODE);
        }

        ContractMaster contractMaster = new ContractMaster();
        ContractDetail contractDetail = new ContractDetail();

        String contractMasterUid = UUID.randomUUID().toString();
        contractMaster.setContractUid(contractMasterUid);
        contractMaster.setCreateTime(new Date());
        contractMaster.setRecStatus("1");
        contractMaster.setCompid(compid);
        contractMaster.setContractId(contractId);
        contractMaster.setContractStatus(0);
        contractMaster.setContractType(contractType);
        contractMaster.setSalesman(salesman);
        contractMaster.setLinkMan(linkMan);
        contractMaster.setLinkTel(linkTel);

        if (expiresDate != null) {
            contractMaster.setExpiresDate(expiresDate);
        }
        if (effectDate != null) {
            contractMaster.setExpiresDate(effectDate);
        }

        contractMaster.setSignDate(signDate);
        contractMaster.setPriceStyle(priceStyle);

        contractMaster.setOpId(opid);


        contractDetail.setCContractCode(contractMaster.getContractId() + "-01");
        contractDetail.setContractUid(contractMasterUid);
        contractDetail.setCreateTime(new Date());
        contractDetail.setRecStatus("1");
        contractDetail.setPreMoney(preMoney);
        contractDetail.setPreNum(preNum);
        contractDetail.setRemarks(remarks);
        contractDetail.setContractNum(contractNum);
        contractDetail.setBuilderCode(builderCode);
        contractDetail.setEppCode(eppCode);
        contractDetail.setCompid(compid);
        contractDetail.setAddress(address);

        contractDetail.setOpId(opid);

        EntityTools.setEntityDefaultValue(contractMaster);
        contractMasterMapper.insert(contractMaster);
        EntityTools.setEntityDefaultValue(contractDetail);
        contractDetailMapper.insert(contractDetail);
        int typeId = 3;
        List<RecipientVO> recipoentList = msgMapper.getRecipoentList(compid,typeId);
        for (RecipientVO r : recipoentList) {
            SendmsgVO sendmsgVO = new SendmsgVO();
            sendmsgVO.setSyncOtherMachine(2);
            sendmsgVO.setToAccount(r.getPhone());
            sendmsgVO.setMsgLifeTime(7);
            String  msgContent ="有新合同,请审核";
            sendmsgVO.setMsgContent(msgContent);
            msgService.sendMsg(sendmsgVO);
        }
    }

    @Override
    public List<Adjunct> uploadAdjunct(String contractUid, String ccontractCode,
                                       Integer num, MultipartFile file, String compid) throws ErpException {
        String adjunctKey = contractUid + ccontractCode;

        if (file == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_ADJUNCT_UPLOAD_FILE_CONNOT_NULL);
        }
        String localFileName = UUID.randomUUID().toString();
        if (!new File(contractAdjunctPath).exists()) {
            boolean mkdirs = new File(contractAdjunctPath).mkdirs();
            if (!mkdirs) {
                log.info("添加文件夹失败");
            }
        }
        File localFile = new File(contractAdjunctPath + localFileName);
        try {
            boolean newFile = localFile.createNewFile();
            if (!newFile) {
                log.info("文件创建失败");
            }

            IOUtils.copy(file.getInputStream(), new FileOutputStream(localFile));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_CONTRACT_ADJUNCT_UPLOAD_ERROR);
        }

        Adjunct adjunct = new Adjunct();
        adjunct.setAdjunctName(file.getOriginalFilename());
        adjunct.setAdjunctKey(adjunctKey);
        adjunct.setAdjunctNum(num == null ? 0 : num);
        adjunct.setAdjunctType(1);
        adjunct.setCompid(compid);
        adjunct.setAdjunctFileName(localFileName);

        try {
            adjunctMapper.insert(adjunct);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_CONTRACT_ADJUNCT_UPLOAD_ERROR);
        }

        return adjunctMapper.getAdjunctList(compid, 1, adjunctKey);
    }

    @Override
    public List<Adjunct> delAdjunct(String fileUid) {
        Adjunct adjunct = adjunctMapper.getAdjunctByFileName(fileUid);
        adjunctMapper.deleteById(adjunct.getAdjunctId());
        String adjunctPath = contractAdjunctPath + adjunct.getAdjunctFileName();
        File file = new File(adjunctPath);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) {
                log.info("文件删除失败");
            }
        }
        return adjunctMapper.getAdjunctList(adjunct.getCompid(), 1, adjunct.getAdjunctKey());
    }

    @Override
    public List<Adjunct> getAdjunct(String contractUid, String ccontractCode, String compid) {
        String adjunctKey = contractUid + ccontractCode;
        return adjunctMapper.getAdjunctList(compid, 1, adjunctKey);

    }

    @Override
    public void getAdjunctItem(String fileUid, HttpServletResponse response) throws ErpException {
        Adjunct adjunct = adjunctMapper.getAdjunctByFileName(fileUid);
        if (adjunct == null) {
            throw new ErpException(ErrEumn.ADJUNCT_NOT_EXIST);
        }

        String adjunctPath = contractAdjunctPath + adjunct.getAdjunctFileName();
        File file = new File(adjunctPath);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.ADJUNCT_NOT_EXIST);
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + adjunct.getAdjunctName());
        response.setContentType("application/octet-stream");
        try {
            OutputStream ops = response.getOutputStream();
            IOUtils.copy(new FileInputStream(file), ops);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADJUNCT_STREAM_ERROR);
        }

    }

    @Override
    public void disableContract(String contractUid, String contractDetailCode, String compid) {

    }

    @Override
    public List<StgIdDropDown> getContractStgIdDropDown(String compid) {
        return contractMapper.getStgIdDropDown(compid);
    }

    @Override
    public void saveContractGradePrice(String compid, String contractUid, String contractDetailCode, String gradePrice)
            throws ErpException {
        log.info("gradePrice={}", gradePrice);
        JSONArray array = JSONArray.parseArray(gradePrice);
        //判断合同是否审核，如果已经审核，则无法增加砼价格。
        if (getContractVerifyStatus(contractUid, contractDetailCode, compid)) {
            throw new ErpException(ErrEumn.VERIFY_CONTRACT_ERROR);
        }
        List<ContractGradePriceDetail> contractGradePriceDetails = new ArrayList<>();
        for (Object obj : array) {
            ContractGradePriceDetail contractGradePriceDetail = new ContractGradePriceDetail();
            contractGradePriceDetail.setCompid(compid);
            contractGradePriceDetail.setContractUid(contractUid);
            contractGradePriceDetail.setCContractCode(contractDetailCode);
            contractGradePriceDetail.setVerifyStatus(true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            contractGradePriceDetail.setVerifyTime(sdf.format(new Date()));
            contractGradePriceDetail.setBuilderCode("0225");
            contractGradePriceDetail.setOpId("0225");
            contractGradePriceDetail.setCreateTime(sdf.format(new Date()));
            JSONObject item = JSONObject.parseObject(obj.toString());
            contractGradePriceDetail.setPumpPrice(new BigDecimal(item.getString("pumpPrice")));
            contractGradePriceDetail.setNotPumpPrice(new BigDecimal(item.getString("notPumpPrice")));
            contractGradePriceDetail.setTowerCranePrice(new BigDecimal(item.getString("towerCranePrice")));
            contractGradePriceDetail.setPriceETime(sdf.format(new Date(item.getLong("priceETime"))));
            contractGradePriceDetail.setStgId(item.getString("stgId"));
            contractGradePriceDetail.setPriceStopTime(sdf.format(new Date(0)));
            contractGradePriceDetail.setRecStatus(true);
            contractGradePriceDetail.setUpDown(0);
            EntityTools.setEntityDefaultValue(contractGradePriceDetail);
            contractGradePriceDetails.add(contractGradePriceDetail);
        }

        try {
            log.info("【保存合同砼价格】contractGradePriceDetails={}", contractGradePriceDetails);
            contractGradePriceDetailRepository.saveAll(contractGradePriceDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_CONTRACT_GRADE_PRICE_ERROR);
        }

    }

    @Override
    public List<PriceMarkupDropDown> getPriceMarkupDropDown(String compid) {
        return contractMapper.getPriceMarkupDropDown(compid);
    }

    @Override
    public void saveContractPriceMarkup(String compid, String contractUid,
                                        String contractDetailCode, String priceMarkup) throws ErpException {
        //判断合同是否审核，如果已经审核，则无法增加特殊材料。
        if (getContractVerifyStatus(contractUid, contractDetailCode, compid)) {
            throw new ErpException(ErrEumn.VERIFY_CONTRACT_ERROR);
        }

        List<PriceMarkupDropDown> priceMarkupDropDowns = JSONArray.parseArray(priceMarkup, PriceMarkupDropDown.class);
        try {
            for (PriceMarkupDropDown p : priceMarkupDropDowns) {
                String pPCode = p.getPCode();
                String pPName = p.getPName();
                BigDecimal unitPrice = p.getUnitPrice();
                BigDecimal jumpPrice = p.getJumpPrice();
                BigDecimal selfDiscPrice = p.getSelfDiscPrice();
                BigDecimal towerCranePrice = p.getTowerCranePrice();
                BigDecimal otherPrice = p.getOtherPrice();
                boolean isDefault = p.isDefault();
                String ppcode = contractMapper.getContractPriceMarkup(compid, contractUid, contractDetailCode, pPCode);
                if (ppcode == null) {
                    contractMapper.saveContractPriceMarkup(compid, contractUid, contractDetailCode, pPCode, pPName,
                            unitPrice, jumpPrice, selfDiscPrice,
                            towerCranePrice, otherPrice, isDefault);
                    log.info("【添加特殊材料】priceMarkupDropDowns={}", priceMarkupDropDowns);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_CONTRACTPRICEMARKUP_ERROR);
        }
    }

    @Override
    public String makeAutoContractId(String compid) throws ErpException {
        if (compid == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_COMPID);
        }
        return contractMapper.getContractId(compid);
    }

    /**
     * @param compid        企业id
     * @param cContractCode 子合同号
     * @param contractUID   合同法UID
     * @return ContractPumperVO
     */
    @Override
    public List<ContractPumperVO> getContractPumperPrice(String compid, String cContractCode,
                                                         String contractUID) throws ErpException {
        if (compid == null) {
            throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_COMPID);
        }
        return contractMapper.getContractPumperPrice(compid, cContractCode, contractUID);
    }


    /**
     * 合同运距
     *
     * @param compid        企业ID
     * @param contractUID   合同UID
     * @param cContractCode 子合同号
     * @return 站名 运输距离
     */
    @Override
    public ContractDistanceVO getContractDistance(String compid, String contractUID, String cContractCode) {
        return contractMapper.getContractDistance(compid, contractUID, cContractCode);
    }


    /**
     * 添加合同运距
     *
     * @param contractUID   合同UID号
     * @param cContractCode 子合同号
     * @param compid        站别代号
     * @param distance      运输距离
     * @param recStatus     标志   0:禁用； 1：启用
     * @param remarks       备注
     * @param upDown        网络标识
     * @param upDownMark    网络下发标识
     * @return int
     */
    @Override
    public ResultVO saveContractDistance(String contractUID, String cContractCode, String compid,
                                         Double distance, String remarks, Integer recStatus,
                                         Double upDown, Integer upDownMark) throws ErpException {

        //判断合同是否审核，如果已经审核，则无法添加合同运距。
        if (getContractVerifyStatus(contractUID, cContractCode, compid)) {
            throw new ErpException(ErrEumn.VERIFY_CONTRACT_ERROR);
        }

        try {

            ContractDistanceVO contractDistance = contractMapper.getContractDistance(compid, contractUID, cContractCode);
            if (contractDistance == null) {
                //用户要新增合同运距
                contractMapper.saveContractDistance(contractUID, cContractCode, compid,
                        distance, remarks, recStatus, upDown, upDownMark);
            } else {
                contractMapper.updateContractDistance(contractUID, cContractCode, compid,
                        distance, remarks, recStatus);
            }

        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_CONTRACTDISTANCESET_ERROR);
        }
        return null;
    }


    /**
     * 泵车类添加
     *
     * @param compid             企业代码
     * @param opid               操作员代号
     * @param contractUID        合同uid号
     * @param contractDetailCode 子合同号
     * @param pumpType           泵车类别
     * @param pumPrice           泵送单价
     * @param tableExpense       台班费
     */
    @Override
    @Transactional
    public ResultVO insertPumpTruck(String compid, String opid, String contractUID, String contractDetailCode,
                                    Integer pumpType, Double pumPrice, Double tableExpense) throws ErpException {

        //判断合同是否审核，如果已经审核，则无法添加泵车价格。
        if (getContractVerifyStatus(contractUID, contractDetailCode, compid)) {
            throw new ErpException(ErrEumn.VERIFY_CONTRACT_ERROR);
        }
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(date);

            Integer pump = contractMapper.selectPumpTruck(compid, pumpType, contractUID, contractDetailCode);
            if (pump == null) {
                Integer row = contractMapper.insertPumpTruck(compid, opid, contractUID, contractDetailCode,
                        pumpType, pumPrice, tableExpense, createTime);
                if (row == null || row == 0) {
                    throw new ErpException(ErrEumn.ADJUNCT_SAVE_ERROR);
                }
            } else {
                contractMapper.updatePumpTruck(compid, opid, contractUID, contractDetailCode,
                        pumpType, pumPrice, tableExpense, createTime);
            }

        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADJUNCT_SAVE_ERROR);
        }
        return null;

    }

    /**
     * 查询泵车列表
     *
     * @param compid   企业代码
     * @param page     开始页
     * @param pageSize 结束页
     */
    @Override
    public PageVO<PumpTruckListVO> selectPumpTruckList(String compid, Integer page, Integer pageSize,
                                                       String builderName) {
        PageHelper.startPage(page, pageSize);
        List<PumpTruckListVO> pumpTruckListVOList = contractMapper.selectPumpTruckList(compid, builderName);
        PageInfo<PumpTruckListVO> pageInfo = new PageInfo<>(pumpTruckListVOList);
        PageVO<PumpTruckListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 添加任务单时根据工程名称或者施工单位查询合同列表
     *
     * @param compid     站别代号
     * @param searchName 搜索添加，可能是施工名称或者是施工单位
     * @param page       页码
     * @param pageSize   每页数量
     * @return 合同列表
     */
    @Override
    public PageVO<ContractListVO> getContractListByEppOrBuild(String compid, String searchName, Integer page,
                                                              Integer pageSize) {
        PageHelper.startPage(page, pageSize, "SignDate desc");

        List<ContractListVO> contractListVOList = contractMapper.getContractListByEppOrBuild(compid, searchName);
        PageInfo<ContractListVO> pageInfo = new PageInfo<>(contractListVOList);
        PageVO<ContractListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<ContractListVO> getBuildContractListByEppOrBuild(Integer buildId, String searchName, Integer page,
                                                                   Integer pageSize) {

        //首先根据buildId查询出此施工方用户下的所有合同uid和所有子合同号
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);

        PageHelper.startPage(page, pageSize, "SignDate desc");
        List<ContractListVO> contractListVOList = contractMapper.getBuildContractListByEppOrBuild(contractDetailCodes,
                contractUIDList, searchName);
        PageInfo<ContractListVO> pageInfo = new PageInfo<>(contractListVOList);
        PageVO<ContractListVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 判断合同审核状态
     */
    private Boolean getContractVerifyStatus(String contractUID, String cContractCode, String compid) {
        return contractMapper.getContractVerifyStatus(contractUID, cContractCode, compid);
    }

}




