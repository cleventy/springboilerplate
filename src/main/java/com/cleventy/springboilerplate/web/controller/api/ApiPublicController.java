package com.cleventy.springboilerplate.web.controller.api;

import java.util.AbstractMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleventy.springboilerplate.business.services.cos.TestCO;
import com.cleventy.springboilerplate.commons.properties.CommonProperties;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiURL.BASE)
@Slf4j
public class ApiPublicController {
	
	@Autowired
	private CommonProperties commonProperties;

	@SuppressWarnings("static-method")
	@GetMapping(path=ApiURL.TEST, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<TestCO> test() {
		log.debug("api test");
		return ResponseEntity.ok().body(new TestCO("test"));
	}
	
	@GetMapping(path=ApiURL.VERSION, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<AbstractMap.SimpleEntry<String, String>> version() {
		log.debug("api version");
		return ResponseEntity.ok().body(new AbstractMap.SimpleEntry<String, String>("version", this.commonProperties.getVersion()));
	}
	
}
