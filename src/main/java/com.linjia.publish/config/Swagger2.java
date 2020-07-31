package com.linjia.publish.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* @Description: swagger2 配置类
* @author haoyukui
*/
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createPublishApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("publishAPI")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linjia.publish.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("邻家 publish 服务构建RESTful APIs")
                .description("邻家 publish 服务API文档")
                .termsOfServiceUrl("http://www.linjia.com/")
                .version("1.0")
                .build();
    }

}
