package com.autotest.utils.factory;

import org.openqa.selenium.WebDriver;

public interface Browser {

	/**
	 * 获取不同浏览器的实例
	 * @return
	 */
	public WebDriver getDriver();

}