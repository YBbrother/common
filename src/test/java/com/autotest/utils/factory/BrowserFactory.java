package com.autotest.utils.factory;

public class BrowserFactory {
	
	public static Browser getChrome() {
		return new ChromeBrowser();
	}
	
	public static Browser getFirefox() {
		return new FirefoxBrowser();
	}
	
}
