package com.myproject.system.dao;

import com.myproject.system.model.UserModel;

public interface UserDao {
	
	UserModel login(String userName, String password);
	
	void updateUserLoginTime();
	
	void updateUser(UserModel userModel);
	
}
