package com.autotest.learn;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autotest.utils.factory.Browser;
import com.autotest.utils.factory.BrowserFactory;

public class PageLoadTest {
	public static void main(String[] args) throws InterruptedException {
		
		Browser browser = BrowserFactory.getChrome();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.baidu.com/");
		
		// 通过id定位网页元素
		//driver.findElement(By.id("kw")).sendKeys("玉山");
		
		// 通过XPath查找网页元素                                                               XPath即为XML路径语言
		//driver.findElement(By.xpath(".//*[@id='kw']")).sendKeys("Java");
	    
		//Thread.sleep(2000);
		
		// 通过class定位网页元素                       
		// 通过class定位时参数名不能有空格                错误示例： bg s_btn 
		//driver.findElement(By.className("s_ipt")).sendKeys("1931");
		
		// 通过name定位网页元素              
		//driver.findElement(By.name("wd")).sendKeys("Java");
		
		// 通过链接文本查找网页元素
		//driver.findElement(By.linkText("新闻")).click();
		
		// 用LinkText里面的一部分字符就可以定位该元素
		// 如果有多个，将定位到第一个
		//driver.findElement(By.partialLinkText("设")).click();
		
		// CSS表达式去定位网页元素       输入内容， 清空内容
		/*WebElement we = driver.findElement(By.cssSelector("#kw"));
		we.sendKeys("天气");
		Thread.sleep(2000);
		we.clear();*/
		// 标签名
		//driver.findElement(By.tagName("html"));
		
		//driver.findElement(By.xpath(".//*[@id='su']")).click();
	    
		// findElements是用来查找一组元素，而findElement是用来查找匹配表达式的第一个元素
		/*List<WebElement> elementList = driver.findElements(By.xpath(".//*[@id=\"pane-news\"]/ul[2]/li/a"));
		if (elementList != null && elementList.size() > 0) {
			for (WebElement webElement : elementList) {
				System.out.println(webElement.getText());
			}
		}*/

/*       
        Thread.sleep(200);
                    获取当前页的title
        String title = driver.getTitle();
                   获取当前页的url	
        String url = driver.getCurrentUrl();
        
        // 使用断言需要先设置，  如果为false则会报异常
        assert title.equals("百度一下，你就知道");
        System.out.println(111);
        
        assert title == "百度一下，你就知道";
        System.out.println(222);*/
		
		// 获取当前页面的源码
		//String pageSource = driver.getPageSource();
		
		
		/*
		Thread.sleep(1000);
        WebElement setHome_link = driver.findElement(By.partialLinkText("设为主页"));
        setHome_link.click();
        Thread.sleep(1000);
        // 获取浏览器窗体的句柄, 也叫windows id, 即便签页的id
        System.out.println(driver.getWindowHandles());*/
		
	/*	// 关闭driver.get的标签页
		driver.close();
		
		// 关闭浏览器
		driver.quit();*/
	}
}
