package com.autotest.utils.factory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxBrowser implements Browser {

	@Override
	public WebDriver getDriver() {
		System.setProperty("webdriver.gecko.driver", "src\\test\\java\\com\\autotest\\tools\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		return driver;
	}

}
