package br.com.CarlosHenriqueSL.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("REST APIs RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
                .version("v1")
                .description("REST APIs RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
        );
    }
}
