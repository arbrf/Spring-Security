package com.in28minutes.learn.spring.security.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldResources {
	@GetMapping("/hello")
public String display() {
	return "Hello world  Hii,...";
}
}
