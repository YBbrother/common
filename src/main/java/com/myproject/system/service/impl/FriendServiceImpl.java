package com.myproject.system.service.impl;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.system.dao.FriendDao;
import com.myproject.system.model.Friend;
import com.myproject.system.service.FriendService;

@Service("FriendService")
@Transactional
public class FriendServiceImpl implements FriendService {

	@Autowired 
	public FriendDao friendDao;

	@Override
	
	public void insertFriend(Friend friend) {
		try {
			friendDao.insertFriend(friend);
			int i = 10/0;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
