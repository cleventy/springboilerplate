package com.cleventy.springboilerplate.util.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TokenCO implements Serializable {

	private static final long serialVersionUID = -5198455531003510795L;
	
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private String scope;
	private String token_type;
	
}
