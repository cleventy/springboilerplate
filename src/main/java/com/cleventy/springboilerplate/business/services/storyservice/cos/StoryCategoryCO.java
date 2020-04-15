package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StoryCategoryCO implements Serializable {

	private static final long serialVersionUID = 6894456808661454977L;
	
	private Long id;
    private String name;
    
}
