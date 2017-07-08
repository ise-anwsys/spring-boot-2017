package de.tuberlin.anwsys.springdemo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import de.tuberlin.anwsys.springdemo.models.Product;
import de.tuberlin.anwsys.springdemo.models.User;
import de.tuberlin.anwsys.springdemo.repos.ProductRepository;
import de.tuberlin.anwsys.springdemo.repos.UserRepository;


@SpringBootApplication
@ComponentScan("de.tuberlin.anwsys.springdemo") // scan in this package and subpackages for components
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}
	
	/**
	 * This gets run on startup,
	 * you can add anything that is possible to be AutoWired as a parameter
	 * @param userRepo 
	 * @param productRepo
	 * @return CommandLineRunner; marks that the Bean should be run on startup
	 */
	@Bean
	CommandLineRunner init(UserRepository userRepo, ProductRepository productRepo) {
		return (evt) -> {
			// add users to DB
			Arrays.asList("demo,test,bestvalue".split(",")).forEach(name -> {
				userRepo.save(new User(name));
			});
			// add products to DB
			Arrays.asList("towel:42,handbag:999".split(",")).forEach(dataEntry -> {
				String[] data = dataEntry.split(":");
				double price = Double.parseDouble(data[1]);
				productRepo.save(new Product(data[0], price));
			});
		};
	}

}
