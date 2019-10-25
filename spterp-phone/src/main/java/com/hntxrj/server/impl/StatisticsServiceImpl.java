package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.server.StatisticsService;
import com.hntxrj.util.jdbc.procedure.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JSONArray phoneStatistics(String compid, int type, String beginTime, String endTime) {


        String sql = "{ call p_PhoneStatistics(?,?,?,?) }";
        CallableStatementCreator csc = connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, compid);
            cs.setInt(2, type);
            cs.setString(3, beginTime);
            cs.setString(4, endTime);
            return cs;
        };
        return jdbcTemplate.execute(csc, new CallableStatementCallback<JSONArray>() {
            @Override
            public JSONArray doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {

                return Procedure.resultSetToJSONArray(callableStatement.executeQuery());
            }
        });
    }
}
