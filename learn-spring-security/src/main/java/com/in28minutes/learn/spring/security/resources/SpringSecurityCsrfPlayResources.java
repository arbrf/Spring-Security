package com.in28minutes.learn.spring.security.resources;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SpringSecurityCsrfPlayResources {
	
	@GetMapping("/csrf")
public CsrfToken retrieveCsrfToken(HttpServletRequest request) {
	return (CsrfToken) request.getAttribute("_csrf");
}
}
