package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private OrderPriceUitl orderPriceUitl;

	@Test
	void contextLoads() {

		Assert.isTrue(!orderPriceUitl.getMenuItems().isEmpty(), "Menu loaded loaded");
		Assert.isTrue(!orderPriceUitl.getMenuPrice().isEmpty(), "Menu price loaded");

	}

	@Test
	public void checkMiniumOrder() {

		String[] orders = null;
		Assert.isTrue(orderPriceUitl.orderPrice(orders) > 0.0, "At least one order to be placed");
		;

	}

	@Test
	public void orderValid() {

		String[] orders = { "Chai, -sugar", "Chai", "Coffee, -milk" };

		Assert.isTrue(orderPriceUitl.orderPrice(orders) == 11.5, "Price computed correctly");
	}

	@Test
	public void OrderNotIncludeExlusion() {

		String[] orders = { "Chai, -Tea, -milk, -sugar, -water " };

		Assert.isTrue(orderPriceUitl.orderPrice(orders) == 0.0, "Order not conatin all exlusion items");

	}

}
