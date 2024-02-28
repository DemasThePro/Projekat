package com.forums.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumsAppApplication.class, args);
		System.out.println("Spring Application is running");
	}

}
