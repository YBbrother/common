package com.myproject.activiti;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class LeaveProcessTest2 {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();    // 得到流程引擎
	
	/**
	 *  部署流程定义，启动流程实例(连线)
	 */
	@Test
	public void testSequenceFlow() {
		// 发布流程
		InputStream inBpmn = this.getClass().getResourceAsStream("/bpmn/Leave2.bpmn");    // "/"表示从classpath下查找
		InputStream inPng = this.getClass().getResourceAsStream("/bpmn/Leave2.png");
		processEngine.getRepositoryService()
				.createDeployment()
				/*.addClasspathResource("bpmn/Leave2.bpmn")
				.addClasspathResource("bpmn/Leave2.png")*/
				.addInputStream("Leave2.bpmn", inBpmn)
				.addInputStream("Leave2.png", inPng)
				.deploy();
		// 启动流程
		String processDefinitionKey = "leave2";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("pid: " + processInstance.getId());
	}
	
	/**
	 *  查询个人任务
	 */
	@Test
	public void findMyTaskList() {
		String assignee = "老赵";
		List<Task> list = processEngine.getTaskService()
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();
		for (Task task : list) {
			System.out.println("任务id: " + task.getId());
			System.out.println("任务名称: " + task.getName());
			System.out.println("办理人: " + task.getAssignee());
			System.out.println("创建时间: " + task.getCreateTime());
			System.out.println("流程执行id: " + task.getExecutionId());
		} 
	}
	
	/**
	 *  完成任务
	 */
	@Test
	public void completeTask() {
		String taskId = "57510"; // 任务id为57510的是不重要，  任务id为57513的是重要
		// 设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		processEngine.getTaskService()
				.complete(taskId, variables);
		System.out.println("完成任务！！！");
	}
	
	
	/**
	 *  排他网关(不要给默认网关设置条件，否则会报错)
	 */
	@Test
	public void testExclusiveGateWay() {
		// 发布流程
		InputStream inBpmn = this.getClass().getResourceAsStream("/bpmn/Exclusive.bpmn");    // "/"表示从classpath下查找
		InputStream inPng = this.getClass().getResourceAsStream("/bpmn/Exclusive.png");
		processEngine.getRepositoryService()
				.createDeployment()
				/*.addClasspathResource("bpmn/Leave2.bpmn")
				.addClasspathResource("bpmn/Leave2.png")*/
				.addInputStream("Exclusive.bpmn", inBpmn)
				.addInputStream("Exclusive.png", inPng)
				.deploy();
		// 启动流程
		String processDefinitionKey = "exclusives";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("pid: " + processInstance.getId());
	}
	
	
	/**
	 *  查询我的个人任务列表
	 */
	@Test
	public void findTaskList() {
		String assignee = "小五";
		List<Task> list = processEngine.getTaskService()
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();
		for (Task task : list) {
			System.out.println("任务id: " + task.getId());
			System.out.println("任务名称: " + task.getName());
			System.out.println("办理人: " + task.getAssignee());
			System.out.println("创建时间: " + task.getCreateTime());
			System.out.println("流程执行id: " + task.getExecutionId());
		}
		System.out.println("##################################################");
	}
	
	
	/**
	 *  完成
	 */
	@Test
	public void completeTasks() {
		String taskId = "507";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("money", 200);
		processEngine.getTaskService()
				.complete(taskId, variables);
		System.out.println("任务完成！！！");
	}
	
	
	
	
}
