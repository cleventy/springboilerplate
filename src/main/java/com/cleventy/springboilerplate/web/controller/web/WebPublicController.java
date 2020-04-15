package com.cleventy.springboilerplate.web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cleventy.springboilerplate.commons.properties.CommonProperties;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebPublicController {
	
	@Autowired
    private CommonProperties commonProperties;

	@GetMapping(WebURL.VERSION)
	public String version(Model model) {
		log.debug("web version");
		model.addAttribute("version", this.commonProperties.getVersion());
		WebControllerUtils.activeView(model, WebControllerUtils.CSS_CLASS_NAME_VERSION);
		return "pages/version";
	}

}
