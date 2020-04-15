package com.cleventy.springboilerplate.commons.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("cmmn")
@PropertySource(ignoreResourceNotFound=true,value="classpath:common.properties")
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@Getter @Setter
public class CommonProperties {

	private String version;
	
}
