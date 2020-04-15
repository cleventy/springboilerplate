package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.ui.Model;

public class WebControllerUtils {
	
	private static final String CSS_CLASS_ACTIVE = "active";
	public static final String CSS_CLASS_NAME_HOME = "classActiveHome";
	public static final String CSS_CLASS_NAME_VERSION = "classActiveVersion";
	public static final String CSS_CLASS_NAME_ADMIN_USERS = "classActiveAdminUsers";
	public static final String CSS_CLASS_NAME_LOGIN = "classActiveLogin";
	
	public static void activeView(Model model, String menuName) {
		model.addAttribute(menuName, CSS_CLASS_ACTIVE);
	}
	
}
