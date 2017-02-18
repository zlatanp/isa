package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories
public class RestoranApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestoranApplication.class);
    }
	
	public static void main(String[] args) {
		System.out.println("a ");
		// prva izmena na 2.2.
		// druga izmena za 2.2.
		SpringApplication.run(RestoranApplication.class, args);
	}
	
	
}
