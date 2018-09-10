package com.myproject.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.myproject.system.model.ScheduleJob;
import com.myproject.utils.JobUtils;

/**
 * 
 * @Description: 计划任务执行处 无状态
 *  Spring调度任务 (重写 quartz 的 QuartzJobBean 类原因是在使用 quartz+spring 把 quartz 的 task 实例化进入数据库时，会产生： serializable 的错误)
 */
public class QuartzJobFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
	    JobUtils.invokMethod(scheduleJob,context);
	}

}
