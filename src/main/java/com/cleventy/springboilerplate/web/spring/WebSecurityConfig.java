package com.cleventy.springboilerplate.web.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.cleventy.springboilerplate.web.controller.web.WebURL;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http
	  		.authorizeRequests()
	  		.antMatchers(WebURL.ROOT).permitAll()
	  		.antMatchers(WebURL.VERSION).permitAll()
  			.antMatchers(WebURL.ADMIN+"/**").hasAnyAuthority(UserDetailsService.AUTHORITY_ADMIN)
  			.antMatchers(WebURL.USERS+"/**").hasAnyAuthority(UserDetailsService.AUTHORITY_USER)
	  		.anyRequest().authenticated()
	  	.and()
	  		.formLogin()
	  		.loginPage(WebURL.AUTH+WebURL.LOGIN)
	  		.permitAll()
	  	.and()
	  		.logout()
	  		.permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/js/**");
		web.ignoring().mvcMatchers("/css/**");
		web.ignoring().mvcMatchers("/favicon.ico");
	}

}