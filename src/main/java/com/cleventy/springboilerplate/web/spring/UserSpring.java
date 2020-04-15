package com.cleventy.springboilerplate.web.spring;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSpring extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 6816604814556898835L;
	
	private Long id;

	public UserSpring(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public UserSpring(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
		super(username, password, authorities);
		this.id = id;
	}
	
	
}
