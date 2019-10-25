package com.hntxrj.txerp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.ContractMapper;
import com.hntxrj.txerp.service.ContractService;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.sell.*;
import com.hntxrj.txerp.entity.sell.QContractDetails;
import com.hntxrj.txerp.entity.sell.QContractGradePrice;
import com.hntxrj.txerp.entity.sell.QContractPriceMarkup;
import com.hntxrj.txerp.entity.sell.QContractPumpPrice;
import com.hntxrj.txerp.entity.sell.QGradePrice;
import com.hntxrj.txerp.entity.sell.QPriceMarkup;
import com.hntxrj.txerp.entity.sell.QPumpPrice;
import com.hntxrj.txerp.entity.sell.QSalesman;
import com.hntxrj.txerp.core.util.EntityTools;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.mapper.SystemSqlMapper;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.repository.*;
import com.hntxrj.txerp.util.PageInfoUtil;
import com.hntxrj.txerp.vo.ContractListVO;
import com.hntxrj.txerp.vo.PageVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final UserService userService;
    private final ContractDetailsRepository contractDetailsRepository;
    private JPAQueryFactory queryFactory;
    private final PriceMarkupRepository priceMarkupRepository;
    private final PumpPriceRepository pumpPriceRepository;
    private final GradePriceRepository gradePriceRepository;
    private final SalesmanRepository salesmanRepository;
    private final ContractGradePriceRepository contractGradePriceRepository;
    private final ContractPriceMarkupRepository contractPriceMarkupRepository;
    private final ContractPumpPriceRepository contractPumpPriceRepository;
    private final JdbcTemplate jdbcTemplate;
    private final SystemSqlMapper systemSqlMapper;
    private final ContractMapper contractMapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, UserService userService, EntityManager entityManager,
                               ContractDetailsRepository contractDetailsRepository,
                               PriceMarkupRepository priceMarkupRepository,
                               PumpPriceRepository pumpPriceRepository,
                               GradePriceRepository gradePriceRepository,
                               SalesmanRepository salesmanRepository,
                               ContractGradePriceRepository contractGradePriceRepository,
                               ContractPriceMarkupRepository contractPriceMarkupRepository,
                               ContractPumpPriceRepository contractPumpPriceRepository,
                               JdbcTemplate jdbcTemplate, SystemSqlMapper systemSqlMapper,
                               ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.userService = userService;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.contractDetailsRepository = contractDetailsRepository;
        this.priceMarkupRepository = priceMarkupRepository;
        this.pumpPriceRepository = pumpPriceRepository;
        this.gradePriceRepository = gradePriceRepository;
        this.salesmanRepository = salesmanRepository;
        this.contractGradePriceRepository = contractGradePriceRepository;
        this.contractPriceMarkupRepository = contractPriceMarkupRepository;
        this.contractPumpPriceRepository = contractPumpPriceRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.systemSqlMapper = systemSqlMapper;
        this.contractMapper = contractMapper;
    }


    @Override
    public Contract saveMasterContract(Contract contract, String token) throws ErpException {

        if (StringUtils.isEmpty(token)) {
            throw new ErpException(ErrEumn.TOKEN_IS_NULL);
        }

        User user = userService.tokenGetUser(token);

        if (user == null) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }

        if (StringUtils.isEmpty(contract.getCmUid())) {
            contract.setCmUid(UUID.randomUUID().toString());
            contract.setCreateUid(user.getUid());
        }
        contract.setUpdateUid(user.getUid());

        if (StringUtils.isEmpty(contract.getCmId())) {
            contract.setCmId(makeContractId(contract.getEnterpriseId()));
        }
        if (contract.getSalesmanUid() == null || contract.getSalesmanUid() <= 0) {
            throw new ErpException(ErrEumn.CONTRACT_SALESMANUID_NULL);
        }
        if (contract.getCmType() == null || contract.getCmType() < 0) {
            throw new ErpException(ErrEumn.CONTRACT_CMTYPE_NULL);
        }
        if (contract.getPriceStyle() == null || contract.getPriceStyle() < 0) {
            throw new ErpException(ErrEumn.CONTRACT_PRICESTYLE_NULL);
        }

        EntityTools.setEntityDefaultValue(contract);
        log.info("【添加主合同】Contract={}", contract);
        try {
            return contractRepository.save(contract);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_CONTRACRT_ERROR);
        }
    }

    @Override
    public ContractDetails saveContractDetails(ContractDetails contractDetails, String token) throws ErpException {

        User user = userService.tokenGetUser(token);

        contractDetails.setUpdateUser(user.getUid());
        contractDetails.setCreateUser(user.getUid());

        if (StringUtils.isEmpty(contractDetails.getCmUid())) {
            throw new ErpException(ErrEumn.CONTRACT_CMUID_NULL);
        }
        if (contractDetails.getEngineeringId() == null || contractDetails.getEngineeringId() <= 0) {
            throw new ErpException(ErrEumn.ENGINEERING_ID_CANNOT_NULL_OR_ZERO);
        }
        if (contractDetails.getBuilderId() == null || contractDetails.getBuilderId() <= 0) {
            throw new ErpException(ErrEumn.BUILDER_CODE_CANNOT_NULL);
        }

        if (contractDetails.getEnterprise() == null || contractDetails.getEnterprise() <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }


        if (contractDetails.getContractStatus() == null || contractDetails.getContractStatus() <= 0) {
            contractDetails.setContractStatus(0);
        }


        // 获取主合同
        Contract contract = getMasterContract(contractDetails.getCmUid());
        contractDetails.setCdUid(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(contractDetails.getCdId())) {
            contractDetails.setCdId(makeContractDetailsId(contract.getCmUid(), contract.getCmId()));
        }

        EntityTools.setEntityDefaultValue(contractDetails);


        return contractDetailsRepository.save(contractDetails);
    }

    /**
     * 生成子代号生成器
     *
     * @param contractUid 主合同uid
     * @param contractId  主合同代号
     * @return 返回子合同代号
     */
    private String makeContractDetailsId(String contractUid, String contractId) {
        long count = queryFactory.selectFrom(QContractDetails.contractDetails)
                .where(QContractDetails.contractDetails.cmUid.eq(contractUid)).fetchCount();
        return contractId + (count > 9 ? count : "0" + count);
    }


    @Override
    public PageVO<ContractListVO> list(String builderName, String engineeringName, String contractId,
                                       Integer saleUid, Integer contractStatus, Integer del, Integer eid,
                                       Integer page, Integer pageSize, String token) throws ErpException {

        User user = userService.tokenGetUser(token);

        PageHelper.startPage(page, pageSize);

        List<ContractListVO> listVOS = contractMapper.contractList(builderName, engineeringName,
                contractId, saleUid, contractStatus, del, eid);


        PageInfo<ContractListVO> pageInfo = new PageInfo<>(listVOS);
        PageInfoUtil<ContractListVO> contractListVO = new PageInfoUtil<>();
        return contractListVO.init(pageInfo);
    }

    @Override
    public ContractDetails getContractDetails(String cdUid) throws ErpException {
        return contractDetailsRepository.findById(cdUid).orElseThrow(() ->
                new ErpException(ErrEumn.CONTRACRT_DETAILS_NOT_FOUND));
    }

    @Override
    public Contract getMasterContract(@Nonnull String cmUid) throws ErpException {
        return contractRepository.findById(cmUid).orElseThrow(() ->
                new ErpException(ErrEumn.CONTRACRT_NOT_FOUND));
    }

    @Override
    public List<ContractDetails> getAllContractDetails(String cmUid) {

        QContractDetails qContractDetails = QContractDetails.contractDetails;

        return queryFactory.selectFrom(qContractDetails)
                .where(qContractDetails.cmUid.eq(cmUid)).fetch();
    }

    @Override
    public PageVO<GradePrice> gradePriceList(String stgId, Integer enterprise, long page, long pageSize) {

        QGradePrice qGradePrice = QGradePrice.gradePrice;
        JPAQuery<GradePrice> query = queryFactory.selectFrom(qGradePrice)
                .where(qGradePrice.stgId.like("%" + stgId + "%"))
                .where(qGradePrice.enterpriseId.eq(enterprise))
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<GradePrice> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<PriceMarkup> priceMarkupList(String projectName, Integer enterprise, long page, long pageSize) {
        QPriceMarkup qPriceMarkup = QPriceMarkup.priceMarkup;
        JPAQuery<PriceMarkup> query = queryFactory.selectFrom(qPriceMarkup)
                .where(qPriceMarkup.projectName.like("%" + projectName + "%"))
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<PriceMarkup> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<PumpPrice> pumpPriceList(String typeName, Integer enterprise, long page, long pageSize) {
        QPumpPrice qPumpPrice = QPumpPrice.pumpPrice;
        BooleanBuilder builder = new BooleanBuilder();
        log.info("【pumpPriceList】typeName={}, enterprise={}", typeName, enterprise);
        if (!StringUtils.isEmpty(typeName)) {
            builder.and(qPumpPrice.typeName.like("%" + typeName + "%"));
        }
        JPAQuery<PumpPrice> query = queryFactory.selectFrom(qPumpPrice)
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<PumpPrice> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<Salesman> salesmanList(String salesmanName, String salesmanCode,
                                         Integer enterprise, long page, long pageSize) {

        BooleanBuilder builder = new BooleanBuilder();
        QSalesman qSalesman = QSalesman.salesman;
        if (!StringUtils.isEmpty(salesmanName)) {
            builder.and(qSalesman.salesmanName.like("%" + salesmanName + "%"));
        }
        if (!StringUtils.isEmpty(salesmanCode)) {
            builder.and(qSalesman.salesmanCode.like("%" + salesmanCode + "%"));
        }

        builder.and(qSalesman.enterprise.eq(enterprise));
        JPAQuery<Salesman> query = queryFactory.selectFrom(qSalesman)
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<Salesman> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<ContractPumpPrice> contractPumpPriceList(String cdUid, long page, long pageSize) {
        QContractPumpPrice qContractPumpPrice = QContractPumpPrice.contractPumpPrice;
        JPAQuery<ContractPumpPrice> query = queryFactory.selectFrom(qContractPumpPrice)
                .where(qContractPumpPrice.cdUid.eq(cdUid))
                .offset((page - 1) * pageSize)
                .limit(pageSize);
        PageVO<ContractPumpPrice> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<ContractGradePrice> contractGradePriceList(String cdUid, long page, long pageSize) {
        QContractGradePrice qContractGradePrice = QContractGradePrice.contractGradePrice;
        JPAQuery<ContractGradePrice> query = queryFactory.selectFrom(qContractGradePrice)
                .where(qContractGradePrice.cdUid.eq(cdUid))
                .offset((page - 1) * pageSize)
                .limit(pageSize);


        PageVO<ContractGradePrice> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public PageVO<ContractPriceMarkup> contractPriceMarkupList(String cdUid, long page, long pageSize) {
        QContractPriceMarkup qContractPriceMarkup = QContractPriceMarkup.contractPriceMarkup;
        JPAQuery<ContractPriceMarkup> query = queryFactory.selectFrom(qContractPriceMarkup)
                .where(qContractPriceMarkup.cdUid.eq(cdUid))
                .offset((page - 1) * pageSize)
                .limit(pageSize);
        ;


        PageVO<ContractPriceMarkup> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        return pageVO;
    }

    @Override
    public GradePrice saveGradePrice(GradePrice gradePrice, String token) throws ErpException {

        if (StringUtils.isEmpty(gradePrice.getStgId())) {
            throw new ErpException(ErrEumn.STG_ID_CANNOT_NULL);
        }
        if (gradePrice.getEnterpriseId() == null || gradePrice.getEnterpriseId() <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }

        User user = userService.tokenGetUser(token);
        gradePrice.setCreateUser(user.getUid());
        gradePrice.setUpdateUser(user.getUid());
        EntityTools.setEntityDefaultValue(gradePrice);
        return gradePriceRepository.save(gradePrice);
    }

    @Override
    public PriceMarkup savePriceMarkup(PriceMarkup priceMarkup, String token) throws ErpException {
        if (priceMarkup.getEnterprise() == null || priceMarkup.getEnterprise() <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }
        if (StringUtils.isEmpty(priceMarkup.getProjectName())) {
            throw new ErpException(ErrEumn.PROJECT_NAME_NULL);
        }
        if (StringUtils.isEmpty(priceMarkup.getProjectCode())) {
            throw new ErpException(ErrEumn.PROJECT_CODE_NULL);
        }

        return priceMarkupRepository.save(priceMarkup);
    }

    @Override
    public PumpPrice savePumpPrice(PumpPrice pumpPrice, String token) throws ErpException {
        User user = userService.tokenGetUser(token);

        pumpPrice.setCreateUser(user.getUid());

        if (pumpPrice.getEnterprise() == null || pumpPrice.getEnterprise() <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_NULL);
        }
        if (StringUtils.isEmpty(pumpPrice.getTypeName())) {
            throw new ErpException(ErrEumn.PUMP_TYPE_NAME_NULL);
        }
        if (StringUtils.isEmpty(pumpPrice.getTypeCode())) {
            throw new ErpException(ErrEumn.PUMP_TYPE_CODE_NULL);
        }


        return pumpPriceRepository.save(pumpPrice);
    }

    @Override
    public Salesman saveSalesman(Salesman salesman, String token) {
        return salesmanRepository.save(salesman);
    }

    @Override
    public ContractGradePrice saveContractGradePrice(ContractGradePrice contractGradePrice, String token) throws ErpException {
        User user = userService.tokenGetUser(token);
        return contractGradePriceRepository.save(contractGradePrice);
    }

    @Override
    public ContractPriceMarkup saveContractPriceMarkup(ContractPriceMarkup contractPriceMarkup, String token) {
        return contractPriceMarkupRepository.save(contractPriceMarkup);
    }

    @Override
    public ContractPumpPrice saveContractPumpPrice(ContractPumpPrice contractPumpPrice, String token) {
        EntityTools.setEntityDefaultValue(contractPumpPrice);
        return contractPumpPriceRepository.save(contractPumpPrice);
    }

    @Override
    public void saveContractGradePrices(List<ContractGradePrice> contractGradePrices, String token) throws ErpException {
        User user = userService.tokenGetUser(token);
        contractGradePrices.forEach(contractGradePrice -> {
            contractGradePrice.setUpdateUser(user.getUid());
        });
        contractGradePriceRepository.saveAll(contractGradePrices);
    }

    @Override
    public void saveContractPriceMarkups(
            List<ContractPriceMarkup> contractPriceMarkups, String token) {
        contractPriceMarkupRepository.saveAll(contractPriceMarkups);
    }

    @Override
    public void saveContractPumpPrices(
            List<ContractPumpPrice> contractPumpPrices, String token) {

        contractPumpPrices.forEach(EntityTools::setEntityDefaultValue);

        contractPumpPriceRepository.saveAll(contractPumpPrices);
    }

    private String makeContractId(Integer enterpriseId) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        StringBuilder dateString = new StringBuilder(sdf.format(new Date()));
        for (int i = 0; i < 3 - enterpriseId.toString().length(); i++) {
            dateString.append("0");
        }
        return dateString.toString() + enterpriseId;
    }

}
