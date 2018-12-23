package com.myproject.shiro.token;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.myproject.shiro.session.CustomSessionManager;
import com.myproject.system.model.UserModel;
import com.myproject.utils.SpringContextUtil;

/**
  * Shiro管理下的Token工具类
 */
public class TokenManager {
	
	//用户登录管理
	public static final SampleRealm realm = SpringContextUtil.getBean("sampleRealm",SampleRealm.class);
	//用户session管理
	public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager",CustomSessionManager.class);
	
	/**
	 * 获取当前登录的user对象
	 */
	public static UserModel getToken() {
		UserModel token = (UserModel) SecurityUtils.getSubject().getPrincipal();
		return token;
	}
	
	/**
	 * 获取当前用户的Session
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 获取当前用户昵称
	 * @return
	 */
	public static String getNickname() {
		return getToken() == null ? null : getToken().getNickname();
	}
	
	/**
	 * 获取当前用户ID
	 * @return
	 */
	public static Integer getUserId() {
		return getToken() == null ? null : getToken().getUserId();
	}
	
	/**
	 * 把值放入当前登录用户的Session里
	 * @param key
	 * @param value
	 */
	// TODO 没读懂这个注释
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	/**
	 * 从当前登录用户的Session里取值
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}
	
	/**
	 * 获取验证码并删除
	 * @return
	 */
	public static String getYZM() {
		String code = (String) getSession().getAttribute("CODE");
		getSession().removeAttribute("CODE");
		return code;
	}
	
	/**
	 * 登录
	 * @param userModel
	 * @param isRemember
	 * @return
	 */
	public static UserModel login(UserModel userModel, Boolean isRemember) {
		ShiroToken token = new ShiroToken(userModel.getUserName(), userModel.getPassword());
		token.setRememberMe(isRemember);
		SecurityUtils.getSubject().login(token);
		return getToken();
	}
	
	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}
	
	/**
	 * 退出登录
	 */
	public static void logOut() {
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * 清空当前用户权限信息
	 * 目的：为了在判断权限的时候，会再次调用 <code>doGetAuthorizationInfo(...)</code> 方法。
	 * ps: 也可以手动调用 <code>doGetAuthorizationInfo(...)</code> 方法。
	 * 即当清空了权限时， <code>doGetAuthorizationInfo(...)</code> 方法会被调用
	 */
	public static void clearNowUserAuth() {
		/**
		 * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
		 *
		 * 获取当前系统的Realm的实例化对象，
		 * 方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
		 * 获取到的时候是一个集合。Collection<Realm> 
		 * RealmSecurityManager securityManager =
		 *  			(RealmSecurityManager) SecurityUtils.getSecurityManager();
		 * 	SampleRealm realm = (SampleRealm)securityManager.getRealms().iterator().next();
		 *
		 * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
		 * 
		 * 还有其他方法
		 */
		
		realm.clearCachedAuthorizationInfo();
	}
	
	/**
	 * 清空多个用户的权限信息
	 * @param userIds
	 */
	public static void clearUserAuthByUserId(Integer...userIds) {
		if (userIds == null || userIds.length == 0) {
			return;
		}
		
		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
		if (result != null && result.size() > 0) {
			for (SimplePrincipalCollection spc : result) {
				realm.clearCachedAuthorizationInfo(spc);
			}
		}
	}
	
	public static void clearUserAuthByUserId(List<Integer> userIds) {
		if (userIds == null || userIds.size() == 0) {
			return;
		}
		clearUserAuthByUserId(userIds.toArray(new Integer[0]));
		
	}

}
