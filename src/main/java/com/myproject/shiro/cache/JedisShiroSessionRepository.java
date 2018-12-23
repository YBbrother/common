package com.myproject.shiro.cache;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

import com.myproject.shiro.session.CustomSessionManager;
import com.myproject.shiro.session.SessionStatus;
import com.myproject.shiro.session.ShiroSessionRepository;
import com.myproject.utils.LoggerUtils;
import com.myproject.utils.SerializeUtil;

public class JedisShiroSessionRepository implements ShiroSessionRepository{
	
	public static final String REDIS_SHIRO_SESSION = "shiro-demo-session";
	//这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
	public static final String REDIS_SHIRO_ALL = "shiro-demo-session:*";
	private static final int DB_INDEX = 1;
	
	private JedisManager jedisManager;
	
	public JedisManager getJedisManager() {
		return jedisManager;
	}
	public void setJedisManager(JedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() ==null) {
			throw new NullPointerException("session is empty");
		}
		
		byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));
		
		// 不存在时添加
		if (session.getAttribute(CustomSessionManager.SESSION_STATUS) == null) {
			// Session 踢出自存存储
			SessionStatus sessionStatus = new SessionStatus();
			session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
		}
		
		byte[] value = SerializeUtil.serialize(session);
		getJedisManager().saveValueByKey(DB_INDEX, key, value, (int) session.getTimeout());
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		if (sessionId == null) {
			throw new NullPointerException("session id is null");
		}
		try {
			getJedisManager().deleteByKey(DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(sessionId)));
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", sessionId);
		}
		
	}

	@Override
	public Session getSession(Serializable sessionId) {
		if (sessionId == null)
       	 throw new NullPointerException("session id is empty");
       Session session = null;
       try {
           byte[] value = getJedisManager().getValueByKey(DB_INDEX, SerializeUtil
                   .serialize(buildRedisSessionKey(sessionId)));
           session = SerializeUtil.deserialize(value, Session.class);
       } catch (Exception e) {
       	LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", sessionId);
       }
       return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		Collection<Session> sessions = null;
		try {
			sessions = getJedisManager().AllSession(DB_INDEX,REDIS_SHIRO_SESSION);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
		}
       
        return sessions;
	}
	
	private String buildRedisSessionKey(Serializable sessionId) {
		return REDIS_SHIRO_SESSION + sessionId;
	}
	

}
