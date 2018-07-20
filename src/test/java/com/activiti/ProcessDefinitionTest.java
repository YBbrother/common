package com.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class ProcessDefinitionTest {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
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
	
	
	
}
