package com.autotest.learn;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty9.server.HttpChannelState.Action;

import com.autotest.utils.factory.Browser;
import com.autotest.utils.factory.BrowserFactory;

public class TestOne {
	public static void main(String[] args) throws InterruptedException {
		
		Browser browser = BrowserFactory.getChrome();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.baidu.com");
		
		
		driver.findElement(By.xpath(".//*[@id='kw']")).sendKeys("Java");
		driver.findElement(By.xpath("//*[@id='kw']")).sendKeys(Keys.F12);
	}
}
