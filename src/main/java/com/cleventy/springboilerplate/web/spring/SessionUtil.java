package com.cleventy.springboilerplate.web.spring;

import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {
	
	private static UserSpring getLoggedUser() {
		if (SecurityContextHolder.getContext()==null || SecurityContextHolder.getContext().getAuthentication()==null || SecurityContextHolder.getContext().getAuthentication().getPrincipal()==null || !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSpring)) {
			return null;
		}
		return (UserSpring) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	public static boolean isUserAuthenticathed() {
		return getLoggedUser()!=null;
	}
	public static boolean isAdmin() {
		return isUserAuthenticathed() && UserDetailsService.isAdmin(getLoggedUser().getAuthorities());
	}
	public static Long getLoggedUserId() {
		return getLoggedUser().getId();
	}
	
}
