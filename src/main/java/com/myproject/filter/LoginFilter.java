package com.myproject.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.myproject.shiro.token.TokenManager;
import com.myproject.system.model.UserModel;
import com.myproject.utils.LoggerUtils;
import com.myproject.utils.WriterUtil;

import net.sf.json.JSONObject;

/**
 * 判断登录
 */
public class LoginFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		UserModel token = TokenManager.getToken();
		if (token != null || isLoginRequest(request, response)) {
			return Boolean.TRUE;
		}
		
		if (ShiroFilterUtils.isAjax(request)) {
			JSONObject json = new JSONObject();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			json.put("login_status", "300");
			json.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");  //当前用户没有登录！
			WriterUtil.write(response, json.toString());
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 保存Request并重定向到登录页
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}

}
