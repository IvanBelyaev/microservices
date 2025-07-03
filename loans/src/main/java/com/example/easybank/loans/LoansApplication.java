package com.example.easybank.loans;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "EasyBank Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Ivan Belyaev",
						email = "belivan84@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		)
)
@SpringBootApplication
@EnableJpaAuditing
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
