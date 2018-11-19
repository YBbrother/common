package com.autotest.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LuanchFirefox {
	public static void main(String[] args) {
		
		// firefox版本低于48
		// System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
		
		// firefox版本高于48
		System.setProperty("webdriver.gecko.driver", "src\\test\\java\\com\\autotest\\tools\\geckodriver.exe");
		// 初始化一个火狐浏览器实例，实例名称叫driver
		// 需要禁用火狐浏览器的自动更新，否则实例化会报错
		WebDriver driver = new FirefoxDriver();
		// 最大化窗口
		driver.manage().window().maximize();
		// 设置隐性等待时间
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		// get()打开一个站点
		driver.get("https://www.sina.com.cn/");
		// getTitle()获取当前页面title的值
		System.out.println("当前打开的页面是" + driver.getTitle());
		// 关闭并退出浏览器
		driver.quit();
		
	}

}
