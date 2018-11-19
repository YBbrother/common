package com.myproject.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		logger.info("##############--菜单--#############");
		mav.setViewName("menuIndex");
		return mav;
	}
}
