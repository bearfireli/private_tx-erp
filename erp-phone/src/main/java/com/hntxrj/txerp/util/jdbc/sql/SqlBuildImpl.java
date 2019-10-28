package com.hntxrj.txerp.util.jdbc.sql;

import com.hntxrj.txerp.util.jdbc.JdbcUtilException;
import com.hntxrj.txerp.util.jdbc.enums.DBTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * sql语句组装
 * @author lhr
 * @create 2018/1/11
 */
@Component
@Slf4j
public class SqlBuildImpl implements SqlBuilder {

    private SQL sql = new SQL();

    @Autowired
    private JdbcUtil jdbcUtil;



    @Override
    public SqlBuilder table(Table table) {
        sql = new SQL();
        sql.setTable(table);
        return this;
    }


    @Override
    public SqlBuilder table(String tableName) {
        Table table = new Table();
        table.setTableName(tableName);
        return this.table(table);
    }

    @Override
    public SqlBuilder table(String tableName, Class cs) {
        Table table = new Table();
        table.setTableName(tableName);
        table.setaClass(cs);
        return this.table(table);
    }

    @Override
    public SqlBuilder field(List<Field> fields) {
        List<Field> baseFields = sql.getFields();
        if (baseFields == null){
            baseFields = fields;
        }else {
            baseFields.addAll(fields);
        }
        sql.setFields(baseFields);
        return this;
    }

    @Override
    public SqlBuilder field(Field field) {
        List<Field> fields = new ArrayList<>();
        fields.add(field);
        return field(fields);
    }

    @Override
    public SqlBuildImpl select() {
        sql.setSqlType(SQL.TYPE_SELECT);
        build();
        return this;
    }


    @Override
    public SqlBuilder update() {
        sql.setSqlType(SQL.TYPE_UPDATE);
        build();
        return this;
    }

    @Override
    public SqlBuilder insert() {
        sql.setSqlType(SQL.TYPE_INSERT);
        build();
        return this;
    }

    @Override
    public SqlBuilder delect() {
        sql.setSqlType(SQL.TYPE_DELECT);
        build();
        return this;
    }

    @Override
    public SqlBuilder where(Where where) {
        List<Where> wheres = new ArrayList<>();
        wheres.add(where);
        return where(wheres);
    }

    @Override
    public SqlBuilder where(String field, String value) {
        Where where = new Where(field, value);
        return this.where(where);
    }

    @Override
    public SqlBuilder where(List<Where> wheres) {
        List<Where> baseWheres = sql.getWheres();
       if (baseWheres == null){
           baseWheres = wheres;
       }else {
           baseWheres.addAll(wheres);
       }
       sql.setWheres(baseWheres);
       return this;
    }

    @Override
    public SqlBuilder order(Order order) {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        return order(orders);
    }

    @Override
    public SqlBuilder order(List<Order> orders) {

        List<Order> baseOrders = sql.getOrders();
        if (baseOrders == null){
            baseOrders = orders;
        }else{
            baseOrders.addAll(orders);
        }
        sql.setOrders(baseOrders);
        return this;
    }

    @Override
    public SqlBuilder limit(Limit limit) {
        sql.setLimit(limit);
        return this;
    }

    @Override
    public SqlBuilder param(List<Param> params) {

        List<Param> baseParams = sql.getParams();

        if(baseParams == null){
            baseParams = params;
        }else {
            baseParams.addAll(params);
        }

        sql.setParams(baseParams);

        return this;
    }

    @Override
    public SqlBuilder param(Param param) {

        List<Param> params = new ArrayList<>();

        params.add(param);

        return param(params);

    }

    @Override
    public Page page() {
        Page page = new Page();

        if (sql.getSqlType() != SQL.TYPE_SELECT){
            throw new JdbcUtilException("非查询操作无法获取分页数据！");
        }

        page.setPageSize(sql.getLimit().getPageSize());

        page.setCurrentPage(sql.getLimit().getPageNumber());

        Map result = null;
        try {
            result = jdbcUtil.find(
                    sql.getLimitSql())
                    .get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert result != null;
        page.setTotal((Integer) result.get("count"));

        page.countTotalPage();


        page.setContent(sql.result);

        return page;
    }

    @Override
    public List getResult() {
        return sql.result;
    }

    @Override
    public SqlBuilder build() {

        String sqlStr = "";

        switch (sql.getSqlType()){

            case SQL.TYPE_INSERT:
                //增
                sqlStr = "INSERT INTO `{{table}}` ({{fields}}) values ({{values}})";

                Table table = sql.getTable();

                sqlStr = sqlStr.replace("{{table}}", table.getTableName());

                //处理field
                List<Field> fields = sql.getFields();

                StringBuilder params = new StringBuilder();
                for (Field field : fields){
                    params.append("?, ");
                }

                // 切割掉最后的一个,
                params = new StringBuilder(params.substring(0, params.length() - 2));

                sqlStr = SqlBuildUtil.replaceField(sqlStr, sql);

                sqlStr = sqlStr.replace("{{values}}", params.toString());

                if (sql.getParams().size() != fields.size()){
                    // 参数个数不匹配
                    throw new JdbcUtilException("Parameter mismatch");
                }

                jdbcUtil.insert(sqlStr, sql.getParams());

                break;

            case SQL.TYPE_DELECT:
                //删除
                sqlStr = "DELETE FROM {{table}} {{WHERE}}";
                String tableName = sql.getTable().getTableName();
                sqlStr = sqlStr.replace("{{table}}", tableName);

                sqlStr = SqlBuildUtil.replaceWhere(sqlStr, sql);

                jdbcUtil.delect(sqlStr, sql.getParams());
                break;

            case SQL.TYPE_UPDATE:
                //改
                sqlStr = "UPDATE {{table}} SET {{value}}";
                tableName = sql.getTable().getTableName();


                StringBuilder updateFields = new StringBuilder();
                fields = sql.getFields();
                for (Field field : fields){
                    updateFields.append(field.getFiledName()).append("=?, ");
                }

                // 切割掉最后的一个,
                updateFields = new StringBuilder(updateFields.substring(0, updateFields.length() - 2));


                sqlStr = sqlStr.replace("{{table}}", tableName)
                        .replace("{{values}}", updateFields.toString());

                // 如果存在where条件就进行查询。
                if (sql.getWheres().size() != 0){
                    sqlStr += "{{WHERE}";
                    sqlStr = SqlBuildUtil.replaceWhere(sqlStr, sql);
                }

                jdbcUtil.update(sqlStr, sql.getParams());

                break;

            case SQL.TYPE_SELECT:

                selectOperation();

                break;

            default:
                break;
        }

        return null;
    }

    private void selectOperation(){

        String limitSql = "";
        String sqlStr = "";
        //查询
        if (jdbcUtil.getDBType() == DBTypeEnums.MYSQL){
            sqlStr = "SELECT {{field}} FROM {{table}} {{JOININ}} {{WHERE}} {{ORDERBY}} {{LIMIT}}";

        }else if (jdbcUtil.getDBType() == DBTypeEnums.SQLSERVER && sql.getLimit() != null){
            sqlStr = "select {{field}} from(select ROW_NUMBER() over(order by {{LIMIT_ORDERBY}}) rownumber,* FROM {{table}}{{WHERE}})TTT WHERE TTT.rownumber between {{begin}} and {{end}}";

        }else if (jdbcUtil.getDBType() == DBTypeEnums.SQLSERVER){
            sqlStr = "select {{field}} from {{table}} {{WHERE}} {{ORDERBY}}";

        }

        // 表
        sqlStr = SqlBuildUtil.replaceTable(sqlStr, sql);


        //todo join in
        sqlStr = sqlStr.replace("{{JOININ}}", "");


        // 分页
        sqlStr = SqlBuildUtil.replaceLimit(sqlStr,
                sql, DBTypeEnums.SQLSERVER);

        // 排序
        sqlStr = SqlBuildUtil.replaceOrder(sqlStr, sql);


        // 查询条件
        sqlStr = SqlBuildUtil.replaceWhere(sqlStr, sql);


        limitSql = sqlStr;
        // 字段
        sqlStr = SqlBuildUtil.replaceField(sqlStr, sql);

        limitSql = limitSql.replace("{{field}}", " count(*) as count ");
        limitSql = limitSql.substring(0, limitSql.indexOf("TTT") + 3);
        sql.setLimitSql(limitSql);

        System.out.println(sqlStr);
        Class clazz;
        // 如果table的长度位1，单表查询
        if ((clazz = sql.getTable().getaClass()) != null){
            sql.result = jdbcUtil.findAll(sqlStr, sql.getParams(),clazz);
        }else {
            sql.result = jdbcUtil.findAll(sqlStr, sql.getParams());
        }
    }












}
