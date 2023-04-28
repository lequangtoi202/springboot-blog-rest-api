package com.quangtoi.blogrestfulapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring boot App Rest APIs",
                description = "Spring boot App Rest APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "quang toi",
                        email = "toiquangle@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring boot App Rest APIs Documentation",
                url = "https://github.com/lequangtoi202/springboot-blog-rest-api"
        )
)
public class BlogRestfulApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogRestfulApiApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
