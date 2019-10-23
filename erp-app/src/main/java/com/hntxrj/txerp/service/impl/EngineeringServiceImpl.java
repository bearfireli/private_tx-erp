package com.hntxrj.txerp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.EngineeringMapper;
import com.hntxrj.txerp.service.EngineeringService;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.sell.QEngineering;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.util.PageInfoUtil;
import com.hntxrj.txerp.vo.EngineeringVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.repository.EngineeringRepository;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.sell.Engineering;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class EngineeringServiceImpl implements EngineeringService {

    private final com.hntxrj.txerp.repository.EngineeringRepository EngineeringRepository;

    private final UserService userService;
    private JPAQueryFactory queryFactory;

    private final JdbcTemplate jdbcTemplate;
    private final EngineeringMapper engineeringMapper;

    @Autowired
    public EngineeringServiceImpl(EngineeringRepository EngineeringRepository,
                                  UserService userService, EntityManager entityManager, JdbcTemplate jdbcTemplate,
                                  EngineeringMapper engineeringMapper) {
        this.EngineeringRepository = EngineeringRepository;
        this.userService = userService;
        queryFactory = new JPAQueryFactory(entityManager);
        this.jdbcTemplate = jdbcTemplate;
        this.engineeringMapper = engineeringMapper;
    }


    @Override
    public List<Engineering> addList(List<Engineering> pojos) {
        return EngineeringRepository.saveAll(pojos);
    }


    public PageVO<EngineeringVO> select(String engineeringCode, String engineeringName, String userToken,
                                        String linkMan, Integer enterprise,
                                        Integer page, Integer pageSize) {
        log.info("【工程名称】engineeringCode={}, " +
                        "engineeringName={},userToken={}," +
                        "linkMan={},enterprise={}," +
                        "page={},pageSize={}",
                engineeringCode, engineeringName, userToken, linkMan, enterprise, page, pageSize);

        PageHelper.startPage(page, pageSize);
        List<EngineeringVO> engineeringVOS = engineeringMapper.engineeringList(engineeringCode, engineeringName, linkMan, enterprise);
        PageInfo<EngineeringVO> pageInfo = new PageInfo<>(engineeringVOS);

        PageInfoUtil<EngineeringVO> pageInfoUtil = new PageInfoUtil<>();

        return pageInfoUtil.init(pageInfo);
    }

    @Override
    public Engineering save(Engineering Engineering, String token) throws ErpException {

        if (token == null || "".equals(token)) {
            throw new ErpException(ErrEumn.TOKEN_IS_NULL);
        }

        User user = userService.tokenGetUser(token);
        log.info("【user】user={}", user);
        log.info("[工程名称保存] Engineering={}", Engineering);
        if (Engineering.getEngineeringCode() == null ||
                Engineering.getEngineeringCode().equals("")) {
            throw new ErpException(ErrEumn.ENGINEERING_CODE_CANNOT_NULL);
        }
        if (Engineering.getEngineeringFullName() == null ||
                "".equals(Engineering.getEngineeringFullName())) {
            throw new ErpException(ErrEumn.ENGINEERING_FULL_NAME_CANNOT_NULL);
        }
        if (Engineering.getEngineeringShortName() == null ||
                "".equals(Engineering.getEngineeringShortName())) {
            throw new ErpException(ErrEumn.ENGINEERING_SHORT_NAME_CANNOT_NULL);
        }

        if (Engineering.getEngineeringLinkMan() == null) {
            Engineering.setEngineeringLinkMan("");
        }
        if (Engineering.getEngineeringLinkTel() == null) {
            Engineering.setEngineeringLinkTel("");
        }
        if (Engineering.getEngineeringAddress() == null) {
            Engineering.setEngineeringAddress("");
        }

        if (Engineering.getDel() == null || Engineering.getDel() < 0) {
            // 默认未删除
            Engineering.setDel(0);
        }

        if (Engineering.getId() == null || Engineering.getId() == 0) {
            Engineering.setCreateUser(user.getUid());
            Engineering.setUpdateUser(user.getUid());
            log.info("【save Engineering】Engineering={}", Engineering);
            return EngineeringRepository.save(Engineering);
        }
        log.info("【update Engineering】Engineering={}", Engineering);
        Engineering.setUpdateUser(user.getUid());
        return EngineeringRepository.save(Engineering);

    }

    @Override
    public Engineering findById(Integer id) throws ErpException {
        if (id == null || id == 0) {
            throw new ErpException(ErrEumn.ENGINEERING_ID_CANNOT_NULL_OR_ZERO);
        }
        return EngineeringRepository.findById(id).orElseThrow(() ->
                new ErpException(ErrEumn.ENGINEERING_NOT_FIND));
    }

    @Override
    public PageVO<Engineering> engineeringDownDrop(Integer eid, String engineeringName, Integer page, Integer pageSize)
            throws ErpException {

        QEngineering qEngineering = QEngineering.engineering;
        if (eid == null || eid <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST);
        }
        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(engineeringName)) {
            builder.and(qEngineering.engineeringFullName.like("%" + engineeringName + "%")
                    .or(qEngineering.engineeringShortName.like("%" + engineeringName + "%")));
        }
        builder.and(qEngineering.enterpriseId.eq(eid));
        JPAQuery<Engineering> engineeringJPAQuery = queryFactory.selectFrom(qEngineering)
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        PageVO<Engineering> pageVO = new PageVO<>();
        pageVO.init(engineeringJPAQuery, pageSize);


        return pageVO;
    }

}
