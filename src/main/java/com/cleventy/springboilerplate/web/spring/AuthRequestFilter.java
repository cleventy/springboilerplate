package com.cleventy.springboilerplate.web.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.util.jwt.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthRequestFilter extends OncePerRequestFilter {
	
	public static final String AUTH_HEADER_NAME = "Authorization";
	public static final String AUTH_HEADER_BEARER_PREFIX_VALUE = "Bearer";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader(AUTH_HEADER_NAME);
		if (requestTokenHeader != null && requestTokenHeader.startsWith(AUTH_HEADER_BEARER_PREFIX_VALUE)) {
			// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
			final String jwtToken = requestTokenHeader.substring(AUTH_HEADER_BEARER_PREFIX_VALUE.length());
			if (this.jwtTokenUtil.isValidAccessToken(jwtToken)) {
				UserDetails userDetails = null;
				try {
					userDetails = this.jwtTokenUtil.getUserDetailsFromToken(jwtToken);
				} catch (InstanceNotFoundException e) {
					log.debug("userDetails not found in JWT Token due to instance not found " + jwtToken);
				}
				if (userDetails == null) {
					log.debug("userDetails not found in JWT Token " + jwtToken);
				} else {
					if (SecurityContextHolder.getContext().getAuthentication() != null) {
						log.debug("authentication already exists " + jwtToken);
					} else {
						// if token is valid configure Spring Security to manually set
						// authentication
						if (!this.jwtTokenUtil.validateToken(jwtToken, userDetails)) {
							log.debug("invalid JWT Token " + jwtToken + " and userDetails " + userDetails.getUsername());
						} else {
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
							usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							// After setting the Authentication in the context, we specify
							// that the current user is authenticated. So it passes the
							// Spring Security Configurations successfully.
							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
							// logger.debug("user " + userDetails.getUsername() + " authenticated by token " + jwtToken);
						}
					}
				}
			}
		}
		// Once we get the token validate it.
		chain.doFilter(request, response);
	}
}
