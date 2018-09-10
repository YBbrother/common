package com.myproject.system.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myproject.system.service.TimeJob;

@Component
public class TimeJobImpl implements TimeJob {
	@Scheduled(cron="0/5 * *  * * ? ")
	@Override
	public void aTask() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+"*********A任务每5秒执行一次进入测试");
        System.out.println(new Date());
	}
	
}
