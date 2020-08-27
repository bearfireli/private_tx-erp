package com.hntxrj.txerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lhr
 */
@SpringBootApplication
@ImportResource(value = {"classpath:context.xml"})
@MapperScan("com.hntxrj.txerp.mapper")
@CrossOrigin
@EnableCaching
public class ErpPhoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpPhoneApplication.class, args);
    }
}