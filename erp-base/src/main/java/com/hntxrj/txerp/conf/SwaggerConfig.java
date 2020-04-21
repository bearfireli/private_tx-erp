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
        ParameterBuilder enterprisePar = new ParameterBuilder();

        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("用户令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pidPar.name("pid").description("项目id")
                .modelRef(new ModelRef("int")).parameterType("header")
                .defaultValue("2")
                .required(true).build();

        enterprisePar.name("enterprise").description("企业id")
                .modelRef(new ModelRef("int")).parameterType("header")
                .defaultValue("0")
                .required(true).build();
        pars.add(tokenPar.build());
        pars.add(pidPar.build());
        pars.add(enterprisePar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }
}