package com.udemy.testing.spring_boot_testing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springTestingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Testing API")
                        .description("Spring Boot Testing application with accounts and banks")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Diego Romero Panes")
                                .email("Diegoignacioromeropanes@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
