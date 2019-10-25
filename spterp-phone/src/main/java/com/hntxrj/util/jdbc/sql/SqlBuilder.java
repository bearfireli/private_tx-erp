package com.hntxrj.util.jdbc.sql;


import java.util.List;

/**
 * sql语句组合
 * @author lhr
 * @create 2018/1/11
 */
public interface SqlBuilder {

    SqlBuilder table(Table table);

    SqlBuilder table(String tableName);

    SqlBuilder table(String tableName, Class cs);

    SqlBuilder field(List<Field> fields);

    SqlBuilder field(Field field);

    SqlBuildImpl select();

    SqlBuilder update();

    SqlBuilder insert();

    SqlBuilder delect();

    SqlBuilder where(Where where);

    SqlBuilder where(String field, String value);

    SqlBuilder where(List<Where> wheres);

    SqlBuilder order(Order order);

    SqlBuilder order(List<Order> orders);

    SqlBuilder limit(Limit limit);

    SqlBuilder build();

    SqlBuilder param(List<Param> params);

    SqlBuilder param(Param param);

    Page page();

    List getResult();

}
