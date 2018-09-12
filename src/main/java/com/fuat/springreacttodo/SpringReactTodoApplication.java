package com.fuat.springreacttodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Start application without security credentials
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringReactTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactTodoApplication.class, args);
	}
}
