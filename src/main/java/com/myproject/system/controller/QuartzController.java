package com.myproject.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.myproject.system.model.ScheduleJob;
import com.myproject.system.service.jobtaskservice.JobTaskService;
import com.myproject.utils.WriterUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("quartz")
public class QuartzController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobTaskService jobTaskService;
	
	@RequestMapping("list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("quartz/jobList");
		try {
			ScheduleJob job = null;
			List<ScheduleJob> taskList = jobTaskService.getAllTask(job);
			mav.addObject("taskList", taskList);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询所有任务出错：" + e.getMessage());
		}
		return mav;
	}
	
	@RequestMapping("addTask")
	public ModelAndView addTask() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("quartz/add");
		return mav;
	}
	
	@RequestMapping("save")
	public void save(HttpServletResponse response, ScheduleJob scheduleJob) {
		/*ModelAndView mav = new ModelAndView();
		mav.setView(new RedirectView("/common/quartz/list.do"));*/
		JSONObject json = new JSONObject();
		try {
			jobTaskService.addTask(scheduleJob);
			System.out.println("添加啦啦啦啦********");
			json.put("code", "success");
			json.put("message", "保存成功");
			WriterUtil.write(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加任务出错：" + e.getMessage());
		}
		//return mav;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("quartz/edit");
		String jobId = request.getParameter("id");
		ScheduleJob scheduleJob = jobTaskService.getTaskById(jobId);
		mav.addObject("scheduleJob", scheduleJob);
		return mav;
	}
	
	@RequestMapping("revise")
	public void revise(HttpServletResponse response, ScheduleJob scheduleJob) {
		JSONObject json = new JSONObject();
		try {
			jobTaskService.updateById(scheduleJob);
			json.put("code", "success");
			json.put("message", "修改成功");
			WriterUtil.write(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改任务出错：" + e.getMessage());
		}
	}
	
	@RequestMapping("remove")
	public void remove(HttpServletResponse response, ScheduleJob scheduleJob) {
		JSONObject json = new JSONObject();
		try {
			jobTaskService.deleteJobById(scheduleJob);
			json.put("code", "success");
			json.put("message", "删除成功");
			WriterUtil.write(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改任务出错：" + e.getMessage());
		}
	}
	
	@RequestMapping("startJob")
	public void scheduleJob(HttpServletResponse response, ScheduleJob scheduleJob) {
		JSONObject json = new JSONObject();
		try {
			ScheduleJob scheduleJob1 = jobTaskService.getTaskById("1001");
			jobTaskService.addJob(scheduleJob1);
			jobTaskService.runAJobNow(scheduleJob1);
			json.put("code", "success");
			json.put("message", "启动成功");
			WriterUtil.write(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改任务出错：" + e.getMessage());
		}
	}
	
	@RequestMapping("jobAll")
	public void showAllJob(HttpServletResponse response) throws SchedulerException {
		JSONObject json = new JSONObject();
		List<ScheduleJob> jobList = jobTaskService.getAllJob();
		if (jobList != null && jobList.size() > 0) {
			for (int i = 0; i < jobList.size(); i++) {
				System.out.println("第" + (i + 1) + "个的任务：" + jobList.get(i));
			}
		}
		
		if (jobList != null) {
			logger.info("***********************一共有" + jobList.size() + "个任务***********************");
		} else {
			logger.info("***********************没有任务！***********************");
		}
		json.put("code", "success");
		json.put("message", "查询成功");
		WriterUtil.write(response, json.toString());
	}
	
	@RequestMapping("running")
	public void showAllRunningJob(HttpServletResponse response) throws SchedulerException {
		JSONObject json = new JSONObject();
		List<ScheduleJob> runningJobList = jobTaskService.getRunningJob();
		if (runningJobList != null && runningJobList.size() > 0) {
			for (int i = 0; i < runningJobList.size(); i++) {
				System.out.println("#######第" + (i + 1) + "个执行的任务：" + runningJobList.get(i));
			}
		}
		
		if (runningJobList != null) {
			logger.info("***********************已经启动了" + runningJobList.size() + "个任务***********************");
		} else {
			logger.info("***********************没有任务启动！***********************");
		}
		json.put("code", "success");
		json.put("message", "查询成功");
		WriterUtil.write(response, json.toString());
	}
	
	@RequestMapping("jobDelete")
	public void jobDelete(HttpServletResponse response, ScheduleJob scheduleJob) throws SchedulerException {
		JSONObject json = new JSONObject();
		ScheduleJob scheduleJob1 = jobTaskService.getTaskById("1001");
		jobTaskService.deleteJob(scheduleJob1);
		logger.info("***********************" + scheduleJob1.getJobId() + "删除成功***********************");
		json.put("code", "success");
		json.put("message", "删除" + scheduleJob1.getJobId() + "成功");
		WriterUtil.write(response, json.toString());
	}

}
