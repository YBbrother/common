package com.myproject.system.service.jobtaskservice;

import java.util.List;

import org.quartz.SchedulerException;

import com.myproject.system.model.ScheduleJob;

public interface JobTaskService {
	
	/**
	 * 查询所有Task
	 * 
	 * @return
	 */
	List<ScheduleJob> getAllTask(ScheduleJob job);
	
	/**
	 * 添加Task
	 * 
	 * @param scheduleJob
	 */
	void addTask(ScheduleJob scheduleJob);
	
	/**
	 * 通过jobId查询Task
	 * 
	 * @param jobId
	 * @return
	 */
	ScheduleJob getTaskById(String jobId);
	
	/**
	 * 更改任务状态
	 * 
	 * @param jobId
	 * @param cmd
	 * @throws SchedulerException
	 */
	void changeStatus(String jobId, String cmd) throws SchedulerException;
	
	/**
	 * 更改任务 cron表达式
	 * 
	 * @param jobId
	 * @param cron
	 * @throws SchedulerException
	 */
	void updateCron(String jobId, String cron) throws SchedulerException;
	
	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void addJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 修改任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void updateById(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 删除任务
	 * 
	 * @param scheduleJob
	 */
	void deleteJobById(ScheduleJob scheduleJob);
	
	/**
	 * 启动
	 * 
	 * @throws SchedulerException
	 */
	void init() throws SchedulerException;
	
	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	List<ScheduleJob> getAllJob() throws SchedulerException;
	
	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	List<ScheduleJob> getRunningJob() throws SchedulerException;
	
	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;
}
