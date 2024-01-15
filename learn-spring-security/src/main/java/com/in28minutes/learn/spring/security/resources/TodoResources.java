package com.in28minutes.learn.spring.security.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResources {
	
	private static final List<Todo> TODOS_LIST = List.of(new Todo("in28minutes", "Learn AWS"),
			new Todo("in28minutes", "Get AWS Certified"));
    private Logger logger=LoggerFactory.getLogger(getClass());


	@GetMapping("/todos")
public List<Todo> displayalltodos() {
	return TODOS_LIST;
}
	
	
	@GetMapping("/users/{username}/todos")
public Todo displayTodosForSpecficUser(@PathVariable String username ) {
	return TODOS_LIST.get(0);
}	

	@PostMapping("/users/{username}/todos")
public void createTodosForSpecficUser(@PathVariable String username,@RequestBody Todo todo ) {
	logger.info("creating a specific todo {} for specifi user {}",todo,username);
}	

	record Todo(String username,String desc) {}
}
