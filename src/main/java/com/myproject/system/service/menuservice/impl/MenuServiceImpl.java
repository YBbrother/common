package com.myproject.system.service.menuservice.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.system.dao.MenuDao;
import com.myproject.system.service.menuservice.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired 
	public MenuDao menuDao;

	@Override
	public Set<String> selectPermsByUserId(int userId) {
		List<String> perms = menuDao.selectPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		if (perms != null && permsSet.size() > 0) {
			for (String perm : perms) {
				permsSet.addAll(Arrays.asList(perm.split(",")));
			}
			
		}
		return permsSet;
	}
	

}
