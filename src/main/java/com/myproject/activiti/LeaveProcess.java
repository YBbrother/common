package com.myproject.activiti;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

////////////没用

public class LeaveProcess {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();    // 得到流程引擎
	
	/**
	 *  部署流程定义（从classpath）
	 */
	@Test
	public void deploymentProcessDef_Clp() {
		Deployment deployment = processEngine.getRepositoryService()  // 与流程定义和部署对象相关的service
				.createDeployment()   // 创建一个部署对象
				.name("leaveProcess") // 添加部署的名称
				.addClasspathResource("bpmn/LeaveProcess.bpmn") // 从classpath的资源中加载，一次只能加载一个文件
				.addClasspathResource("bpmn/LeaveProcess.png") // 从classpath的资源中加载，一次只能加载一个文件
				.deploy();   // 完成部署
		System.out.println(deployment.getId());  // 2501
		System.out.println(deployment.getName());  // leaveProcess
	}
	
	/**
	 *  部署流程定义（从zip文件）
	 */
	@Test
	public void deploymentProcessDef_Zip() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("bpmn/LeaveProcess.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()  // 与流程定义和部署对象相关的service
				.createDeployment()   // 创建一个部署对象
				.name("leaveProcess") // 添加部署的名称
				.addZipInputStream(zipInputStream)
				.deploy();   // 完成部署
		System.out.println(deployment.getId());  // 2501
		System.out.println(deployment.getName());  // leaveProcess
		
	}
	
	/**
	 *  启动流程实例
	 */
	@Test
	public void  startProcessInstance() {
		String processDefinitionKey = "leave";   // 流程定义的key
		ProcessInstance processInstance = processEngine.getRuntimeService()  // 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);  // 使用流程定义的key启动流程实例，key对应
		System.out.println("流程实例ID: " + processInstance.getId());  // 5001
		System.out.println("流程定义ID: " + processInstance.getProcessDefinitionId());  // leave:1:2504
	}
	
	/**
	 *  查询当前人的个人任务
	 */
	@Test
	public void findMyPersonTask() {
		String assignee = "老张";  // 当前人
		List<Task> list = processEngine.getTaskService()    // 与正在执行的任务管理相关的service
				.createTaskQuery()       // 创建任务查询对象
				.taskAssignee(assignee)  // 指定个人任务查询， 指定办理人
				.list();
		if(list != null && list.size() > 0) {
			for(Task task : list) {
				System.out.println("任务ID: " + task.getId());  // 5004
				System.out.println("任务名称: " + task.getName());  // 提交申请【申请人】
				System.out.println("任务创建时间: " + task.getCreateTime());  // Tue Jul 03 14:51:38 CST 2018
				System.out.println("任务办理人: " + task.getAssignee());  // 老张
				System.out.println("流程实例ID: " + task.getProcessInstanceId());  // 5001
				System.out.println("执行对象ID: " + task.getExecutionId());  // 5001
				System.out.println("流程定义ID: " + task.getProcessDefinitionId());  // leave:1:2504
			}
		} else {
			System.out.println("无结果");
		}
		System.out.println("##########################################");
	}
	
	/**
	 *  完成我的任务
	 */
	@Test
	public void completeMyPersonalTask() {
		String taskId = "35004";  // 任务ID
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务ID: " + taskId);
	}
	
	
}
