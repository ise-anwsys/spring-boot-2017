package de.tuberlin.anwsys.springdemo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A simple Jpa Entity
 * A User can own several wishlists
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	private String name;
	@OneToMany(mappedBy="owner")
	private List<Wishlist> wishlists = new ArrayList<>();
	
	// for jpa
	protected User() {
		
	}
	
	public User(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Wishlist> getWishlists() {
		return wishlists;
	}

	public void setWishlists(List<Wishlist> wishlists) {
		this.wishlists = wishlists;
	}
	
	
}
