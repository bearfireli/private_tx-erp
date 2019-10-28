package com.hntxrj.txerp.util.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取Connection
 * @author lhr
 * @create 2017/12/22
 */
@Component
public class BaseConnention {


    @Autowired
    private void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    };

    private DataSource dataSource;
    private String DBType = "SQLServer";

    private Connection connection;


    public Connection getConnection() throws SQLException {
        // 为了防止druid自己关闭连接使用this.connection.isClosed()进行判断是否连接已经被close
        if (this.connection == null || this.connection.isClosed()){
            this.connection = dataSource.getConnection();
        }
        return this.connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            connection = null;
            e.printStackTrace();
        }
    }

    public String getDBType() {
        return DBType == null ? "mysql" : DBType;
    }

    public void setDBType(String dbtype) {
        this.DBType = dbtype;
    }
}
