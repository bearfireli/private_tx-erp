package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.BuilderService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.sell.Builder;
import com.hntxrj.txerp.entity.sell.QBuilder;
import com.hntxrj.txerp.vo.BuilderVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.repository.BuilderRepository;
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

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BuilderServiceImpl implements BuilderService {


    private final UserService userService;
    private final BuilderRepository builderRepository;
    private JPAQueryFactory queryFactory;

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BuilderServiceImpl(UserService userService, BuilderRepository builderRepository,
                              EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.builderRepository = builderRepository;
        queryFactory = new JPAQueryFactory(entityManager);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Builder saveBuilder(Builder builder, String token, Integer enterprise) throws ErpException {
        User user = userService.tokenGetUser(token);
        if (StringUtils.isEmpty(builder.getBuilderCode())) {
            throw new ErpException(ErrEumn.BUILDER_CODE_CANNOT_NULL);
        }
        if (StringUtils.isEmpty(builder.getBuilderName()) ||
                StringUtils.isEmpty(builder.getBuilderShortName())) {
            throw new ErpException(ErrEumn.BUILDER_NAME_CANNOT_NULL);
        }

        builder.setEnterprise(enterprise);

        builder.setUpdateUser(user.getUid());

        if (builder.getId() == null || builder.getId() == 0) {
            builder.setCreateUser(user.getUid());
        }

        log.info("【创建施工单位】builder={}, user={}", builder, user);


        return builderRepository.save(builder);
    }

    @Override
    public Builder delBuilder(Integer id) throws ErpException {

        Builder builder = getOne(id);

        builder.setDel(1);

        return builderRepository.save(builder);
    }

    @Override
    public Builder getOne(Integer id) throws ErpException {
        return builderRepository.findById(id)
                .orElseThrow(() -> new ErpException(ErrEumn.BUILDER_NOT_FIND));
    }

    @Override
    public PageVO<BuilderVO> findBuilder(String name, Integer eid, Integer page, Integer pageSize) throws ErpException {
        if (eid <= 0) {
            throw new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST);
        }

        String sql = "select b.*, u.username update_user_name,e.ep_short_name enterprise_name\n" +
                "from builder_master as b \n" +
                "left join `user` as u on u.uid=b.update_user \n" +
                "left join enterprise e on e.eid=b.enterprise \n" +
                "where b.enterprise=" + eid + " and b.del=0 \n" +
                "and (b.builder_name like '%" + name + "%' or b.builder_short_name like '%" + name + "%') " +
                "order by builder_name, builder_code asc limit " + (page - 1) * pageSize + "," + pageSize;

        List<BuilderVO> builderVOS = jdbcTemplate.query(sql, new Object[]{},
                new BeanPropertyRowMapper<>(BuilderVO.class));


        QBuilder qBuilder = QBuilder.builder;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBuilder.enterprise.eq(eid));
        builder.and(qBuilder.del.eq(0));
        if (!StringUtils.isEmpty(name)) {
            builder.and((qBuilder.builderName.like("%" + name + "%")
                    .or(qBuilder.builderShortName.like("%" + name + "%"))));
        }


        JPAQuery<BuilderVO> query = queryFactory.select(
                Projections.bean(
                        BuilderVO.class,
                        qBuilder.id,
                        qBuilder.builderName,
                        qBuilder.builderCode,
                        qBuilder.builderShortName,
                        qBuilder.del,
                        qBuilder.enterprise,
                        qBuilder.builderAddress,
                        qBuilder.builderLinkTel,
                        qBuilder.createTime,
                        qBuilder.createUser,
                        qBuilder.updateTime,
                        qBuilder.updateUser
                )
        ).from(qBuilder)
                .where(builder)
                .offset(((page - 1) * pageSize))
                .limit(pageSize);


        PageVO<BuilderVO> pageVO = new PageVO<>();
        pageVO.init(query, pageSize);
        pageVO.setArr(builderVOS);

        return pageVO;
    }

    @Override
    public List<JSONObject> builderDownDrop(String builderName, Integer eid, Integer page, Integer pageSize) throws ErpException {
        PageVO<BuilderVO> builders = findBuilder(builderName, eid, page, pageSize);
        List<BuilderVO> builderList = builders.getArr();
        List<JSONObject> result = new ArrayList<>();
        builderList.forEach(builder -> {
                    JSONObject o = new JSONObject();
                    o.put("builderId", builder.getId());
                    o.put("builderName", builder.getBuilderName());
                    o.put("builderShortName", builder.getBuilderShortName());
                    result.add(o);
                }
        );
        return result;
    }
}
