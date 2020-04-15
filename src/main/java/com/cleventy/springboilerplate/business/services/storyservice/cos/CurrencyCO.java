package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CurrencyCO implements Serializable {

	private static final long serialVersionUID = -8356239803154508014L;
	
	private String code;
	private String symbol;
}
