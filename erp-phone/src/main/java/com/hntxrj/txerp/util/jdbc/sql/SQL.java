package com.hntxrj.txerp.util.jdbc.sql;

import java.util.*;

/**
 * Sql语句对象
 * @author lhr
 * @create 2018/1/11
 */
public class SQL {

    /**
     * 操作类型
     */
    public static final int TYPE_INSERT = 0;

    public static final int TYPE_DELECT = 1;

    public static final int TYPE_UPDATE = 2;

    public static final int TYPE_SELECT = 3;

    /**
     * sql类型，select,update,insert
     */
    private int sqlType;

    /**
     * 查询的字段
     */
    private List<Field> fields = new ArrayList<>();


    /**
     * 表名
     */
    private Table table;


    /**
     * where条件
     */
    private List<Where> wheres = new ArrayList<>();

    /**
     * 分页
     */
    private Limit limit;

    /**
     * 排序
     */
    private List<Order> orders = new ArrayList<>();


    /**
     * 是否编译,只有编译了的SQL对象才能提交.
     */
    private Boolean build = false;

    /**
     * sql语句
     */
    private String sql;


    /**
     * 参数
     */
    private List<Param> params;

    private String limitSql;


    /**
     * 结果集
     */
    public List result;


    @Override
    public String toString() {
        return "SQL{" +
                "sqlType=" + sqlType +
                ", fields=" + fields +
                ", table=" + table +
                ", wheres=" + wheres +
                ", limit=" + limit +
                ", orders=" + orders +
                ", build=" + build +
                ", sql='" + sql + '\'' +
                ", params=" + params +
                ", limitSql='" + limitSql + '\'' +
                ", result=" + result +
                '}';
    }

    public String getLimitSql() {
        return limitSql;
    }

    public void setLimitSql(String limitSql) {
        this.limitSql = limitSql;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public List<Where> getWheres() {
        return wheres;
    }

    public void setWheres(List<Where> wheres) {
        this.wheres = wheres;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Boolean getBuild() {
        return build;
    }

    public void setBuild(Boolean build) {
        this.build = build;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public void setParams(List<Param> params) {
        this.params = params;
    }


    public List<Param> getParams() {
        return params;
    }


}
