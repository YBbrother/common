package com.myproject.system.service.timejob.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.myproject.system.service.timejob.TimeJob;

@Component(value = "timeJob")
public class TimeJobImpl implements TimeJob {

	@Override
	public void aTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+"*********A任务每5秒执行一次进入测试" + "LOVELOVELOVE");
	}
	
}
