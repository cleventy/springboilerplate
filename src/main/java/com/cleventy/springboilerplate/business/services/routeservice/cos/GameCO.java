package com.cleventy.springboilerplate.business.services.routeservice.cos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GameCO implements Serializable {
	
	private static final long serialVersionUID = 3529619599494816578L;
	
	private Long id;
	private Long userId;
	private Long eventId;
}
