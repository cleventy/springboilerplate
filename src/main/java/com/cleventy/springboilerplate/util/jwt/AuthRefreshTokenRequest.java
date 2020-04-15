package com.cleventy.springboilerplate.util.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthRefreshTokenRequest implements Serializable {

	private static final long serialVersionUID = 5936941129927853575L;
	
	private String refresh_token;
	
}