package com.myproject.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import com.myproject.common.constant.Constant;
import com.myproject.utils.LoggerUtils;
import com.myproject.utils.WriterUtil;
import net.sf.json.JSONObject;

/**
 * 权限校验 Filter
 */
public class PermissionFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 先判断带参数的权限判断
		Subject subject = getSubject(request, response);
		if (mappedValue != null) {
			String[] arr = (String[]) mappedValue;
			for (String permission : arr) {
				if (subject.isPermitted(permission)) {
					return Boolean.TRUE;
				}
				
			}
		}
		
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		/**
		 * 此处是改版后，为了兼容项目不需要部署到root下，也可以正常运行，但是权限没设置目前必须到root 的URI，
		 * 原因：如果你把这个项目叫 ShiroDemo，那么路径就是 /ShiroDemo/xxxx.shtml ，那另外一个人使用，又叫Shiro_Demo,那么就要这么控制/Shiro_Demo/xxxx.shtml 
		 * 理解了吗？
		 * 所以这里替换了一下，使用根目录开始的URI
		 */
		String url = httpRequest.getRequestURL().toString();
		String basePath = httpRequest.getContextPath();
		if (url != null && url.startsWith(basePath)) {
			url = url.replaceFirst(basePath, "");
		}
		
		if (subject.isPermitted(url)) {
			return Boolean.TRUE;
		}
		if(ShiroFilterUtils.isAjax(request)){
			JSONObject json = new JSONObject();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			json.put("login_status", "300");
			json.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
			WriterUtil.write(response, json.toString());
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {  //表示没有登录，重定向到登录页面  
			saveRequest(request);
			WebUtils.issueRedirect(request, response, Constant.LOGIN_URL);
		} else {
			if (StringUtils.hasText(Constant.UNAUTHORIZED_URL)) {  //如果有未授权页面跳转过去
				WebUtils.issueRedirect(request, response, Constant.UNAUTHORIZED_URL);
			} else {  //否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return Boolean.FALSE;
	}
	
}
