package edu.immune.design.cart;

import org.apache.logging.log4j.ThreadContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.immune.design.cart.exception.NonExistentProductException;
import edu.immune.design.cart.model.Cart;
import edu.immune.design.cart.model.Product;

public class CartTest {
	
	@BeforeClass
	public static void before() {
		ThreadContext.put("type", "random");
	}
	
	@Test
	public void testEmptyCart() {
		Cart cart = new Cart();		
		Assert.assertTrue(cart.isEmpty());
	}
	
	@Test
	public void testFilledCart() {
		Cart cart = new Cart();
		Product product = new Product(1, "Soap", 10.5);
		cart.add(product);
		Assert.assertFalse(cart.isEmpty());
	}
	
	@Test
	public void testNoOfProductsInCart() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(2, "Soap", 10.5);
		Product p3 = new Product(3, "Soap", 10.5);
		cart.add(p1);
		cart.add(p2);
		cart.add(p3);
		Assert.assertEquals(3, cart.count());
	}
	
	@Test
	public void testDistinctProductsInCart() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(1, "Soap", 10.5);
		Product p3 = new Product(2, "Soap", 10.5);
		cart.add(p1);
		cart.add(p2);
		cart.add(p3);
		Assert.assertEquals(2, cart.count());
	}
	
	@Test
	public void testNoOfSameProductsInCart() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(1, "Soap", 10.5);
		Product p3 = new Product(2, "Soap", 10.5);
		cart.add(p1);
		cart.add(p2);
		cart.add(p3);
		Assert.assertEquals(2, cart.count(p1));
		Assert.assertEquals(1, cart.count(p3));
	}
	
	@Test
	public void testSingleProductPrice() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(1, "Soap", 10.5);
		Product p3 = new Product(2, "Apple", 12);
		cart.add(p1);
		cart.add(p2);
		cart.add(p3);
		Assert.assertEquals(10.5d, cart.getPrice(p1), 0.01);
	}
	
	@Test(expected = NonExistentProductException.class)
	public void testNonExitingProductPrice() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(3, "Watch", 50);
		cart.add(p1);
		Assert.assertEquals(10.5d, cart.getPrice(p2), 0.01);
	}
	
	@Test
	public void testCartValue() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(1, "Soap", 10.5);
		Product p3 = new Product(2, "Apple", 12);
		cart.add(p1);
		cart.add(p2);
		cart.add(p3);
		Assert.assertEquals(33d, cart.getCartValue(), 0.001);
	}
	
	@Test
	public void testEmptyCartValue() {
		Cart cart = new Cart();
		Assert.assertEquals(0, cart.getCartValue(), 0.001);
	}
	
	@Test(expected = NonExistentProductException.class)
	public void testNonExistingProductCount() {
		Cart cart = new Cart();
		Product p1 = new Product(1, "Soap", 10.5);
		Product p2 = new Product(2, "Apple", 12);
		cart.add(p1);
		Assert.assertEquals(2, cart.count(p2));
	}
	
	
	
}
