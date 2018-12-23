package com.myproject.system.service.userservice;

import com.myproject.system.model.UserModel;

public interface UserService {
	
	UserModel login(String userName, String password);
	
	/**
	 * 更新登陆时间
	 */
	void updateUserLoginTime();
	
	void updateUser(UserModel userModel);

}
