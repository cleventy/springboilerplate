package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cleventy.springboilerplate.web.spring.UserDetailsService;

public class WebUtils {
	
	public static boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication() != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
	public static boolean isAdmin() {
		// SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().forEach(authority->log.debug(authority.getAuthority()));
		return isAuthenticated() && UserDetailsService.isAdmin(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
	}

}
