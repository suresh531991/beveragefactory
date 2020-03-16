package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceApi {

	@Autowired
	private OrderPriceUitl orderPriceUitl;

	@PostMapping("/order")
	public Double orderPrice(@RequestBody String[] orders) {

		return orderPriceUitl.orderPrice(orders);

	}

}
