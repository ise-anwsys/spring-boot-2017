package de.tuberlin.anwsys.springdemo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.anwsys.springdemo.models.User;
import de.tuberlin.anwsys.springdemo.repos.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@RequestMapping(method=RequestMethod.POST)
	public User createUser(@RequestBody User input) {
		return repository.save(new User(input.getName()));
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<User> getAllUsers() {
		return repository.findAll();
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public User getSingleUser(@PathVariable(name="id", required=true)Long id) {
		return repository.findOne(id);
	}
}
