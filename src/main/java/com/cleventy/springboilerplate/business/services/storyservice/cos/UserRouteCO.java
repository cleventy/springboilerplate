package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;
import java.util.Set;

import com.cleventy.springboilerplate.business.services.routeservice.cos.GameCO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=true) @Data @SuperBuilder
public class UserRouteCO extends RouteCO implements Serializable {
	
	private static final long serialVersionUID = -3923018345440688762L;
	
	private Set<GameCO> games;
    
}
