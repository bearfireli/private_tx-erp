package com.hntxrj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lhr
 */
@SpringBootApplication
@MapperScan("com.hntxrj.mapper")
@CrossOrigin
@EnableCaching
public class SpterpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpterpApplication.class, args);
    }
}