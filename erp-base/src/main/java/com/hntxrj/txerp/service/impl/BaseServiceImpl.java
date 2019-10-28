package com.hntxrj.txerp.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class BaseServiceImpl {
    //实体管理者

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    public BaseServiceImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }


}
