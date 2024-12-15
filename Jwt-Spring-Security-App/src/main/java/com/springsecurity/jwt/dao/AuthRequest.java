package com.springsecurity.jwt.dao;

import lombok.Data;

@Data
public class AuthRequest {
	
	public AuthRequest() {}
private String username;
private String password;
}
