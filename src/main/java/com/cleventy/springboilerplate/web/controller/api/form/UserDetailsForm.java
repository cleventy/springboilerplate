package com.cleventy.springboilerplate.web.controller.api.form;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDetailsForm implements Serializable {

	private static final long serialVersionUID = 1528491723909281373L;
	
    private String username;
    private String password;
    private String email;
    private String name;
    private String role;
    private Integer state;

}
