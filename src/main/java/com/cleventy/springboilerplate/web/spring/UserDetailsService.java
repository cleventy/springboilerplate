package com.cleventy.springboilerplate.web.spring;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleventy.springboilerplate.business.entities.user.User;
import com.cleventy.springboilerplate.business.entities.user.UserConstants;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.userservice.UserService;
import com.cleventy.springboilerplate.util.jwt.JwtTokenUtil;
import com.cleventy.springboilerplate.util.jwt.TokenCO;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    public static final String AUTHORITY_ADMIN = "ADMIN_A";
    public static final String AUTHORITY_USER = "USER_A";
    public static final String AUTHORITY_SHOP = "SHOP_A";


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = this.userService.findUserAndCheckNullAndState(username);
			return getUserDetailsByUser(user);
		} catch (InstanceNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	public static UserDetails getUserDetailsByUser(User user) {
		return new UserSpring(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(getUserAuthoritiesArray(user.getRole())), user.getId());
	}
	public UserDetails getUserDetails(String username) throws InstanceNotFoundException {
		User user = this.userService.findUserAndCheckNullAndState(username);
		return getUserDetailsByUser(user);
	}
	
	
	private static String[] getUserAuthoritiesArray(String rol) {
		Collection<String> authoritiesList = getUserAuthoritiesCollection(rol);
		String[] authoritiesArr = new String[authoritiesList.size()];
		return authoritiesList.toArray(authoritiesArr);
	}
	private static Set<String> getUserAuthoritiesCollection(String rol) {
		Set<String> authoritiesList = new HashSet<String>();
		if (UserConstants.ROLE_USER.equals(rol)) {
			authoritiesList.add(UserDetailsService.AUTHORITY_USER);
		}
		if (UserConstants.ROLE_ADMIN.equals(rol)) {
			authoritiesList.add(UserDetailsService.AUTHORITY_ADMIN);
			authoritiesList.add(UserDetailsService.AUTHORITY_USER);
			authoritiesList.add(UserDetailsService.AUTHORITY_SHOP);
		}
		if (UserConstants.ROLE_SHOP.equals(rol)) {
			authoritiesList.add(UserDetailsService.AUTHORITY_SHOP);
		}
		return authoritiesList;
	}
	private static GrantedAuthority getAuthority(String authority) {
		return new SimpleGrantedAuthority(authority);
	}
	public static String getScopeFromRole(String role) {
		return getUserAuthoritiesCollection(role).stream().map(authority -> authority).collect(Collectors.joining( " " ));
	}
	public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
		return authorities.contains(UserDetailsService.getAuthority(UserDetailsService.AUTHORITY_ADMIN));
	}
//	public static String getScope(Collection<? extends GrantedAuthority> authorities) {
//		return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.joining( " " ));
//	}
//	public static String getScope(Collection<String> authorities) {
//		return authorities.stream().map(authority -> authority).collect(Collectors.joining( " " ));
//	}


	@Transactional(readOnly=true)
	public TokenCO checkCredentialsAndGetTokens(String username, String password) {
		try {
			User user = this.userService.findUserAndCheckNullAndState(username);
			UserDetails userDetails = getUserDetailsByUser(user);
			if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
				throw new BadCredentialsException("invalid password");
			}
			return this.jwtTokenUtil.generateTokens(username, user.getPassword(), user.getRole(), getScopeFromRole(user.getRole()));
		} catch (InstanceNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	
	@Transactional(readOnly=true)
	public TokenCO checkCredentialsAndGetTokens(String refreshToken) {
		try {
			if (!this.jwtTokenUtil.isValidRefreshToken(refreshToken)) {
				throw new BadCredentialsException("invalid refresh token");
			}
			String username = this.jwtTokenUtil.getUsernameFromToken(refreshToken);
			User user = this.userService.findUserAndCheckNullAndState(username);
			UserDetails userDetails = getUserDetailsByUser(user);
			return this.jwtTokenUtil.generateTokens(username, userDetails.getPassword(), user.getRole(), getScopeFromRole(user.getRole()));
		} catch (InstanceNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

}
