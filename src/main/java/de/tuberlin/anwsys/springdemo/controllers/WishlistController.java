package de.tuberlin.anwsys.springdemo.controllers;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.tuberlin.anwsys.springdemo.exceptions.OwnerMismatchException;
import de.tuberlin.anwsys.springdemo.exceptions.ProductNotFoundException;
import de.tuberlin.anwsys.springdemo.exceptions.UserNotFoundException;
import de.tuberlin.anwsys.springdemo.exceptions.WishlistNotFoundException;
import de.tuberlin.anwsys.springdemo.models.User;
import de.tuberlin.anwsys.springdemo.models.Wishlist;
import de.tuberlin.anwsys.springdemo.repos.ProductRepository;
import de.tuberlin.anwsys.springdemo.repos.UserRepository;
import de.tuberlin.anwsys.springdemo.repos.WishlistRepository;

/**
 * The wishlist controller
 * A wishlist belongs to an user.
 * <br/>
 * Also have a look at
 * {@code @PostMapping},
 * {@code @GetMapping},
 * {@code @PutMapping} and 
 * {@code @DeleteMapping}
 */
@RestController
@RequestMapping(path="/wishlist/{userId}")
public class WishlistController {

	@Autowired
	private WishlistRepository wishRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createNewWishlist(@PathVariable Long userId, @RequestBody Wishlist input) {
		this.validateUser(userId);
		
		return this.userRepo.findById(userId).map((User user) -> {
			Wishlist result = wishRepo.save(new Wishlist(input.getName(), user));
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("{/id}").buildAndExpand(result.getId()).toUri();
			
			return ResponseEntity.created(location).build();
		}).orElse(ResponseEntity.noContent().build());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<Wishlist> getAllWishlistsForUser(@PathVariable Long userId) {
		this.validateUser(userId);
		return this.userRepo.findById(userId).map((User user) -> {
			return this.wishRepo.findByOwner(user);
		}).orElse(Collections.emptyList());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public Wishlist getWishlistForUser(@PathVariable Long userId, @PathVariable Long id) {
		this.validateUser(userId);
		return this.wishRepo.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public Wishlist updateWishlistForUser(@PathVariable Long userId, @PathVariable Long id, @RequestBody Wishlist input) {
		this.validateUser(userId);
		this.validateWishlist(id);
		Wishlist currentWishlist = this.wishRepo.findOne(id);
		
		currentWishlist.setName(input.getName());
		currentWishlist.setProducts(input.getProducts());
		
		return this.wishRepo.save(currentWishlist);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Void> deleteWishlistForUser(@PathVariable Long userId, @PathVariable Long id, @RequestBody Wishlist input) {
		this.validateUser(userId);
		this.wishRepo.delete(id);
		// a builder pattern is also available.
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}/{productId}")
	public Wishlist addProductToWishlist(@PathVariable Long userId, @PathVariable Long id,@PathVariable Long productId) {
		this.validateUser(userId);
		this.validateWishlist(id);
		this.validateProduct(productId);
		this.validateMatchingUserAndWishlist(userId, id);
		
		return this.productRepo.findById(productId).map((product) -> {
			Wishlist wishlist = this.wishRepo.findOne(id);
			if(!wishlist.getProducts().contains(product)) {
				wishlist.getProducts().add(product);
			}
			return this.wishRepo.save(wishlist);
		}).orElse(null);
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}/{productId}")
	public ResponseEntity<Object> deleteProductFromWishlist(@PathVariable Long userId, @PathVariable Long id,@PathVariable Long productId) {
		this.validateUser(userId);
		this.validateWishlist(id);
		this.validateProduct(productId);
		this.validateMatchingUserAndWishlist(userId, id);
		
		return this.productRepo.findById(productId).map((product) -> {
			Wishlist wishlist = this.wishRepo.findOne(id);
			if(wishlist.getProducts().contains(product)) {
				wishlist.getProducts().remove(product);
			}
			this.wishRepo.save(wishlist);
			//using the builder pattern with ResponseEntity
			//constructors are also available
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
	/**
	 * Throws an expection if the id is not found in the database.
	 * This enables a useful default error json with the correct status code
	 * @param userId
	 */
	private void validateUser(Long userId) {
		this.userRepo.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}
	
	/**
	 * Throws an expection if the id is not found in the database.
	 * This enables a useful default error json with the correct status code
	 * @param wishlistId
	 */
	private void validateWishlist(Long wishlistId) {
		this.wishRepo.findById(wishlistId)
		.orElseThrow(() -> new WishlistNotFoundException(wishlistId));
	}
	
	/**
	 * Throws an expection if the id is not found in the database.
	 * This enables a useful default error json with the correct status code
	 * @param productId
	 */
	private void validateProduct(Long productId) {
		this.productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}
	
	/**
	 * Checks if the owner for a given wishlist matches the given user
	 * @param userId - the owner of the wishlist
	 * @param wishlistId - the wishlist to be checked for its owner
	 */
	private void validateMatchingUserAndWishlist(Long userId, Long wishlistId) {
		this.userRepo.findById(userId).map(user -> {
			this.wishRepo.findById(wishlistId).map(wishlist -> {
				if (!wishlist.getOwner().getId().equals(userId)) {
					throw new OwnerMismatchException(wishlistId);
				}
				return Optional.empty();
			})
			.orElseThrow(() -> new WishlistNotFoundException(wishlistId));
			return Optional.empty();
		}).orElseThrow(() -> new UserNotFoundException(userId));
	}
}
