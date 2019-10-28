package com.hntxrj.txerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {
        "com.hntxrj.txerp.core.util",
        "com.hntxrj.txerp.mapper",
        "com.hntxrj.txerp.conf",
        "com.hntxrj.txerp.controller",
        "com.hntxrj.txerp.web.api",
        "com.hntxrj.txerp.service",
        "com.hntxrj.txerp.util"
})
@EnableJpaRepositories("com.hntxrj.txerp.repository")
@MapperScan("com.hntxrj.txerp.mapper")
@EntityScan({
        "com.hntxrj.txerp.entity.base",
        "com.hntxrj.txerp.entity.amqp",
        "com.hntxrj.txerp.entity.oa",
        "com.hntxrj.txerp.entity.sell",
        "com.hntxrj.txerp.entity.system",
        "com.hntxrj.txerp.entity.util"
})
public class ErpBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpBaseApplication.class, args);
    }

}