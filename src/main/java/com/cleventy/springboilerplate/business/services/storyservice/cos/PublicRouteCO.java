package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=true) @Data @SuperBuilder
public class PublicRouteCO extends RouteCO implements Serializable {
	
	private static final long serialVersionUID = -514398677753785227L;

}
