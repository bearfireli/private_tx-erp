package com.hntxrj.util.jdbc.procedure;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.util.jdbc.BaseConnention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * 封装存储过程调用函数
 * 按照构造器创建该类，
 * --出参
 * 如果带出参，待commit()执行完毕
 * 通过getOutParamters()方法获取出参对象。
 * 备注：如果未执行commit()调用getOutParamters()方法
 * 虽然可以拿到集合，但是该集合中Param的data属性为空。
 * --结果集
 * 同上，获取必须先执行commit();
 * 结果集中对所有查询进行封装（包括空结果集），
 * 循环进行rs处理时，请自行进行非空判断。
 * 返回ResultSet集合。
 *
 * @author 刘浩然
 * @date 2017/7/27
 */
@Slf4j
@Component
@Scope("prototype")
public class Procedure {

    //    private final BaseConnention baseConnention;
    private ProcedurePOJO procedurePOJO = null;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Procedure(BaseConnention baseConnention, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        procedurePOJO = new ProcedurePOJO();
//        this.baseConnention = baseConnention;
    }


    /**
     * 带入参存储过程
     *
     * @param procedureName  存储过程名
     * @param comeParameters 入参集合
     */
    public void init(String procedureName, List<Param> comeParameters) {
        procedurePOJO.setProcedureName(procedureName);
        procedurePOJO.setComeParameters(comeParameters);
    }

    /**
     * 不带入参的存储过程
     *
     * @param procedureName 存储过程名
     */
    public void init(String procedureName) {
        procedurePOJO.setProcedureName(procedureName);
    }

    /**
     * 带入参和出参
     *
     * @param procedureName  存储过程名
     * @param comeParameters 入参
     * @param outParameters  出参
     */
    public void init(String procedureName, List<Param> comeParameters, List<Param> outParameters) {
        procedurePOJO.setProcedureName(procedureName);
        procedurePOJO.setComeParameters(comeParameters);
        procedurePOJO.setOutParameters(outParameters);
    }


    public void commit() throws Exception {
        //拼接存储过程执行sql
        /* 获取基本sql */
        String baseSql = this.getBaseSql();
        /* 创建CallableStatement对象 */
        if (jdbcTemplate.getDataSource() == null) {
            throw new RuntimeException("获取DataSource失败");
        }
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        //预编译
        CallableStatement cstm = null;
        try {
            cstm = conn.prepareCall(baseSql);

        } catch (Exception e) {
            System.out.println(baseSql);
            e.printStackTrace();
        }
        /* 设置超时时间 */
        ResultSet rs = null;
        if (cstm == null) {
            throw new RuntimeException("CallableStatement is null");
        }
        cstm.setQueryTimeout(40);
        /*
            拼接参数,当参数大于一个，即有参数时执行拼接参数;
        */
        if (procedurePOJO.getComeParameters().size() > 0) {
            cstm = this.setParam(cstm);
        }

        /*
            提交
        */
        boolean hashResult = false;
        try {
            hashResult = cstm.execute();
        } catch (SQLException e) {
            log.error("【调用存储过程失败】 errmsg={}, name={}, Param={}," +
                            " resultArray={}, outParam={}",
                    e.getMessage(),
                    this.procedurePOJO.getProcedureName(),
                    this.procedurePOJO.getComeParameters(),
                    this.procedurePOJO.getResultArray(),
                    this.procedurePOJO.getOutParameters());
            StringBuilder sql = new StringBuilder("exec " + procedurePOJO.getProcedureName());
            for (Param param : procedurePOJO.getComeParameters()) {
                sql.append(" '").append(param.getData()).append("',");
            }
            sql.delete(sql.lastIndexOf(",") - 1, sql.lastIndexOf(","));
            log.error("【error sql】 sql={}", sql);
            e.printStackTrace();
        }
        /*
            获取出参
         */
        if (this.procedurePOJO.getOutParameters() != null &&
                this.procedurePOJO.getOutParameters().size() > 0) {
            for (int i = 0; i < this.procedurePOJO.getOutParameters().size(); i++) {
                Param param = this.procedurePOJO.getOutParameters().get(i);
                param.setData(cstm.getObject(param.getLocation()));
                this.procedurePOJO.getOutParameters().set(i, param);
            }
        }
        /*
            获取结果集
        */
        //  清空结果集
        procedurePOJO.setResultArray(new JSONArray());
        while (true) {
            //判断本次循环是否为数据集
            if (hashResult) {
                rs = cstm.getResultSet();
                JSONArray jsonresult = resultSetToJSONArray(rs);
                // 此处因为有多次循环所以使用add

                procedurePOJO.getResultArray().add(jsonresult);
            } else {
                int updateCount = cstm.getUpdateCount();
                if (updateCount == -1) {
                    /*
                        当updateCount为-1时，
                        代表存储过程返回的最后一条数据集
                        跳出循环
                    */
                    break;
                }
                // 如果需要加入空结果集打开下面注释
                // resultSets.add(null);
                // Do something with update count ...
            }
            try {
                hashResult = cstm.getMoreResults();
            } catch (SQLException e) {
                log.error("【获取Result失败】ErrorCode={}, msg={}", e.getErrorCode(), e.getMessage());
            }
        }
        log.debug("【调用存储过程】 name={}, Param={}, resultArray={}, outParam={}",
                this.procedurePOJO.getProcedureName(),
                this.procedurePOJO.getComeParameters(),
                this.procedurePOJO.getResultArray(),
                this.procedurePOJO.getOutParameters());

        StringBuilder sql = new StringBuilder("exec " + procedurePOJO.getProcedureName());
        for (Param param : procedurePOJO.getComeParameters()) {
            if (param.getType() == Types.VARCHAR
                    || param.getType() == Types.DATE
                    || param.getType() == Types.CHAR) {
                sql.append(" '").append(param.getData().toString().equals("") ? "0" : param.getData().toString()).append("',");
            } else {
                sql.append(" ").append(param.getData().toString().equals("") ? "''" : param.getData().toString()).append(",");
            }
        }
        log.info("【sql】 sql={}", sql.toString()
                .substring(0, sql.toString().length() - 1));

        /* 关闭连接 */


        if (!conn.isClosed()) {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (!cstm.isClosed()) {
                    cstm.close();
                }
                conn.close();

            } catch (Exception e) {
                conn = null;
            }
        }
    }

    /**
     * 根据出餐和入参个数获取存储过程sql{call xxxx(?,?,?)}类型
     *
     * @return
     */
    private String getBaseSql() {
        /* 创建参数模板 */
        String procedureModel = "{call " + procedurePOJO.getProcedureName() + "(#{params})}";
        /* 计算参数 */
        if (procedurePOJO.getComeParameters().size() == 0) {
            return procedureModel.replace("#{params}", "");
        }
        /* 替换params为?,?,?形式 */
        StringBuilder param = new StringBuilder();
        for (int i = 0; i < procedurePOJO.getComeParameters().size(); i++) {
            param.append("?,");
        }
        if (param.length() >= 1) {
            //去掉最后的，
            param = new StringBuilder(param.substring(0, param.length() - 1));
        }
        return procedureModel.replace("#{params}",
                param.toString().equals("null") ? "" : param.toString());
    }

    /**
     * 植入参数
     *
     * @param cstm
     * @return
     * @throws SQLException
     */
    private CallableStatement setParam(CallableStatement cstm) throws SQLException {
        /* 配置入参 */
        if (this.procedurePOJO.getComeParameters() != null) {
            for (Param param : this.procedurePOJO.getComeParameters()) {
                //转义%
                if (param.getData() != null && param.getData().toString().contains("%")) {
                    String replace = param.getData().toString().replace("%", "[%]");
                    param.setData(replace);
                }

                if (param.getData() == null || "".equals(param.getData().toString())) {
                    cstm.setString(param.getLocation(), null);
                } else {
                    cstm.setString(param.getLocation(), param.getData().toString());
                }

            }
        }
        /* 配置出参 */
        if (procedurePOJO.getOutParameters() != null) {
            for (Param param : procedurePOJO.getOutParameters()) {
                cstm.registerOutParameter(param.getLocation(), param.getType());
            }
        }
        return cstm;
    }


    /**
     * 结果集转数组
     *
     * @param rs 结果集
     * @return list
     * @throws java.sql.SQLException
     */
    public static List resultSetToList(ResultSet rs) throws java.sql.SQLException {
        if (rs == null) {
            return Collections.EMPTY_LIST;
        }
        //得到结果集(rs)的结构信息，比如字段数、字段名等
        ResultSetMetaData md = rs.getMetaData();
        //返回此 ResultSet 对象中的列数
        int columnCount = md.getColumnCount();
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> rowData;
        while (rs.next()) {
            rowData = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * 结果集转jsonarray
     *
     * @param rs
     * @return
     * @throws java.sql.SQLException
     */
    public static JSONArray resultSetToJSONArray(ResultSet rs) throws java.sql.SQLException {
        if (rs == null) {
            return new JSONArray();
        }
        //得到结果集(rs)的结构信息，比如字段数、字段名等
        ResultSetMetaData md = rs.getMetaData();
        //返回此 ResultSet 对象中的列数
        int columnCount = md.getColumnCount();
        JSONArray array = new JSONArray();
        JSONObject rowData;
        while (rs.next()) {
            rowData = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            array.add(rowData);
        }
        return array;
    }

    public List<Param> getComeParameters() {
        return this.procedurePOJO.getComeParameters();
    }

    public void setComeParameters(List<Param> comeParameters) {
        this.procedurePOJO.setComeParameters(comeParameters);
    }

    public String getProcedureName() {
        return this.procedurePOJO.getProcedureName();
    }

    public void setProcedureName(String procedureName) {
        this.procedurePOJO.setProcedureName(procedureName);
    }

    public List<Param> getOutParameters() {
        return this.procedurePOJO.getOutParameters();
    }

    public void setOutParameters(List<Param> outParameters) {
        this.procedurePOJO.setOutParameters(outParameters);
    }

    public List getResultSets() {
        return this.procedurePOJO.getResultSets();
    }

    public void setResultSets(List resultSets) {
        this.procedurePOJO.setResultSets(resultSets);
    }

    public JSONArray getResultArray() {
//        JSONArray resultArray = procedurePOJO.getResultArray();
//        procedurePOJO.getResultArray().clear();
        return procedurePOJO.getResultArray();
    }

    public void setResultArray(JSONArray resultArray) {

        this.procedurePOJO.setResultArray(new JSONArray());
        this.procedurePOJO.setResultArray(resultArray);
    }


}
