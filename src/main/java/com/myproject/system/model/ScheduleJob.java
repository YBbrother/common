package com.myproject.system.model;

import java.io.Serializable;

public class ScheduleJob implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long jobId;
	private String createTime;
	private String updateTime;
	
	/**
	 * 任务名称
     */
    private String jobName;
	
    /**
     * 任务分组
     */
    private String jobGroup;
	
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus;
	
    /**
     * cron表达式
     */
    private String cronExpression;
	
    /**
     * 描述
     */
    private String description;
    
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    
    /**
     * 任务是否有状态    需要把值设成false
     */
    private String isConcurrent;
    
    /**
     * spring bean
     */
    private String springId;
    
    /**
     * 任务调用的方法名
     */
    private String methodName;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return "ScheduleJob [jobId=" + jobId + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", jobStatus=" + jobStatus + ", cronExpression="
				+ cronExpression + ", description=" + description + ", beanClass=" + beanClass + ", isConcurrent="
				+ isConcurrent + ", springId=" + springId + ", methodName=" + methodName + "]";
	}
    
}
