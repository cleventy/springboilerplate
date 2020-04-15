package com.cleventy.springboilerplate;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.cleventy.springboilerplate.util.date.DateUtil;

@SpringBootApplication
public class SpringBootTomcatApplication extends SpringBootServletInitializer {

    @PostConstruct
    public static void init(){
        TimeZone.setDefault(DateUtil.getDefaultTimeZone());
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTomcatApplication.class, args);
	}
}