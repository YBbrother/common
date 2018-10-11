package com.myproject.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.myproject.system.model.ScheduleJob;
import com.myproject.system.service.JobTaskService;
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

}
