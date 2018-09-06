package com.myproject.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface Job {
	
	/**
	 * 定义运行任务
	 * @param context
	 * @throws JobExecutionException
	 */
	void execute(JobExecutionContext context) throws JobExecutionException;

}
