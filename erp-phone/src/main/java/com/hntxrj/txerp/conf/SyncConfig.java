package com.hntxrj.txerp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.hntxrj.SyncPlugin;

/**
 * 此类用户同步erp线上数据和本地服务器的数据
 */
@Component
public class SyncConfig {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SyncConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public SyncPlugin syncPlugin() {
        return new SyncPlugin(jdbcTemplate);
    }


}
