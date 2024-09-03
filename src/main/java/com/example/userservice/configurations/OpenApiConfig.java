package com.example.userservice.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "UserService APP",
        version = "V1",
        description = "API for managing users in the UserService application."
))
public class OpenApiConfig {
}
