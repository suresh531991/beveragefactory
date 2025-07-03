package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Expense Management System started successfully!");
		System.out.println("Access H2 Console at: http://localhost:8080/h2-console");
		System.out.println("API Base URL: http://localhost:8080/api/expenses");
	}

}
