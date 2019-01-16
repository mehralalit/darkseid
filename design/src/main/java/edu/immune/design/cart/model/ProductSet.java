package edu.immune.design.cart.model;

/**
 * Mimics clubbing of similar products 
 * @author lalit
 *
 */
public class ProductSet {

	/**
	 * The actual Product
	 */
	private Product product;
	
	/**
	 * Quantity of the product 
	 */
	private int count;

	/**
	 * Create a new ProductSet
	 * Add a new Product to the ProductSet
	 * Upon Addition of a new Product the initial quantity is set as 1
	 * @param product
	 */
	public ProductSet(Product product) {
		this.product = product;
		this.count = 1;
	}
	
	/**
	 * Add a new Product to to
	 * @param product
	 * @param count
	 */
	public ProductSet(Product product, int count) {
		this.product = product;
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public int getCount() {
		return count;
	}

	public void incrementValue(int value) {
		count += value;
	}
	
	/**
	 * Calculate and returns value of a Product in the Product Set
	 * productValue = productPrice * productCount
	 * @return
	 */
	public double productValue() {
		return this.product.getPrice() * count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSet other = (ProductSet) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
