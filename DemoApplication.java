package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// String[] orders = { "Chai, -tea, -milk, -sugar, -water" };
		// String[] orders = { "Chai" };
		// String[] orders = { "Chai, -sugar" };
		// String[] orders = { "Chai, -sugar, -milk" };
		String[] orders = { "Chai, -sugar,", "Chai", "Coffee, -milk" };

		System.out.println("Price " + OrderPriceUitl.orderPrice(orders));
	}

}
