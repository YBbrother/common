package com.myproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dao.HobbyDao;
import com.myproject.model.Hobby;
import com.myproject.service.HobbyService;

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
