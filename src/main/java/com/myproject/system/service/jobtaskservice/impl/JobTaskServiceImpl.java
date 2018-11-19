package com.myproject.system.service.jobtaskservice.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.myproject.quartz.QuartzJobFactory;
import com.myproject.quartz.QuartzJobFactoryDisallowConcurrentExecution;
import com.myproject.system.dao.ScheduleJobDao;
import com.myproject.system.model.ScheduleJob;
import com.myproject.system.service.jobtaskservice.JobTaskService;
import com.myproject.utils.JobUtils;

// @Cacheable 缓存结果          @CachePut 不仅会缓存方法的结果，还会执行方法的代码段         @CacheEvict 表明所修饰的方法是用来删除失效或无用的缓存数据

@Service
public class JobTaskServiceImpl implements JobTaskService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private ScheduleJobDao scheduleJobMapper;
	
//	@Cacheable(value="backupCache", key="0")
	@Override
	public List<ScheduleJob> getAllTask(ScheduleJob job) {
		logger.info("********************走数据库******************");
		List<ScheduleJob> list = scheduleJobMapper.select(job);
		return list;
	}

//	@CacheEvict(value="backupCache", key="0")
	@Override
	public void addTask(ScheduleJob scheduleJob) {
		scheduleJobMapper.addTask(scheduleJob);
	}
	
//	@CacheEvict(value="backupCache", key="0")
	@Override
	public void updateById(ScheduleJob scheduleJob) throws SchedulerException {
		scheduleJobMapper.updateById(scheduleJob);
	}

	@Override
	public void deleteJobById(ScheduleJob scheduleJob) {
		scheduleJobMapper.deleteJobById(scheduleJob);
	}

	@Override
	public ScheduleJob getTaskById(String jobId) {
		return scheduleJobMapper.getTaskById(jobId);
	}

	@Override
	public void changeStatus(String jobId, String cmd) throws SchedulerException {
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
	public void updateCron(String jobId, String cron) throws SchedulerException {
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
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
	    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
	    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
	    scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	public static void main(String[] args) {
		// 检查cron表达式是否正确
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("5 * * * * ?");
		System.out.println(scheduleBuilder);
	}

}

