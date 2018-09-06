package com.myproject.dao;

import java.util.List;

import org.quartz.SchedulerException;

import com.myproject.model.ScheduleJob;

public interface ScheduleJobDao {
	
	List<ScheduleJob> select(ScheduleJob scheduleJob);
	
	void addTask(ScheduleJob scheduleJob);
	
	ScheduleJob getTaskById(Long jobId);
	
	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId, String cron) throws SchedulerException;

	void addJob(ScheduleJob scheduleJob) throws SchedulerException;

	void updateById(ScheduleJob scheduleJob) throws SchedulerException;

}
