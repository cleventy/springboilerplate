package com.cleventy.springboilerplate.web.controller.api.form.route;

import java.io.Serializable;

import com.cleventy.springboilerplate.web.controller.api.form.GenericForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteStartGameDetailsForm implements Serializable, GenericForm {

	private static final long serialVersionUID = -5757176881103263892L;
	
	private String password;
}
