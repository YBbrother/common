package com.autotest.utils.factory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser implements Browser {

	@Override
	public WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", "src\\test\\java\\com\\autotest\\tools\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		return driver;
	}

}
