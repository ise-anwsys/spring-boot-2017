package de.tuberlin.anwsys.springdemo.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import de.tuberlin.anwsys.springdemo.models.Product;

/**
 * All the database methods related to Product
 * 
 * JpaRepository extends CrudRepository
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findById(Long id);
}
