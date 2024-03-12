package main.resources.com.bookstore.controller.frontend.shoppingcart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.resources.com.bookstore.entity.Product;

public class ShoppingCart {
private Map<Product, Integer> cart = new HashMap<>();
	
	public void addItem(Product product) {
		if (cart.containsKey(product)) {
			Integer quantity = cart.get(product) + 1;
			cart.put(product, quantity);
		} else {
			cart.put(product, 1);
		}
	}
	
	public void removeItem(Product product) {
		cart.remove(product);
	}
	
	public int getTotalQuantity() {
		int total = 0;
		
		Iterator<Product> iterator = cart.keySet().iterator();
		
		while (iterator.hasNext()) {
			Product next = iterator.next();
			Integer quantity = cart.get(next);
			total += quantity;
		}
		
		return total;
	}
	
	public float getTotalAmount() {
		float total = 0.0f;
		
        Iterator<Product> iterator = cart.keySet().iterator();
		
		while (iterator.hasNext()) {
			Product product = iterator.next();
			Integer quantity = cart.get(product);
			double subTotal = quantity * product.getPrice();
			total += subTotal;
		}
		
		return total;
	}
	
	public void updateCart(int[] productIds, int[] quantities) {
		for (int i = 0; i < productIds.length; i++) {
			Product key = new Product(productIds[i]);
			Integer value = quantities[i];
			cart.put(key, value);
		}
	}
	
	public void clear() {
		cart.clear();
	}
	
	public int getTotalItems() {
		return cart.size();
	}
	
	public Map<Product, Integer> getItems() {
		return this.cart;
	}
}
