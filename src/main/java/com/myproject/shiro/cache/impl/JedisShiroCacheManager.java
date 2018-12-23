package com.myproject.shiro.cache.impl;

import org.apache.shiro.cache.Cache;

import com.myproject.shiro.cache.JedisManager;
import com.myproject.shiro.cache.ShiroCacheManager;

public class JedisShiroCacheManager implements ShiroCacheManager {
	
	private JedisManager jedisManager;
	
	public JedisManager getJedisManager() {
		return jedisManager;
	}
	
	public void setJedisManager(JedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) {
		return null;
	}

	@Override
	public void destory() {
		//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
	}

}
