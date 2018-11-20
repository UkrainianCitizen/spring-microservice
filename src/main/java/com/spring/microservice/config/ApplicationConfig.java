package com.spring.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Application bean configuration.
 *
 * @author Alexander Huba
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.microservice.web")).build()
                .apiInfo(new ApiInfo("Explore California API's",
                        "API's for the Explore California Travel Service", "2.0", null,
                        null, null, null, Collections.emptyList()));
    }}