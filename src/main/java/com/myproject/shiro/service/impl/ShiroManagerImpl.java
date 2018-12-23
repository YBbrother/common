package com.myproject.shiro.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.myproject.common.constant.Constant;
import com.myproject.config.INI4j;
import com.myproject.shiro.service.ShiroManager;
import com.myproject.utils.LoggerUtils;

public class ShiroManagerImpl implements ShiroManager {
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	@Override
	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer();
		//固定权限，采用读取配置文件
		sb.append(getFixedAuthRule());
		return sb.toString();
	}
	
	/**
	 * 从配额文件获取固定权限验证规则串
	 * @return
	 */
	private String getFixedAuthRule() {
		String fileName = "shiro_base_auth.ini";
		ClassPathResource cpr = new ClassPathResource(fileName);
		INI4j ini = null;
		try {
			ini = new INI4j(cpr.getFile());
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "加载文件出错。file:[%s]", fileName);
		}
		String section = "base_auth";
		Set<String> keys = ini.get(section).keySet();
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			String value = ini.get(section, key);
			sb.append(key).append(" = ").append(value).append(Constant.CRLF);
		}
		return sb.toString();
	}

	@Override
	public synchronized void reCreateFilterChains() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			LoggerUtils.error(getClass(), "getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}
		
		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
		
		// 清空旧的权限控制
		manager.getFilterChains().clear();
		
		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
		// 重新构建生成
		Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		for (Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}
	}

	public void setShiroFilterFactoryBean(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		this.shiroFilterFactoryBean = shiroFilterFactoryBean;
	}
	
}
