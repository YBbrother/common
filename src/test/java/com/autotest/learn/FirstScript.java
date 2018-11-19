package com.autotest.learn;

import org.openqa.selenium.WebDriver;

import com.autotest.utils.factory.Browser;
import com.autotest.utils.factory.BrowserFactory;

public class FirstScript {
	public static void main(String[] args) {
		
		Browser browser = BrowserFactory.getChrome();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.baidu.com");
		assert "百度一下，你就知道".equals(driver.getTitle());
		System.out.println("Test Pass");
		
	}
}
