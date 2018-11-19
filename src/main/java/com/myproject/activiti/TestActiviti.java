package com.myproject.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActiviti {
	
	// 使用代码生成25张表
	@Test
	public void createTableByCode() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("");
		processEngineConfiguration.setJdbcUsername("");
		processEngineConfiguration.setJdbcPassword("");
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
	
	// 使用配置文件生成25张表
	@Test
	public void createTableByProperty() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}

}
