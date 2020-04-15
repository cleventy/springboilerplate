package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebController {
	
	@GetMapping(WebURL.ROOT)
	public static String root(Model model) {
		log.debug("web /");
		if (!WebUtils.isAuthenticated()) {
			return "redirect:"+WebURL.AUTH+WebURL.LOGIN;
		} else {
			if (WebUtils.isAdmin()) {
				return "redirect:"+WebURL.ADMIN+WebURL.USERS;
			} else {
				return "redirect:"+WebURL.VERSION;
			}
		}
	}

}
