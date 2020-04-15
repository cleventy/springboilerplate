package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleventy.springboilerplate.business.services.userservice.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(WebURL.ADMIN)
@Slf4j
public class WebAdminUsersController {
	
	@Autowired
	private UserService userService;

	@GetMapping(WebURL.USERS)
	public String users(Model model) {
		log.debug("web admin users");
		model.addAttribute("users", this.userService.getUsers());
		WebControllerUtils.activeView(model, WebControllerUtils.CSS_CLASS_NAME_ADMIN_USERS);
		return "pages/admin/users";
	}

}
