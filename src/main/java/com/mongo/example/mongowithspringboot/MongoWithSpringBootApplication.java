package com.mongo.example.mongowithspringboot;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class MongoWithSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MongoWithSpringBootApplication.class, args);
  }

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(any())
        .build()
        .apiInfo(metaData());
  }
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Spring Boot REST API")
        .description("\"Spring Boot REST API for UserLocations\"")
        .version("1.0.0")
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
        .contact(new Contact("Rafael Tumasyan","org.rr.com", "r.t@Gmail.com"))
        .build();
  }

}
