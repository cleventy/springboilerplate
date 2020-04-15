package com.cleventy.springboilerplate.business.services.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TestCO implements Serializable {

	private static final long serialVersionUID = 9135960993167948332L;
	
	private String test;

}
