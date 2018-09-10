package com.myproject.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.myproject.system.model.Person;

public class LeaveProcessTest1 {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
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
	
	/**
	 *  查询流程定义
	 */
	@Test
	public void findProcessDefinition() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				/**指定查询条件, where */
//				.deploymentId(deploymentId)  // 使用部署对象ID查询
//				.processDefinitionId(processDefinitionId)  // 使用流程定义ID查询 
//				.processDefinitionKey(processDefinitionKey)  // 使用流程定义的key查询
//				.processDefinitionNameLike(processDefinitionNameLike)
				
				/**排序*/
				.orderByProcessDefinitionVersion().asc()  // 按照版本的升序排列
//				.orderByProcessDefinitionName().desc()  // 按照流程定义的名称降序排列
				
				/**返回的结果集*/
				.list();  // 返回一个集合列表，封装流程定义
//				.singleResult();  // 返回唯一结果集
//				.count();   // 返回结果集数量
//				.listPage(firstResult, maxResults);  // 分页查询
		if(list != null && list.size() > 0) {
			for(ProcessDefinition pd : list) {
				System.out.println("流程定义ID：" + pd.getId());  // 流程定义的key+版本号+随机生成数
				System.out.println("流程定义名称：" + pd.getName()); // 对应bpmn文件的name属性
				System.out.println("流程定义的key：" + pd.getKey());  // 对应bpmn文件的id属性
				System.out.println("流程定义的版本：" + pd.getVersion());
				System.out.println("资源名称bpmn文件：" + pd.getResourceName());
				System.out.println("资源名称png文件：" + pd.getDiagramResourceName());
				System.out.println("部署对象ID：" + pd.getDeploymentId());
			}
		} else {
			System.out.println("无结果");
		}		
		System.out.println("###############################");		
	}
	
	/**
	 *  删除流程定义
	 */
	@Test
	public void deleteProcessDefinition() {
		// 使用部署ID删除
		String deploymentId = "2501";
		
		//  不带级联删除， 只能删除没有启动的流程，如果流程启动，就会抛出异常
		//processEngine.getRepositoryService().deleteDeployment(deploymentId);
		
		// 级联删除，不管流程是否启动，都能删除,该id相关的数据都会删除
		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		
		System.out.println("删除成功！！！");
	}
	
	/**
	 *  查看流程附件，主要查的是图片，用于显示流程用
	 */
	@Test
	public void viewImage() {
		// 从仓库中找需要展示的文件
		String deploymentId = "15001";
		List<String> names = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			System.out.println(name + "//////////");
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		System.out.println("imageName: " + imageName);
		if (imageName != null) {
			File file = new File("e:/" + imageName);
			// 通过部署ID和文件名得到文件的输入流
			InputStream in = processEngine.getRepositoryService()
					.getResourceAsStream(deploymentId, imageName);
			try {
				FileUtils.copyInputStreamToFile(in, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	/**
	 *  查询最新版本的流程定义
	 */
	@Test
	public void queryAllLatestVersions() {
		// 用版本号顺序查询
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc()
				.list();
		
		// 把相同key的value循环覆盖，过滤出最新的版本
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : list) {
			map.put(pd.getKey(), pd);
		}
		
		for (ProcessDefinition pd : map.values()) {
			System.out.println("id: " + pd.getId()
			+ ", name: " + pd.getName() + ", key: "
			+ pd.getKey() + ", version: " + pd.getVersion()
			+ ", deploymentId: " + pd.getDeploymentId());
		}
	}
	
	/**
	 *  删除（使用流程定义的key）
	 */
	@Test
	public void deleteByKey() {
		String processDefinitionKey = "leave2";  // 流程定义的key查询
		// 查询指定key的所有版本的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.list();
		
		// 循环删除
		for (ProcessDefinition pd : list) {
			processEngine.getRepositoryService()
					.deleteDeployment(pd.getDeploymentId(), true);   // 级联删除
		}
		System.out.println("删除成功！！！");
	}
	
	/**
	 *  查看流程状态（判断流程是正在执行，还是已经结束）
	 */
	@Test
	public void queryProcessState() {
		String processInstanceId = "17501";
		// 一个流程实例ID只对应一个实例
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		if (processInstance != null) {
			System.out.println("当前流程在：" + processInstance.getActivityId());
		} else {
			// 流程实例从act_ru_execution删除
			System.out.println("流程已经结束！！！");
		}
	}
	
	/**
	 *  查询历史任务
	 */
	@Test
	public void queryHistoryTask() {
		String taskAssignee = "老张";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.taskAssignee(taskAssignee)
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				System.out.println("任务ID: " + hti.getId());
				System.out.println("流程实例ID: " + hti.getProcessInstanceId());
				System.out.println("任务办理人: " + hti.getAssignee());
				System.out.println("执行对象ID: " + hti.getExecutionId());
				System.out.println(hti.getStartTime() + "" + hti.getEndTime() + "" + hti.getDurationInMillis());
			}
		}
	}
	
	/**
	 *  查询历史流程实例
	 */
	@Test
	public void queryHistoryProcessInstance() {
		String processInstanceId = "17501";
		HistoricProcessInstance hpi = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		System.out.println("流程定义ID: " + hpi.getProcessDefinitionId());
		System.out.println("流程实例ID: " + hpi.getId());
		System.out.println("" + hpi.getStartTime() + "" + hpi.getEndTime() + "" + hpi.getDurationInMillis());
	}
	
	/**
	 *  设置流程变量
	 */
	@Test
	public void setVariables() {
		TaskService taskService = processEngine.getTaskService();
		String assignee = "老张";  // 指定办理人
		String processInstanceId = "40001";
		Task task = taskService.createTaskQuery()
				.taskAssignee(assignee)
				.processInstanceId(processInstanceId)
				.singleResult();
		
		// 变量中存放基本数据类型
		taskService.setVariable(task.getId(), "请假人", "老张");  // 使用流程变量的名称和流程变量的值设置流程变量，一次只能设置一个
		taskService.setVariableLocal(task.getId(), "请假天数", 3);
		taskService.setVariable(task.getId(), "请假日期", new Date());
		
		// 变量中存放对象的前提是实现序列化
		Person person = new Person();
		person.setId(1);
		person.setName("老狼");
		taskService.setVariable(task.getId(), "人员信息", person);
	}
	
	/**
	 *  历史任务查看（某一次流程的执行经历的任务节点数量）
	 */
	@Test
	public void queryHistoricTask() {
		String processInstanceId = "17501";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				// .listPage(firstResult, maxResults)
				.list();
		for (HistoricTaskInstance hti : list) {
			System.out.println("taskId: " + hti.getId());
			System.out.println("name: " + hti.getName());
			System.out.println("pdId: " + hti.getProcessDefinitionId());
			System.out.println("pid: " + hti.getProcessInstanceId());
			System.out.println("assignee: " + hti.getAssignee());
			System.out.println("startTime: " + hti.getStartTime());
			System.out.println("endTime: " + hti.getEndTime());
			System.out.println("duration: " + hti.getDurationInMillis());
		}
	}
}
