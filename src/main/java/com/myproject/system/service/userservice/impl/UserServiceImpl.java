package com.myproject.system.service.userservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.system.dao.UserDao;
import com.myproject.system.model.UserModel;
import com.myproject.system.service.userservice.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired 
	public UserDao userDao;

	@Override
	public UserModel login(String userName, String password) {
		return userDao.login(userName, password);
	}

	@Override
	public void updateUserLoginTime() {
		userDao.updateUserLoginTime();
	}

	@Override
	public void updateUser(UserModel userModel) {
		userDao.updateUser(userModel);
	}

}
