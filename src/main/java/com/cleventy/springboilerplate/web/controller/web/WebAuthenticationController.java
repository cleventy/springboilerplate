package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(WebURL.AUTH)
@Slf4j
public class WebAuthenticationController {

	@GetMapping(WebURL.LOGIN)
	public static String login(Model model) {
		log.debug("web login");
		WebControllerUtils.activeView(model, WebControllerUtils.CSS_CLASS_NAME_LOGIN);
		return "pages/auth/login";
	}

}
