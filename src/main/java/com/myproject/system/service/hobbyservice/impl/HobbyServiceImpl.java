package com.myproject.system.service.hobbyservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.system.dao.HobbyDao;
import com.myproject.system.model.Hobby;
import com.myproject.system.service.hobbyservice.HobbyService;

@Service("HobbyService")
public class HobbyServiceImpl implements HobbyService {

	@Autowired 
	public HobbyDao hobbyDao;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertHobby(Hobby hobby) {
		hobbyDao.insertHobby(hobby);
	}

}
