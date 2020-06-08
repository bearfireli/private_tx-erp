package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.ContractService;
import com.hntxrj.txerp.server.SalesmanService;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.hntxrj.txerp.util.WebUtilKt.getCompid;

@RestController
@RequestMapping("/api/contract/")
@Slf4j
public class ContractApi {

    private final ContractService contractService;
    private final SalesmanService salesmanService;

    @Autowired
    public ContractApi(ContractService contractService, SalesmanService salesmanService) {
        this.contractService = contractService;
        this.salesmanService = salesmanService;
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
     * @param page         页码
     * @param pageSize     每页数量
     * @return 合同列表
     */
    @PostMapping("/getContractList")
    public ResultVO getContractList(String startTime, String endTime,
                                    String contractCode, String eppCode,
                                    String buildCode, String salesMan,
                                    @RequestParam(required = false) String verifyStatus,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize, HttpServletRequest request) {

        return ResultVO.create(contractService.getContractList(
                startTime == null ? null : Long.parseLong(startTime),
                endTime == null ? null : Long.parseLong(endTime), contractCode,
                eppCode, buildCode, salesMan, getCompid(request), verifyStatus, page, pageSize));
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
    public ResultVO verifyContract(String contractUid, String compid, String opId, Integer verifyStatus) throws ErpException {
        contractService.verifyContract(contractUid, compid, opId, verifyStatus);
        return ResultVO.create();
    }

    /**
     * 获取合同砼价格列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 子合同代号
     * @param compid             企业id
     * @return 砼价格列表
     */
    @PostMapping("/getContractGradePrice")
    public ResultVO getContractGradePrice(String contractUid, String contractDetailCode, String compid) {
        if (StringUtils.isEmpty(contractUid) && StringUtils.isEmpty(contractDetailCode)) {
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


    /**
     * 获取合同泵送价格列表
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 合同子合同号
     * @param compid             企业id
     * @return 合同泵送价格列表
     */
    @PostMapping("/getContractPumpPrice")
    public ResultVO getContractPumpPrice(String contractUid, String contractDetailCode, String compid) {
        return ResultVO.create(contractService.getContractPumpPrice(contractUid, contractDetailCode, compid));
    }

    /**
     * 获取合同运距
     *
     * @param contractUid        合同uid
     * @param contractDetailCode 合同子合同号
     * @param compid             企业id
     */
    @PostMapping("/getContractDistanceSet")
    public ResultVO getContractDistanceSet(String contractUid, String contractDetailCode, String compid) {
        return ResultVO.create(contractService.getContractDistanceSet(contractUid, contractDetailCode, compid));
    }

    /**
     * 获取合同类别
     *
     * @param compid 企业id
     * @return 合同类别下拉
     */
    @PostMapping("/getContractTypeDropDown")
    public ResultVO getContractTypeDropDown(String compid) {
        return ResultVO.create(contractService.getContractTypeDropDown(compid));
    }

    /**
     * 合同价格执行方式
     *
     * @param compid 企业id
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
     * @param linkMan      合同联系人
     * @param linkTel      合同联系电话
     * @param opid         操作员id
     */
    @PostMapping("/addContract")
    public ResultVO addContract(String contractId,
                                String salesman,
                                Long signDate,
                                Long expiresDate,
                                Long effectDate,
                                Integer contractType,
                                Integer priceStyle,
                                String eppCode,
                                String builderCode,
                                BigDecimal contractNum,
                                BigDecimal preNum,
                                BigDecimal preMoney,
                                String remarks,
                                String compid,
                                String address,
                                String linkMan,
                                String linkTel,
                                String opid) throws ErpException {
        contractService.addContract(contractId,
                salesman, new Timestamp(signDate), expiresDate == null ? null : new Timestamp(expiresDate),
                effectDate == null ? null : new Timestamp(effectDate), contractType, priceStyle, eppCode, builderCode,
                contractNum, preNum, preMoney, remarks, compid, address, linkMan, linkTel, opid);
        return ResultVO.create();
    }


    /**
     * 禁用合同（此接口还未启用）
     *
     * @param contractUid        主合同
     * @param contractDetailCode 子合同
     * @param compid             企业
     */
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

    /**
     * 保存合同标号价格
     *
     * @param compid             企业id
     * @param contractUid        主合同号
     * @param contractDetailCode 子合同号
     * @param gradePrice         砼价格对象
     */
    @PostMapping("/saveContractGradePrice")
    public ResultVO saveContractGradePrice(String compid, String contractUid,
                                           String contractDetailCode, String gradePrice) throws ErpException {
        contractService.saveContractGradePrice(compid, contractUid, contractDetailCode, gradePrice);
        return ResultVO.create();
    }


    /**
     * 保存合同加价项目
     *
     * @param compid             企业id
     * @param contractUid        主合同号
     * @param contractDetailCode 子合同号
     * @param priceMarkup        加价项目
     */
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
     * @param compid       企业代号
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
     * @param recStatus     标志   0:禁用； 1：启用
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
     * @param compid      企业代号
     * @param builderName 工程名称
     * @param page        页码
     * @param pageSize    每页数量
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
        return ResultVO.create(contractService.getContractListByEppOrBuild(compid, searchName, page, pageSize));
    }

    /**
     * 工地端添加任务单时根据施工方查询关联的合同列表
     *
     * @param buildId    施工方id
     * @param searchName 搜索添加，可能是施工名称或者是施工单位
     * @param page       页码
     * @param pageSize   每页数量
     * @return 合同列表
     */
    @PostMapping("/getBuildContractListByEppOrBuild")
    public ResultVO getBuildContractListByEppOrBuild(Integer buildId, String searchName,
                                                     @RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(contractService.getBuildContractListByEppOrBuild(buildId, searchName, page, pageSize));
    }


    /**
     * 获取销售员下拉
     *
     * @param salesName 销售员名称
     * @param compid    企业id
     * @return 销售员下拉对象
     */
    @PostMapping("/getSalesMan")
    public ResultVO getSalesMan(String salesName, String compid) {
        return ResultVO.create(salesmanService.getSalesmanDropDown(salesName, compid));
    }

    /**
     * 获取业务员的分组
     *
     * @param compid   企业id
     * @param page     分页
     * @param pageSize 每页数量
     */
    @PostMapping("/getSaleGroup")
    public ResultVO getSaleGroup(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(salesmanService.getSaleGroup(compid, page, pageSize));
    }

    /**
     * 添加销售员
     *
     * @param compid     企业id
     * @param saleName   销售员姓名
     * @param department 销售员部门分组
     */
    @PostMapping("/addSaleMan")
    public ResultVO addSaleMan(String compid, String saleName, String department) {
        salesmanService.addSaleMan(compid, saleName, department);
        return ResultVO.create();
    }


}
