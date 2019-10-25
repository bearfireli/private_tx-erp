package com.hntxrj.util.jdbc.sql;

import com.hntxrj.util.jdbc.JdbcUtilException;
import com.hntxrj.util.jdbc.enums.DBTypeEnums;

import java.util.List;

/**
 * @author lhr
 * @create 2018/1/29
 */
class SqlBuildUtil {


    /**
     * 处理查询字段
     * @param sqlStr    sql语句
     * @param sql       sql对象
     * @return          处理完的sql语句
     */
    static String replaceField(String sqlStr, SQL sql){
        if (sql.getFields() == null || sql.getFields().size() == 0){
            sqlStr = sqlStr.replace("{{field}}", "*");
        }else {
            sqlStr = sqlStr.replace("{{field}}", getFieldsToStr(sql.getFields()));
        }
        return sqlStr;
    }

    /**
     * 替换表名
     * @param sqlStr
     * @param sql
     * @return
     */
    static String replaceTable(String sqlStr, SQL sql){
        if(sql.getTable() == null){
            throw new JdbcUtilException("table is not found!");
        }else {
            sqlStr = sqlStr.replace("{{table}}",
                    sql.getTable().getTableName());
        }
        return sqlStr;
    }

    /**
     * 替换where条件
     * @param sqlStr
     * @param sql
     * @return
     */
    static String replaceWhere(String sqlStr, SQL sql){
        if (sql.getWheres() != null && sql.getWheres().size() != 0){
            sqlStr = sqlStr.replace("{{WHERE}}"," WHERE {{WHERE}} ");
            sqlStr = sqlStr.replace("{{WHERE}}",getWhereToStr(sql.getWheres()));

        }else if (sql.getLimit() == null) {
            sqlStr = sqlStr.replace("{{WHERE}}", "");
        }
        return sqlStr;
    }


    static String replaceOrder(String sqlStr, SQL sql){
        if (sql.getOrders() != null && sql.getOrders().size() != 0){

            sqlStr = sqlStr.replace("{{ORDERBY}}",
                    " ORDER BY "
                            + getOrderByStr(sql.getOrders()));

        }else {
            sqlStr = sqlStr.replace("{{ORDERBY}}",  "");
        }
        return sqlStr;
    }


    /**
     * 替换分页
     * @param sqlStr        sql语句
     * @param sql           sql对象
     * @param dbTypeEnums   数据库类型
     * @return              组合好的sql语句
     */
    static String replaceLimit(String sqlStr, SQL sql,
                               DBTypeEnums dbTypeEnums){
        if (sql.getLimit() != null){
            switch (dbTypeEnums){
                case SQLSERVER:
                    // 务必将该方法放在replaceOrder和replaceWhere前面执行

                    int pageNumber = sql.getLimit().getPageNumber();
                    int pageSize = sql.getLimit().getPageSize();
                    int begin = (pageNumber - 1) * pageSize + 1;
                    Field orderByField = sql.getLimit().getOrderByField();
//                    sqlStr = "select * from(select ROW_NUMBER() over(order by {{LIMIT_ORDERBY}}) rownumber,{{field}} FROM {{table}})TTT WHERE TTT.rownumber between {{begin}} and {{end}}";
                    sqlStr = sqlStr
                            .replace("{{LIMIT_ORDERBY}}", orderByField.getFiledName())
                            .replace("{{begin}}", begin+"")
                            .replace("{{end}}", (begin
                                    + sql.getLimit().getPageSize() - 1)+"");



                    break;
                case MYSQL:
                    sqlStr = sqlStr.replace("{{LIMIT}}", sql.getLimit().toString());
                    break;

                default:
                    break;
            }

        }else {
            sqlStr = sqlStr.replace("{{LIMIT}}", "");
            // 替换sqlserver情况
            sqlStr = sqlStr.replace("{{LIMIT_pagesize}}", "");
        }

        return sqlStr;
    }
































    private static String getFieldsToStr(List<Field> fields){
        //处理field

        StringBuilder fieldsStr = new StringBuilder();
        for (Field field : fields){
            fieldsStr.append(field.getFiledName()).append(", ");
        }

        // 切割掉最后的一个,
        fieldsStr = new StringBuilder(fieldsStr.substring(0, fieldsStr.length() - 2));
        // params.add(new Param(2, Param.STRING, insertFields));

        return fieldsStr.toString();

    }
    private static String getWhereToStr(List<Where> wheres){
        StringBuilder wheresStr = new StringBuilder();

        int time = 1;

        for(Where where : wheres){
            if (where.getData() == null){
                continue;
            }

            String ws = where.toString();

            if (time != wheres.size()){
                //最后一个where条件
                ws += " and ";
            }

            wheresStr.append(ws);
            time++;
        }
        return wheresStr.toString();
    }
    private static String getOrderByStr(List<Order> orders){

        StringBuilder orderStr = new StringBuilder();

        for (Order order : orders){
            orderStr.append(order.getFields() + " ");
            switch (order.getType()){
                case Order.TYPE_ASC:
                    orderStr.append("asc");
                    break;
                case Order.TYPE_DESC:
                    orderStr.append("desc");
                default:
                    break;
            }
            orderStr.append(", ");
        }

        return orderStr.substring(0, orderStr.length() - 2);
    }






}
