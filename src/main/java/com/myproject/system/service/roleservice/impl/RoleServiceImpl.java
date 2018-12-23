package com.myproject.system.service.roleservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.system.dao.RoleDao;
import com.myproject.system.service.roleservice.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired 
	public RoleDao roleDao;


}
