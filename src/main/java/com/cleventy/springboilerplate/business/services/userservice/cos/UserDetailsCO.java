package com.cleventy.springboilerplate.business.services.userservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDetailsCO implements Serializable {
	
    private static final long serialVersionUID = 5252965325538805827L;
    
    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private Integer state;

}
