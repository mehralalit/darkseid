package edu.immune.design.cart.exception;

public class NonExistentProductException extends RuntimeException {

	private static final long serialVersionUID = 5111276968498477321L;
	
	public NonExistentProductException() {
		super("Product does not Exist in the Cart");
	}

}
