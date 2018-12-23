package com.myproject.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.shiro.token.TokenManager;
import com.myproject.system.model.UserModel;

@Controller
@RequestMapping("login")
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginIndex");
		return mav;
	}
	
	@RequestMapping("submitLogin")
	@ResponseBody
	public Map<String, Object> susubmitLoginb(HttpServletRequest request, UserModel userModel, Boolean isRemember) {
		try {
			userModel = TokenManager.login(userModel, isRemember);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
