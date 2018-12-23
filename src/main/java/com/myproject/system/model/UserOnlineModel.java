package com.myproject.system.model;

import java.util.Date;

public class UserOnlineModel extends UserModel {

	private static final long serialVersionUID = 1L;
	
	public UserOnlineModel() {
		
	}
	
	public UserOnlineModel(UserModel userModel) {
		super(userModel);
	}
	
	private String sessionId;	// Session Id
	private String host;	// Session Host
	private Date startTime;	   // Session创建时间
	private Date lastAccess;	// Session最后交互时间
	private long timeout;	// Session最后交互时间
	private boolean sessionStatus = Boolean.TRUE;	// session 是否踢出
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public boolean isSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(boolean sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	
	@Override
	public String toString() {
		return "UserOnlineModel [sessionId=" + sessionId + ", host=" + host + ", startTime=" + startTime
				+ ", lastAccess=" + lastAccess + ", timeout=" + timeout + ", sessionStatus=" + sessionStatus + "]";
	}

}
