package com.hntxrj.txerp.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder pidPar = new ParameterBuilder();

        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pidPar.name("pid").description("user pid")
                .modelRef(new ModelRef("string")).parameterType("header")
                .defaultValue("3")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(tokenPar.build());
        pars.add(pidPar.build()); //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }
}