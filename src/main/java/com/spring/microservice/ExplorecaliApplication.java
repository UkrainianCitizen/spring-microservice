package com.spring.microservice;

import com.spring.microservice.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main Class for the Spring Boot Application.
 *
 * @author Alexander Huba
 */
@SpringBootApplication
@EnableSwagger2
@Import(ApplicationConfig.class)
public class ExplorecaliApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExplorecaliApplication.class, args);
    }
}