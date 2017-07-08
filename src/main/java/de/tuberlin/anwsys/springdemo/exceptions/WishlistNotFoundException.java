package de.tuberlin.anwsys.springdemo.exceptions;

public class WishlistNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WishlistNotFoundException(Long id) {
		super("Wishlist with id " + id + " was not found.");
	}
}
