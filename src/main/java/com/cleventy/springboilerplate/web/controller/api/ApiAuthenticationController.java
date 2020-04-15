package com.cleventy.springboilerplate.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cleventy.springboilerplate.util.jwt.AuthLoginPasswordRequest;
import com.cleventy.springboilerplate.util.jwt.AuthRefreshTokenRequest;
import com.cleventy.springboilerplate.util.jwt.TokenCO;
import com.cleventy.springboilerplate.web.spring.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping(ApiURL.BASE)
@Slf4j
public class ApiAuthenticationController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping(value = ApiURL.LOGIN, method = RequestMethod.POST, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<TokenCO> authByUsernameAndPassword(@RequestBody AuthLoginPasswordRequest authLoginPasswordRequest) {
		log.debug("api login");
		return ResponseEntity.ok(this.userDetailsService.checkCredentialsAndGetTokens(authLoginPasswordRequest.getUsername(), authLoginPasswordRequest.getPassword()));
	}
	@RequestMapping(value = ApiURL.REFRESH_TOKEN, method = RequestMethod.POST, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<TokenCO> authByRefreshToken(@RequestBody AuthRefreshTokenRequest authenticationRequest) {
		log.debug("api refresh token");
		return ResponseEntity.ok(this.userDetailsService.checkCredentialsAndGetTokens(authenticationRequest.getRefresh_token()));
	}
}