package com.myproject.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myproject.model.ScheduleJob;

/**
 * 定时任务工具类
 */
public class JobUtils {
	public static final Logger logger = LoggerFactory.getLogger(JobUtils.class);
	
	/**
	 * 启动状态
	 */
	public static final String STATUS_RUNNING = "1";
	
	/**
	 * 未启动状态
	 */
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
	public static void invokMethod(ScheduleJob scheduleJob, JobExecutionContext context) {
		Object object = null;
		Class<?> clazz = null;
		
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (object == null) {
			logger.error("任务名称 = [" + scheduleJob.getJobName() + "]------------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getMethod(scheduleJob.getMethodName(), new Class[] {JobExecutionContext.class});
		} catch (NoSuchMethodException e) {
			logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			 e.printStackTrace();
		}
		
		if(method != null) {
			try {
				method.invoke(object, new Object[] {context});
			}catch (Exception e) {
				 e.printStackTrace();
			}
		}
		
		logger.info("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
	}
}
