package com.myproject.shiro.session;

public class SessionStatus {

	// 是否踢出 true：是， false：否
	private Boolean onlineStatus = Boolean.TRUE;

	public Boolean getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	
	public Boolean isOnlineStatus() {
		return onlineStatus;
	}
	
}
