package edu.immune.design.cart.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import edu.immune.design.cart.exception.NonExistentProductException;

/** 
 * Realization of a Shopping Cart used for purchasing {@link Product} from an Online Store
 * @author lalit
 *
 */
public class Cart {

	private final Logger logger = LogManager.getLogger("EventLogger");
	
	/**
	 * Bucket which stores actual products
	 * @see Product
	 * @see ProductSet
	 */
	private Map<Product, ProductSet> bucket;
	
	public Cart() {
		bucket = new HashMap<Product, ProductSet>();
		logger.info(ThreadContext.get("type"));
	}
	
	/** 
	 * Helps to check if the cart is empty
	 * @return true/false
	 */
	public boolean isEmpty() {
		return bucket.isEmpty();
	}

	/**
	 * Help to add a Product to the Cart
	 * If there already exists a similar Product the quantity for that Product is updated otherwise a new Product is added with initial quantity set to 1 
	 * @see Product
	 * @param product
	 */
	public void add(Product product) {
		if(!bucket.containsKey(product)) {
			ProductSet pset = new ProductSet(product);
			bucket.put(product, pset);
		} else {
			bucket.get(product).incrementValue(1);
		}
	}

	/**
	 * Returns count of distinct Products in the Cart 
	 * @return
	 */
	public int count() {
		return bucket.size();
	}
	
	/**
	 * Returns count of a Particular product
	 * @param product
	 * @return
	 */
	public int count(Product product) throws NonExistentProductException {
		if(!bucket.containsKey(product)) {
			throw new NonExistentProductException();
		}
		return bucket.get(product).getCount();
	}

	/**
	 * Returns price of a Product
	 * @param product
	 * @return
	 */
	public double getPrice(Product product) throws NonExistentProductException {
		if(!bucket.containsKey(product)) {
			throw new NonExistentProductException();
		}
		return bucket.get(product).getProduct().getPrice();
	}

	/**
	 * Returns complete Cart value
	 * @return
	 */
	public double getCartValue() {
		double sum = 0;
		for(ProductSet ps: bucket.values()) {
			sum += ps.productValue();
		}
		return sum;
	}
	
}
