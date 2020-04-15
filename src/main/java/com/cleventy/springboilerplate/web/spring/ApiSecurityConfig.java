package com.cleventy.springboilerplate.web.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cleventy.springboilerplate.web.controller.api.ApiURL;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RestAuthEntryPoint restAuthEntryPoint;
	
	@Autowired
	private AuthRequestFilter jwtRequestFilter;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
  		.antMatcher(ApiURL.BASE+"/**")
  			.csrf().disable()
	  	.authorizeRequests()
	  		.antMatchers(HttpMethod.OPTIONS, ApiURL.BASE+"/**").permitAll()
	  		
  			.antMatchers(ApiURL.BASE+ApiURL.TEST).permitAll()
  			.antMatchers(ApiURL.BASE+ApiURL.VERSION).permitAll()
  			
  			.antMatchers(ApiURL.BASE+ApiURL.LOGIN).permitAll()
  			.antMatchers(ApiURL.BASE+ApiURL.REFRESH_TOKEN).permitAll()
  			
  			.antMatchers(ApiURL.BASE+ApiURL.STORIES).permitAll()
  			.antMatchers(ApiURL.BASE+ApiURL.STORIES+"/**").permitAll()
  			
  			.antMatchers(ApiURL.BASE+ApiURL.ADMIN+"/**").hasAuthority(UserDetailsService.AUTHORITY_ADMIN)
  			
  			.antMatchers(ApiURL.BASE+ApiURL.USER+"/**").hasAuthority(UserDetailsService.AUTHORITY_USER)
  			
  			.antMatchers(ApiURL.BASE+"/**").authenticated()
		.and()
			.exceptionHandling().authenticationEntryPoint(this.restAuthEntryPoint)
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}