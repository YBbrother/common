package com.myproject.system.dao;

import java.util.List;

import org.quartz.SchedulerException;

import com.myproject.system.model.ScheduleJob;

public interface ScheduleJobDao {
	
	List<ScheduleJob> select(ScheduleJob scheduleJob);
	
	void addTask(ScheduleJob scheduleJob);
	
	ScheduleJob getTaskById(String jobId);
	
	void updateById(ScheduleJob scheduleJob) throws SchedulerException;
	
	void deleteJobById(ScheduleJob scheduleJob);
}
