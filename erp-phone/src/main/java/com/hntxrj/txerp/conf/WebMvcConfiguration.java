package com.hntxrj.txerp.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Bean
    public MethodInterceptor getMethodInterceptor() {
        return new MethodInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //        多个拦截器组成一个拦截器链
        //        addPathPatterns 用于添加拦截规则
        //        excludePathPatterns 用户排除拦截
        registry.addInterceptor(getMethodInterceptor()).addPathPatterns("/*/*").excludePathPatterns(
                "*.css", "*.js", "*.ttf", "*.png", "*.jpg", "*.png", "*.gif", "*.swf", "*.do", "*.txt", "/auth/**");
    }

//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 1添加允许使用的域名，也可以用"*"表示任何域名
//        corsConfiguration.addAllowedOrigin("*");
//        // 2允许任何头
//        corsConfiguration.addAllowedHeader("*");
//        // 3允许任何方法（post、get等）
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }

}
