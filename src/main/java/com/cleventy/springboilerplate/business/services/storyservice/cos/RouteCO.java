package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class RouteCO implements Serializable {
	
	private static final long serialVersionUID = 5646853704792439885L;
	
	protected Long id;
	protected LocationCO location;
	protected Boolean isProtected;
    
}
