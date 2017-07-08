package de.tuberlin.anwsys.springdemo.repos;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tuberlin.anwsys.springdemo.models.User;
import de.tuberlin.anwsys.springdemo.models.Wishlist;

/**
 * All the database methods related to Wishlist
 * 
 * JpaRepository extends CrudRepository
 *
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	Collection<Wishlist> findByOwner(User owner);
	Optional<Wishlist> findById(Long id);
}
