package com.myproject.utils;

/**
 * 
 * 自定义异常
 * @author lzq
 *
 */
public class MyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private boolean propertiesKey = true;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isPropertiesKey() {
		return propertiesKey;
	}

	public void setPropertiesKey(boolean propertiesKey) {
		this.propertiesKey = propertiesKey;
	}

	/**
	 * 构造一个基本异常
	 * @param message
	 * 			信息描述
	 */
	public MyException(String message) {
		super(message);
	}
	
	public MyException(String errorCode, String message) {
		this(errorCode, message, true);
	}
	
	public MyException(String errorCode, String message, boolean propertiesKey) {
		super(message);
		setErrorCode(errorCode);
		setPropertiesKey(propertiesKey);
	}
}
