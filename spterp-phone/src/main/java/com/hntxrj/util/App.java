package com.hntxrj.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


//Mightiness  16:10:06
/**
 * @author lhr
 * @create 2017/10/11
 */
@Component
@ConfigurationProperties(prefix="app")
@Data
public class App {
    private Map<String, String> spterp;

}
