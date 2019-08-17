package edu.immune.design.cart;

import org.junit.Assert;
import org.junit.Test;

import edu.immune.design.cart.model.Product;


public class ProductTest {

	@Test
	public void testProductSetup() {
		Product product = new Product(1, "Soap", 10.50);
		Assert.assertNotNull(product);
	}
	
}
