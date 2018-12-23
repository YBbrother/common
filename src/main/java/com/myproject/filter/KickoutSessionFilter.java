package com.myproject.filter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.myproject.shiro.cache.VCache;
import com.myproject.shiro.session.ShiroSessionRepository;
import com.myproject.shiro.token.TokenManager;
import com.myproject.utils.LoggerUtils;
import com.myproject.utils.WriterUtil;

import net.sf.json.JSONObject;

/**
  * 相同帐号登录控制
 */
public class KickoutSessionFilter extends AccessControlFilter {
	// 静态注入
	static String kickoutUrl;
	// 在线用户
	final static String ONLINE_USER = KickoutSessionFilter.class.getCanonicalName() + "_online_user";
	// 踢出状态
	final static String KICKOUT_STATUS = KickoutSessionFilter.class.getCanonicalName() + "_kickout_status";
	
	// session获取
	static ShiroSessionRepository shiroSessionRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getRequestURI();
		Subject subject = getSubject(request, response);
		// TODO 如果是相关目录 or 如果没有登录 就直接return true  这个注释没看懂
		if (url.startsWith("/open/") || (!subject.isAuthenticated() && !subject.isRemembered())) {
			return Boolean.TRUE;
		}
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		
		/**
		 * 判断是否已经踢出
		 * 1.如果是ajax访问，则给予json返回值提示
		 * 2.如果是普通请求，直接跳到登录页
		 */
		Boolean marker = (Boolean) session.getAttribute(KICKOUT_STATUS);
		if (marker != null && marker) {
			JSONObject json = new JSONObject();
			// 判断是不是Ajax请求
			if (ShiroFilterUtils.isAjax(request)) {
				LoggerUtils.debug(getClass(), "当前用户已经在其他地方登录，并且是Ajax请求！");
				json.put("user_status", "300");
				json.put("message", "您已在其他地方登录，请重新登录！");
				WriterUtil.write(response, json.toString());
			}
			return Boolean.FALSE;
		}
		
		// 从缓存获取用户-Session信息<UserId, SessionId>
		LinkedHashMap<Integer, Serializable> infoMap = VCache.get(ONLINE_USER, LinkedHashMap.class);
		// 如果不存在， 创建一个新的       先执行三目运算，后赋值
		infoMap = infoMap == null ? new LinkedHashMap<>() : infoMap;
		
		// 获取tokenId
		Integer userId = TokenManager.getUserId();
		// 如果已经包含当前Session，并且是同一用户，跳过
		if (infoMap.containsKey(userId) && infoMap.containsValue(sessionId)) {
			// 更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			VCache.setex(ONLINE_USER, infoMap, 3600);
			return Boolean.TRUE;
		}
		
		// 如果用户相同，Session不相同，需要处理
		/**
		 * 如果用户Id相同，Session不相同
		 * 1.获取到原来的session，并且标记为踢出
		 * 2.继续走
		 */
		
		if (infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
			Serializable oldSessionId = infoMap.get(userId);
			Session oldSession = shiroSessionRepository.getSession(oldSessionId);
			if (oldSession != null) {
				// 标记session已经踢出
				oldSession.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
				shiroSessionRepository.saveSession(oldSession);
				LoggerUtils.fmtDebug(getClass(), "kickout old session success,oldId[%s]", oldSessionId);
			} else {
				shiroSessionRepository.deleteSession(sessionId);
				infoMap.remove(userId);
				VCache.setex(ONLINE_USER, infoMap, 3600);
			}
			return Boolean.TRUE;
		}
		
		if (!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
			infoMap.put(userId, sessionId);
			// 存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session有效期）
			VCache.setex(ONLINE_USER, infoMap, 3600);
		}
		return Boolean.TRUE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 先退出
		Subject subject = getSubject(request, response);
		subject.logout();
		WebUtils.getSavedRequest(request);
		// 再重定向
		WebUtils.issueRedirect(request, response, kickoutUrl);
		return false;
	}
	

	public static void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		KickoutSessionFilter.shiroSessionRepository = shiroSessionRepository;
	}

	public static String getKickoutUrl() {
		return kickoutUrl;
	}

	public static void setKickoutUrl(String kickoutUrl) {
		KickoutSessionFilter.kickoutUrl = kickoutUrl;
	}

}
