package com.myproject.system.dao;

import java.util.List;

public interface MenuDao {
	
	List<String> selectPermsByUserId(int userId);
	
}
