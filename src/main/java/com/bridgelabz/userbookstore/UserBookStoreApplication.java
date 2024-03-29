package com.bridgelabz.userbookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.bridgelabz.userbookstore")
@EnableJpaRepositories("com.bridgelabz.userbookstore.repository")
@EnableEurekaClient
public class UserBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserBookStoreApplication.class, args);
	}

}
