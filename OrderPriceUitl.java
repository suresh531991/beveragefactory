package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OrderPriceUitl {

	private static Map<String, List<String>> menuItems = new HashMap<>();

	private static Map<String, Double> menuPrice = new HashMap<>();

	// public static void main(String[] args) {
	//
	// // TODO Auto-generated method stu
	// String[] orders = { "Chai" };
	// populateItems();
	// Double totalBill = orderPrice(orders);
	//
	// System.out.println("Ttoal order billl is " + totalBill);
	//
	// }

	public Map<String, List<String>> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(Map<String, List<String>> menuItems) {
		this.menuItems = menuItems;
	}

	public Map<String, Double> getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(Map<String, Double> menuPrice) {
		this.menuPrice = menuPrice;
	}

	public OrderPriceUitl() {
		populateItems();
	}

	public static Double orderPrice(String[] orders) {

		Double price = 0.0;

		if (orders.length > 0) {

			for (int i = 0; i < orders.length; ++i) {

				String order = orders[i];

				String[] ingrdentiants = order.split(", -");
				double totalExlusion = 0;

				if (ingrdentiants.length > 1) {

					List<String> items = menuItems.get(ingrdentiants[0].toLowerCase());

					for (int j = 1; j < ingrdentiants.length; j++) {

						String exclusionContent = ingrdentiants[j];

						items.remove(exclusionContent);

						if (null != menuPrice.get(exclusionContent)) {

							totalExlusion = totalExlusion + menuPrice.get(exclusionContent.toLowerCase());
						}

					}
					System.out.println(items);
					if (items.isEmpty()) {
						return price;
					}

				}
				Double totalPrice = menuPrice.get(ingrdentiants[0].toLowerCase());

				price = price + (totalPrice - totalExlusion);

			}
		}
		return price;
	}

	public void populateItems() {

		List<String> coffeIngredients = new ArrayList<>();
		coffeIngredients.add("coffee");
		coffeIngredients.add("milk");
		coffeIngredients.add("sugar");
		coffeIngredients.add("water");

		List<String> chaiIngredients = new ArrayList<>();
		chaiIngredients.add("tea");
		chaiIngredients.add("milk");
		chaiIngredients.add("sugar");
		chaiIngredients.add("water");

		List<String> bananaSmoothieIngredients = new ArrayList<>();

		bananaSmoothieIngredients.add("banana");
		bananaSmoothieIngredients.add("milk");
		bananaSmoothieIngredients.add("sugar");
		bananaSmoothieIngredients.add("water");

		List<String> strawbeeryShakeeIngredients = new ArrayList<>();
		strawbeeryShakeeIngredients.add("strawberries");
		strawbeeryShakeeIngredients.add("milk");
		strawbeeryShakeeIngredients.add("sugar");
		strawbeeryShakeeIngredients.add("water");

		List<String> mojitoIngredients = new ArrayList<>();
		mojitoIngredients.add("lemon");
		mojitoIngredients.add("sugar");
		mojitoIngredients.add("water");
		mojitoIngredients.add("soda");
		mojitoIngredients.add("mint");

		menuItems.put("coffee", coffeIngredients);
		menuItems.put("chai", chaiIngredients);
		menuItems.put("banana", bananaSmoothieIngredients);
		menuItems.put("strawberry", strawbeeryShakeeIngredients);

		menuItems.put("mojito", mojitoIngredients);

		menuPrice.put("coffee", 5.0);
		menuPrice.put("chai", 4.0);
		menuPrice.put("banana", 6.0);
		menuPrice.put("strawberry", 7.0);
		menuPrice.put("mojito", 7.5);
		menuPrice.put("milk", 1.0);
		menuPrice.put("sugar", 0.5);
		menuPrice.put("Soda", 0.5);
		menuPrice.put("mint", 0.5);
		menuPrice.put("Soda", 5.0);
		menuPrice.put("water", 0.5);
		menuPrice.put("tea", 1.0);

	}

}
