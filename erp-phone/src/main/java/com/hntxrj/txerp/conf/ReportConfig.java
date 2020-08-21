package com.hntxrj.txerp.conf;

import com.bstek.ureport.console.UReportServlet;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.apache.poi.poifs.nio.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ReportConfig implements BuildinDatasource {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public ServletRegistrationBean<Servlet> ureport2Servlet() {
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }

    @Override
    public String name() {
        return "SPTERPNewData";
    }

    @Override
    public Connection getConnection() {
        try {
            return jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
