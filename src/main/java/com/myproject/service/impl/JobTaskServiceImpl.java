package com.myproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.myproject.dao.ScheduleJobDao;
import com.myproject.model.ScheduleJob;
import com.myproject.quartz.QuartzJobFactory;
import com.myproject.quartz.QuartzJobFactoryDisallowConcurrentExecution;
import com.myproject.service.JobTaskService;
import com.myproject.utils.JobUtils;

public class JobTaskServiceImpl implements JobTaskService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	private SchedulerFactoryBean schedulerFactoryBean;
	
	private ScheduleJobDao scheduleJobMapper;
	
	@Override
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.select(null);
	}

	@Override
	public void addTask(ScheduleJob scheduleJob) {
		scheduleJobMapper.addTask(scheduleJob);
	}

	@Override
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.getTaskById(jobId);
	}

	@Override
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob scheduleJob = getTaskById(jobId);
		if (scheduleJob == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(scheduleJob);
			scheduleJob.setJobStatus(JobUtils.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			scheduleJob.setJobStatus(JobUtils.STATUS_RUNNING);
			addJob(scheduleJob);
		}
		scheduleJobMapper.updateById(scheduleJob);
	}

	@Override
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob scheduleJob = getTaskById(jobId);
		if (scheduleJob == null) {
			return;
		}
		scheduleJob.setCronExpression(cron);
		if (JobUtils.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
	         updateJobCron(scheduleJob);
	      }
	      scheduleJobMapper.updateById(scheduleJob);
	}

	@Override
	public void addJob(ScheduleJob scheduleJob) throws SchedulerException {
		if (scheduleJob == null || !JobUtils.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		logger.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (cronTrigger == null) {
			Class clazz = JobUtils.CONCURRENT_IS.equals(scheduleJob.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			cronTrigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withSchedule(schedBuilder).build();
			scheduler.scheduleJob(jobDetail, cronTrigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, cronTrigger);
		}
	}
	
	@Override
	public void init() throws SchedulerException {
		// 这里获取任务信息数据
	      List<ScheduleJob> jobList = scheduleJobMapper.select(null);
	   
	      for (ScheduleJob job : jobList) {
	         addJob(job);
	      }
	}
	
	@Override
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeySet) {
			List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggerList) {
				ScheduleJob scheduleJob = new ScheduleJob();
				scheduleJob.setJobName(jobKey.getName());
				scheduleJob.setJobGroup(jobKey.getGroup());
				scheduleJob.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				scheduleJob.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
		               CronTrigger cronTrigger = (CronTrigger) trigger;
		               String cronExpression = cronTrigger.getCronExpression();
		               scheduleJob.setCronExpression(cronExpression);
		            }
		            jobList.add(scheduleJob);
		            // TODO 后面去掉看效果
		            scheduleJob = null;
			}
		}
		return jobList;
	}
	
	@Override
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobList = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<>(executingJobList.size());
		for (JobExecutionContext executingJob : executingJobList) {
			ScheduleJob scheduleJob = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			scheduleJob.setJobName(jobKey.getName());
			scheduleJob.setJobGroup(jobKey.getGroup());
			scheduleJob.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			scheduleJob.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				scheduleJob.setCronExpression(cronExpression);
			}
			jobList.add(scheduleJob);
			scheduleJob = null;
		}
		return jobList;
	}
	
	@Override
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}
	
	@Override
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
    }
	
	@Override
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	@Override
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	@Override
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		// TODO Auto-generated method stub
	}
	
	
}





