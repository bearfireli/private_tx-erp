package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.ContractService;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/contract/")
@Slf4j
public class ContractApi {

    private final ContractService contractService;

    @Autowired
    public ContractApi(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * 合同列表查询
     *
     * @param startTime    签订日期 开始时间
     * @param endTime      签订日期 结束时间
     * @param contractCode 合同编号
     * @param eppCode      工程代号
     * @param buildCode    施工单位代号
     * @param salesMan     销售员代号
     * @param compid       企业代号
     * @param page         页码
     * @param pageSize     每页数量
     * @return 合同列表
     */
    @PostMapping("/getContractList")
    public ResultVO getContractList(String startTime, String endTime,
                                    String contractCode, String eppCode,
                                    String buildCode, String salesMan, String compid,
                                    @RequestParam(required = false) String verifyStatus ,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResultVO.create(contractService.getContractList(
                startTime == null ? null : Long.parseLong(startTime),
                endTime == null ? null : Long.parseLong(endTime), contractCode,
                eppCode, buildCode, salesMan, compid, verifyStatus,page, pageSize));
    }

    /**
     * 获取合同详情
     *
     * @param contractUid 合同uid
     * @param compid      企业id
     * @return 合同详情
     */
    @PostMapping("/getContractDetail")
    public ResultVO getContractDetail(String contractDetailCode, String contractUid, String compid) {
        return ResultVO.create(contractService.getContractDetail(contractDetailCode, contractUid, compid));
    }


    /**
     * 审核/取消审核 合同
     *
     * @param contractUid  合同代号
     * @param compid       企业id
     * @param opId         操作员代号
     * @param verifyStatus 审核状态
     * @return 结果
     */
    @PostMapping("/verifyContract")
    public ResultVO verifyContract(String contractUid, String compid, String opId, Integer verifyStatus) {
        contractService.verifyContract(contractUid, compid, opId, verifyStatus);
        return ResultVO.create();
    }

    /**
     * 获取合同砼价格列表
     * <p>
     * //     * @param contractUid        合同uid
     * //     * @param contractDetailCode 子合同代号
     *
     * @param compid 企业id
     * @return 砼价格列表
     */
    @PostMapping("/getContractGradePrice")
    public ResultVO getContractGradePrice(String contractUid, String contractDetailCode, String compid) {
        if(StringUtils.isEmpty(contractUid) && StringUtils.isEmpty(contractDetailCode)){
            return ResultVO.create(contractService.getContractStgIdDropDown(compid));
        }
        return ResultVO.create(contractService.getContractGradePrice(contractUid, contractDetailCode, compid));
    }

    /**
     * 获取合同特殊材料列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 子合同代号
     * @param compid             企业id
     * @return 合同特殊材料列表
     */
    @PostMapping("/getContractPriceMarkup")
    public ResultVO getContractPriceMarkup(String contractUid, String contractDetailCode, String compid) {
        return ResultVO.create(contractService.getContractPriceMarkup(contractUid, contractDetailCode, compid));
    }

    @PostMapping("/getContractPumpPrice")
    public ResultVO getContractPumpPrice(String contractUid, String contractDetailCode, String compid) {
        return ResultVO.create(contractService.getContractPumpPrice(contractUid, contractDetailCode, compid));
    }

    @PostMapping("/getContractDistanceSet")
    public ResultVO getContractDistanceSet(String contractUid, String contractDetailCode, String compid) {
        return ResultVO.create(contractService.getContractDistanceSet(contractUid, contractDetailCode, compid));
    }

    /**
     * 获取合同类别
     *
     * @return 合同类别下拉
     */
    @PostMapping("/getContractTypeDropDown")
    public ResultVO getContractTypeDropDown(String compid) {
        return ResultVO.create(contractService.getContractTypeDropDown(compid));
    }

    /**
     * 合同价格执行方式
     *
     * @return 合同价格执行方式下拉
     */
    @PostMapping("/getPriceTypeDropDown")
    public ResultVO getPriceTypeDropDown(String compid) {
        return ResultVO.create(contractService.getPriceTypeDropDown(compid));
    }


    /**
     * 上传合同附件
     *
     * @param contractUid   合同uid
     * @param ccontractCode 子合同代号
     * @param num           第几个附件
     * @param file          文件
     * @param compid        企业id
     * @return 上传后该合同的附件列表
     * @throws ErpException 异常
     */
    @PostMapping("/uploadAdjunct")
    public ResultVO uploadAdjunct(String contractUid, String ccontractCode,
                                  Integer num, MultipartFile file, String compid) throws ErpException {

        return ResultVO.create(contractService.uploadAdjunct(contractUid, ccontractCode, num, file, compid));
    }

    /**
     * 获取某个合同的附件列表
     *
     * @param contractUid   合同uid
     * @param ccontractCode 子合同代号
     * @param compid        企业id
     * @return 上传后该合同的附件列表
     */
    @PostMapping("/getAdjunct")
    public ResultVO getAdjunct(String contractUid, String ccontractCode, String compid) {
        return ResultVO.create(contractService.getAdjunct(contractUid, ccontractCode, compid));
    }


    /**
     * 删除某附件
     *
     * @param fileUid 上传后在服务器的文件名称（一个uid）
     * @return 删除后该合同的附件列表
     */
    @GetMapping("/delAdjunct/{fileUid}")
    public ResultVO delAdjunct(@PathVariable String fileUid) {
        return ResultVO.create(contractService.delAdjunct(fileUid));
    }

    /**
     * 下载某个附件
     *
     * @param fileName 上传后在服务器的文件名称（一个uid）
     * @param response resp对象
     * @throws ErpException 找不到文件等异常
     */
    @GetMapping("/adjunct/{fileName}")
    public void getAdjunctFile(@PathVariable String fileName, HttpServletResponse response) throws ErpException {
        contractService.getAdjunctItem(fileName, response);
    }


    @PostMapping("/addContract")
    public ResultVO addContract(String contractId,
                                String salesman,
                                long signDate,
                                long expiresDate,
                                Integer contractType,
                                Integer priceStyle,
                                String eppCode,
                                String builderCode,
                                BigDecimal contractNum,
                                BigDecimal preNum,
                                BigDecimal preMoney,
                                String remarks, String compid) throws ErpException {
        contractService.addContract(contractId,
                salesman,
                new Date(signDate),
                new Date(expiresDate),
                contractType,
                priceStyle,
                eppCode,
                builderCode,
                contractNum,
                preNum,
                preMoney,
                remarks, compid);
        return ResultVO.create();
    }

    @PostMapping("/disableContract")
    public ResultVO disableContract(String contractUid, String contractDetailCode, String compid) {
        contractService.disableContract(contractUid, contractDetailCode, compid);
        return ResultVO.create();
    }

    /**
     * 获取标号下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    @PostMapping("/getStgIdDropDown")
    public ResultVO getStgIdDropDown(String compid) {
        return ResultVO.create(contractService.getContractStgIdDropDown(compid));
    }


    /**
     * 获取特殊材料下拉
     *
     * @param compid 企业id
     * @return 标号下拉
     */
    @PostMapping("/getPriceMarkupDropDown")
    public ResultVO getPriceMarkupDropDown(String compid) {
        return ResultVO.create(contractService.getPriceMarkupDropDown(compid));

    }

    @PostMapping("/saveContractGradePrice")
    public ResultVO saveContractGradePrice(String compid, String contractUid,
                                           String contractDetailCode, String gradePrice) throws ErpException {
        contractService.saveContractGradePrice(compid, contractUid, contractDetailCode, gradePrice);
        return ResultVO.create();
    }

    @PostMapping("/saveContractPriceMarkup")
    public ResultVO saveContractPriceMarkup(String compid, String contractUid,
                                            String contractDetailCode, String priceMarkup) throws ErpException {
        contractService.saveContractPriceMarkup(compid, contractUid, contractDetailCode, priceMarkup);
        return ResultVO.create();
    }


    /**
     * 生成合同id
     *
     * @param compid 企业代号
     * @return 合同id
     */
    @PostMapping("/makeAutoContractId")
    public ResultVO makeAutoContractId(String compid) throws ErpException {
        return ResultVO.create(contractService.makeAutoContractId(compid));
    }

    /**
     * 泵车类价格
     *
     * @param compid        企业代号
     * @param cContractCode 合同号
     * @param contractUID   合同UID号
     */
    @PostMapping("/getContractPumperPrice")
    public ResultVO getContractPumperPrice(String compid, String cContractCode, String contractUID) throws ErpException {
        return ResultVO.create(contractService.getContractPumperPrice(compid, cContractCode, contractUID));
    }

    /**
     * 泵车类价格插入数据
     *
     * @param compid        企业代号
     * @param opid         操作员代号
     * @param contractUID  合同uid号
     * @param contractCode 子合同号
     * @param pumptype     泵车类别
     * @param pumPrice     泵送单价
     * @param tableExpense 台班费
     * @return Interger
     */
    @PostMapping("/insertPumpTruck")
    public ResultVO insertPumpTruck(String compid, String opid, String contractUID, String contractCode,
                                    Integer pumptype, Double pumPrice, Double tableExpense) throws ErpException {
        return ResultVO.create(contractService.insertPumpTruck(compid, opid, contractUID,
                contractCode, pumptype, pumPrice, tableExpense));
    }


    /**
     * 合同运距
     *
     * @param compid        企业ID
     * @param contractUID   合同UID
     * @param cContractCode 子合同号
     * @return 站名 运输距离
     */
    @PostMapping("/getContractDistance")
    public ResultVO getContractDistance(String compid, String contractUID, String cContractCode) throws ErpException {
        return ResultVO.create(contractService.getContractDistance(compid, contractUID, cContractCode));


    }

    /**
     * 添加合同运距
     *
     * @param contractUID   合同UID号
     * @param cContractCode 子合同号
     * @param compid        站别代号
     * @param distance      运输距离
     * @param remarks       备注
     * @param recStatus     标志
     * @param upDown        网络标识
     * @param upDownMark    网络下发标识
     * @return int
     */
    @PostMapping("/saveContractDistance")
    public ResultVO saveContractDistance(String contractUID, String cContractCode, String compid, Double distance,
                                         String remarks,
                                         @RequestParam(defaultValue = "1") Integer recStatus,
                                         @RequestParam(defaultValue = "0") Double upDown,
                                         @RequestParam(defaultValue = "0") Integer upDownMark) throws ErpException {

        return ResultVO.create(contractService.saveContractDistance(contractUID, cContractCode, compid, distance,
                remarks, recStatus, upDown, upDownMark));
    }

    /**
     * 泵车列表查询
     *
     * @param compid  企业代号
     * @return 列表查询
     */
    @PostMapping("/selectPumpTruckList")
    public ResultVO selectPumpTruckList(String compid, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize, String builderName) {
        return ResultVO.create(contractService.selectPumpTruckList(compid, page, pageSize, builderName));
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
    @PostMapping("/getContractListByEppOrBuild")
    public ResultVO getContractListByEppOrBuild(String compid, String searchName,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(contractService.getContractListByEppOrBuild(compid,searchName,page,pageSize));
    }


}
