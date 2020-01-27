package com.eventosapp.eventosapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.eventosapp.controllers" })
@EntityScan(basePackages = { "com.eventosapp.models" })
@EnableJpaRepositories("com.eventosapp.repository")
public class EventosappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventosappApplication.class, args);
	}

}
