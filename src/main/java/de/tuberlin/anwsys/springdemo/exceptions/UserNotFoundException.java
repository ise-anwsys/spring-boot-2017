package de.tuberlin.anwsys.springdemo.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long userId) {
		super("User with id " + userId + " was not found.");
	}

}
