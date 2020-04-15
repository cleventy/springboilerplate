package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StoryDetailsBookCO implements Serializable {

	private static final long serialVersionUID = 5593377004057070222L;
	
	private Boolean enabled;
	private String link;
    private Double price;
    private String description;
    private CurrencyCO currency;
}
