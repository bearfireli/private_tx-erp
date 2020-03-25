package com.hntxrj.txerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author nsk
 */
@SpringBootApplication
@MapperScan("com.hntxrj.txerp.mapper")
@CrossOrigin
@EnableCaching
public class ErpTankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpTankApplication.class, args);
    }

}
