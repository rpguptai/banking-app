/**
 * 
 */
package com.bank.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO: Auto-generated Javadoc
/**
 * Configure for swagger
 * swagger URL : http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bank.account.controllers")).paths(PathSelectors.any()).build()
				.apiInfo(getApiInfo());
	}

	/**
	 * Gets the api info.
	 *
	 * @return the api info
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Swagger UI for Banking Account").version("1.0").description("This is light weight banking account APIs")
				.contact(new Contact("Ravi Gupta", "http://www.ravipgupta.com", "rpgupta.nl@gmail.com"))
				.license("Apache License Version 2.0").build();
	}

}