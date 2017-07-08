package de.tuberlin.anwsys.springdemo.exceptions;

public class OwnerMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OwnerMismatchException(Long wishlistId) {
		super("The owner for wishlist " + wishlistId + " did not match the expected owner");
	}
}
