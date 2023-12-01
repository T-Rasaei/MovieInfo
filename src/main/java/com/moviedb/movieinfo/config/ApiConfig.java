package com.moviedb.movieinfo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@OpenAPIDefinition(info = @Info(title = "Movie Info Api", version = "1.0",
        contact = @Contact(name = "taherehrasaei",email = "taherehrasaei@gmail.com"))
)
@Configuration
public class ApiConfig {
    @Bean
    public RestTemplate restTesmplate() {
        return new RestTemplate();
    }
}
