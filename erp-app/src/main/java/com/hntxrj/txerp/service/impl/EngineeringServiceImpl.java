package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.EngineeringService;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.sell.QEngineering;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.EngineeringVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.repository.EngineeringRepository;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.sell.Engineering;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class EngineeringServiceImpl implements EngineeringService {

    private final com.hntxrj.txerp.repository.EngineeringRepository EngineeringRepository;

    private UserService userService;
    private JPAQueryFactory queryFactory;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EngineeringServiceImpl(EngineeringRepository EngineeringRepository,
                                  EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.EngineeringRepository = EngineeringRepository;
        queryFactory = new JPAQueryFactory(entityManager);
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Engineering> addList(List<Engineering> pojos) {
        return EngineeringRepository.saveAll(pojos);
    }


    public PageVO<EngineeringVO> select(String engineeringCode, String engineeringName, String userToken,
                                        String linkMan, Integer enterprise,
                                        Integer page, Integer pageSize) throws ErpException {
        log.info("【工程名称】engineeringCode={}, " +
                        "engineeringName={},userToken={}," +
                        "linkMan={},enterprise={}," +
                        "page={},pageSize={}",
                engineeringCode, engineeringName, userToken, linkMan, enterprise, page, pageSize);

        String sql = "select eg.*, e.ep_short_name enterprise_name, u1.username create_user_name, u2.username update_user_name\n" +
                "from engineering_master eg\n" +
                "left join enterprise e on eg.enterprise_id=e.eid\n" +
                "left join user u1 on u1.uid=eg.create_user\n" +
                "left join user u2 on u2.uid=eg.update_user\n" +
                "where eg.engineering_code like '%" + engineeringCode + "%'\n" +
                "and (eg.engineering_full_name like '%" + engineeringName + "%' " +
                "and eg.engineering_short_name like '%" + engineeringName + "%')\n" +
                "and eg.engineering_link_man like '%" + linkMan + "%'\n" +
                "and eg.enterprise_id=" + enterprise +
                " limit " + (page - 1) * pageSize + "," + pageSize;

        List<EngineeringVO> engineeringVOS =
                jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(EngineeringVO.class));


        QEngineering qEngineering = new QEngineering("eg");

        // 获取用户，查询该用户企业下的工程
        if (StringUtils.isEmpty(userToken)) {
            throw new ErpException(ErrEumn.TOKEN_IS_NULL);
        }
        User user = userService.tokenGetUser(userToken);

        if (user == null) {
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }

        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(engineeringCode)) {
            builder.and(qEngineering.engineeringCode.like("%" + engineeringCode + "%"));
        }
        if (!StringUtils.isEmpty(engineeringName)) {
            builder.and(qEngineering.engineeringFullName.like("%" + engineeringName + "%")
                    .or(qEngineering.engineeringShortName.like("%" + engineeringName + "%")));

        }
        if (!StringUtils.isEmpty(linkMan)) {
            builder.and(qEngineering.engineeringLinkMan.like("%" + linkMan + "%"));
        }

        builder.and(qEngineering.enterpriseId.eq(enterprise));

        JPAQuery<EngineeringVO> query;
        query = queryFactory.select(
                Projections.bean(
                        EngineeringVO.class,
                        qEngineering.id,
                        qEngineering.engineeringCode,
                        qEngineering.engineeringFullName,
                        qEngineering.engineeringShortName,
                        qEngineering.engineeringAddress,
                        qEngineering.engineeringLinkMan,
                        qEngineering.engineeringLinkTel,
                        qEngineering.enterpriseId,
                        qEngineering.createTime,
                        qEngineering.createUser,
                        qEngineering.remark,
                        qEngineering.updateTime,
                        qEngineering.updateUser,
                        qEngineering.del
                )
        ).from(qEngineering)
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);


        PageVO<EngineeringVO> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        pageVO.setArr(engineeringVOS);
        return pageVO;
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
