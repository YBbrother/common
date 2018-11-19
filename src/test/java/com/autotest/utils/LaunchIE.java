package com.autotest.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class LaunchIE {
	public static void main(String[] args) {
		
		// 去掉IE浏览器d的所有区域的保护模式设置
		System.setProperty("webdriver.ie.driver", "src\\test\\java\\com\\autotest\\tools\\IEDriverServer.exe");
		// 初始化一个IE浏览器实例，实例名称叫driver
		WebDriver driver = new InternetExplorerDriver();
		// 最大化窗口
		driver.manage().window().maximize();
		// 设置隐性等待时间
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		// get()打开一个站点
		driver.get("https://www.baidu.com");
		// getTitle()获取当前页面title的值
		System.out.println("当前打开的页面是" + driver.getTitle());
		// 关闭并退出浏览器
		driver.quit();
		
	}

}
