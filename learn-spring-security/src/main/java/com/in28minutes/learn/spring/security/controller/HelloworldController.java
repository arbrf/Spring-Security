package com.in28minutes.learn.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {
	@GetMapping("/hello")
public String display() {
	return "Hello world";
}
}
