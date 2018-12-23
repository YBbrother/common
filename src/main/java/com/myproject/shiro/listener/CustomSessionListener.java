package com.myproject.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.myproject.shiro.session.ShiroSessionRepository;

public class CustomSessionListener implements SessionListener {
	
	private ShiroSessionRepository shiroSessionRepository;
	
	/**
	 * 一个会话的生命周期开始
	 */
	@Override
	public void onExpiration(Session arg0) {
		System.out.println("On Start");
	}
	
	/**
	 * 一个会话的生命周期结束
	 */
	@Override
	public void onStart(Session arg0) {
		System.out.println("On Stop");
	}

	/**
	 * 清除session
	 */
	@Override
	public void onStop(Session session) {
		shiroSessionRepository.deleteSession(session.getId());
	}
	
	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}
	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
	
}
