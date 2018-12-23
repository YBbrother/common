package com.myproject.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class ShiroFilterUtils {
	static final Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class; 
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	
	
}
