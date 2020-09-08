package com.app.apigateway.apidocumentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApiDocumentation {

    @Bean
    public Docket api() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .description("REST API documentation for smooth recruiter project")
                .title("Smooth Recruiter")
                .version("0.1")
                .license("ISC")
                .contact(new Contact(
                        "Przemysław Żochowski",
                        "",
                        "przemyslaw.zochowski@gmail.com"))
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}
