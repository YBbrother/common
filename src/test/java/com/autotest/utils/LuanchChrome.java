package com.autotest.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LuanchChrome {
	public static void main(String[] args) {
		
		// 把chromedriver设置成为系统的全局变量
		System.setProperty("webdriver.chrome.driver", "src\\test\\java\\com\\autotest\\tools\\chromedriver.exe");
		// 初始化一个谷歌浏览器实例，实例名称叫driver
		WebDriver driver = new ChromeDriver();
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
