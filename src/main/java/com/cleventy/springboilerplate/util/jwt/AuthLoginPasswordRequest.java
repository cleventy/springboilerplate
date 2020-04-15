package com.cleventy.springboilerplate.util.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthLoginPasswordRequest implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	
}