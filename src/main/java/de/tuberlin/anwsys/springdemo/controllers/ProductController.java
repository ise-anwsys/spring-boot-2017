package de.tuberlin.anwsys.springdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.anwsys.springdemo.models.Product;
import de.tuberlin.anwsys.springdemo.repos.ProductRepository;

/**
 * A typical rest controller
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	// springs injection annotation
	@Autowired
	private ProductRepository repository;
	
	//@PostMapping
	@RequestMapping(method=RequestMethod.POST)
	public Product createProduct(@RequestBody Product input) {
		Product p = new Product(input.getName(), input.getPrice());
		return repository.save(p);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Product> getAllProducts() {
		return repository.findAll();
	}
	
	//Path gets build with the initial RestController value and any value in the Mapping annotations
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public Product getSingleProduct(@PathVariable Long id) {
		return repository.findOne(id);
	}
	
	//PutMapping is an alias for the following:
	//@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody Product input) {
		Product p = repository.findOne(id);
		p.setName(input.getName());
		p.setPrice(input.getPrice());
		return repository.save(p);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		repository.delete(id);
		return ResponseEntity.ok().build();
	}
}
