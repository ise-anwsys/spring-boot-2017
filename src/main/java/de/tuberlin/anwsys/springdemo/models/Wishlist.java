package de.tuberlin.anwsys.springdemo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * A simple Jpa Entity
 *
 *
 */
@Entity
public class Wishlist {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="WISHLIST_ID")
	private long id;
	private String name;
	
	// Creation does not require an owner
	// as a field. Side effect: It is impossible to move ownership of a wishlist
	@JsonIgnore
	@ManyToOne
	private User owner;
	
	//Not a bidirectional relationship
	@ManyToMany
	@JoinColumn(name="PROD_ID", referencedColumnName="WISHLIST_ID")
	private List<Product> products = new ArrayList<>();
	
	protected Wishlist() {
		
	}
	
	public Wishlist(String name, User owner) {
		this.name = name;
		this.owner = owner;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
}


