package com.myproject.shiro.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.myproject.shiro.CustomShiroSessionDAO;
import com.myproject.system.model.UserModel;
import com.myproject.system.model.UserOnlineModel;
import com.myproject.utils.DealUtil;
import com.myproject.utils.LoggerUtils;

public class CustomSessionManager {
	
	public static final String SESSION_STATUS = "session-online-status";
	
	ShiroSessionRepository shiroSessionRepository;
	
	CustomShiroSessionDAO customShiroSessionDAO;
	
	public List<UserOnlineModel> getAllUser() {
		// 获取所有session
		Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
		List<UserOnlineModel> list = new ArrayList<>();
		if (sessions != null && sessions.size() > 0) {
			for (Session session : sessions) {
				UserOnlineModel uom = getSessionParam(session);
				if (uom != null) {
					list.add(uom);
				}
			}
		}
		return list;
	}		
	
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Integer...userIds) {
		// 把userIds转出Set好判断
		@SuppressWarnings("unchecked")
		Set<Integer> userIdSet = (Set<Integer>) DealUtil.array2Set(userIds);
		// 获取所有session
		Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
		// 定义返回
		List<SimplePrincipalCollection> list = new ArrayList<>();
		for (Session session : sessions) {
			// 获取SimplePrincipalCollection
			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			// TODO 这里的实例判断有必要吗
			if (obj != null && obj instanceof SimplePrincipalCollection) {
				SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
				obj = spc.getPrimaryPrincipal();
				if (obj != null && obj instanceof UserModel) {
					UserModel userModel = (UserModel) obj;
					// 比较用户ID， 符合即加入集合
					if (userModel != null && userIdSet.contains(userModel.getUserId())) {
						list.add(spc);
					}
				}
			}
		}
		return list;
	}
	
	public UserOnlineModel getSession(String sessionId) {
		Session session = shiroSessionRepository.getSession(sessionId);
		UserOnlineModel uom = getSessionParam(session);
		return uom;
	}
	
	private UserOnlineModel getSessionParam(Session session) {
		// 获取session登录信息
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if (obj == null) {
			return null;
		}
		// 确保是SimplePrincipalCollection对象
		if (obj instanceof SimplePrincipalCollection) {
			SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
			obj = spc.getPrimaryPrincipal();
			if (obj != null && obj instanceof UserModel) {
				// 存储session + user 综合信息
				UserOnlineModel uom = new UserOnlineModel();
				// 最后一次和系统交互的时间
				uom.setLastAccess(session.getLastAccessTime());
				// 主机的ip地址
				uom.setHost(session.getHost());
				// session ID
				uom.setSessionId(session.getId().toString());
				// session最后一次与系统交互的时间
				uom.setLastLoginTime(session.getLastAccessTime().toString());
				// 会话到期时间ttl(ms)
				uom.setTimeout(session.getTimeout());
				// session创建时间
				uom.setStartTime(session.getStartTimestamp());
				// 是否踢出
				SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
				boolean status = Boolean.TRUE;
				if (sessionStatus != null) {
					status = sessionStatus.getOnlineStatus();
				}
				uom.setSessionStatus(status);
				return uom;
			}
		}
		return null;
	}
	
	/**
	 * 改变Session状态
	 * @param status {true:踢出,false:激活}
	 * @param sessionId
	 * @return
	 */
	public Map<String, Object> changeSessionStatus(boolean status, String sessionIds) {
		Map<String, Object> map = new HashMap<>();
		String[] sessionIdArr = null;
		try {
			if (sessionIds.indexOf(",") == -1) {
				sessionIdArr = new String[] {sessionIds};
			} else {
				sessionIdArr = sessionIds.split(",");
			}
			
			for (String sessionId : sessionIdArr) {
				Session session = shiroSessionRepository.getSession(sessionId);
				SessionStatus sessionStatus = new SessionStatus();
				sessionStatus.setOnlineStatus(status);
				session.setAttribute(SESSION_STATUS, sessionStatus);
				customShiroSessionDAO.update(session);
			}
			
			map.put("status", 200);
			map.put("sessionStatus", status? 1 : 0);
			map.put("sessionStatusText", status? "踢出" : "激活");
			map.put("sessionStatusTextId", status? "有效" : "已踢出");
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "改变Session状态错误，sessionId[%s]", sessionIds);
			map.put("status", 500);
			map.put("message", "改变失败，有可能Session不存在，请刷新再试！");
		}
		return map;
	}
	
	/**
	 * 查询要禁用的用户是否在线。
	 * @param id		用户ID
	 * @param status	用户状态
	 */
	public void forbidUserById(Integer id, Long status) {
		// 获取所有在线用户
		for (UserOnlineModel uom : getAllUser()) {
			Integer userId = uom.getUserId();
			// 匹配用户ID
			if (userId.equals(id)) {
				// 获取用户session
				Session session = shiroSessionRepository.getSession(uom.getSessionId());
				// 标记用户Session
				SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
				// 是否踢出 0：是，1：否
				sessionStatus.setOnlineStatus(status.intValue() == 1);
				// 更新Session
				customShiroSessionDAO.update(session);
			}
		}
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

	public CustomShiroSessionDAO getCustomShiroSessionDAO() {
		return customShiroSessionDAO;
	}

	public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
		this.customShiroSessionDAO = customShiroSessionDAO;
	}
	
}
