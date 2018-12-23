package com.myproject.shiro.token;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.system.model.UserModel;
import com.myproject.system.service.menuservice.MenuService;
import com.myproject.system.service.userservice.UserService;

/**
 * shiro 认证 + 授权   重写
 */
public class SampleRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	
	public SampleRealm() {
		super();
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Integer userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据用ID查询权限，放入到Authorization里
		Set<String> perms = menuService.selectPermsByUserId(userId);
		info.setStringPermissions(perms);
		return info;
	}

	/**
	 *  认证信息，主要针对用户登录， 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		ShiroToken shiroToken = (ShiroToken) token;
		UserModel userModel = userService.login(shiroToken.getUsername(), shiroToken.getPwd());
		if (userModel == null) {
			throw new AccountException("帐号或密码不正确！");
		} else if (UserModel._0.equals(userModel.getStatus())) { // 如果用户的status为禁用。那么就抛出DisabledAccountException
			throw new DisabledAccountException("账号禁止登陆");
		} else {
			userService.updateUserLoginTime();
		}
		return new SimpleAuthenticationInfo(userModel, userModel.getPassword(), getName());
	}
	
	/**
	 * 清空当前用户的权限信息
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection pc = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(pc, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
	 * 指定principalCollection清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection pc) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				pc, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
}
