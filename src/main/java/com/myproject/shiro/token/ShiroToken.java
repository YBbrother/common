package com.myproject.shiro.token;

import java.io.Serializable;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Shiro token
 */
public class ShiroToken extends UsernamePasswordToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ShiroToken(String username, String password) {
		super(username, password);
		this.pwd = password;
	}
	
	private String pwd;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
