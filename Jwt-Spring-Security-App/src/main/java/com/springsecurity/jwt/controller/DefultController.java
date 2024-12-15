package com.springsecurity.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.jwt.dao.AuthRequest;
import com.springsecurity.jwt.service.CustomUserDetailsService;
import com.springsecurity.jwt.util.JwtUtil;

@RestController
public class DefultController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/")
	public String defaultPage() {
		return "Welcome to Default page";
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		System.out.println("Postmapping");
		System.out.println("Username: " + authRequest.getUsername());
		System.out.println("Password: " + authRequest.getPassword());

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtUtil.generateToken(authRequest.getUsername());

	}

	@GetMapping("/authenticate")
	public String generateToken() throws Exception {
		System.out.println("Getmapping");

		return "dummyToken";

	}

	@GetMapping("/validate-token")
	public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			String username = jwtUtil.extractUsername(token);

			// Fetch user details from the database based on username
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			// Validate the token
			if (jwtUtil.validateToken(token, userDetails)) {
				// If token is valid, set authentication in SecurityContextHolder
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("Token is valid. Access granted!");
				return ResponseEntity.ok("Token is valid. Access granted!");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid.");
		}
	}

}
