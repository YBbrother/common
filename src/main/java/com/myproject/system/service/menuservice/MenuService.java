package com.myproject.system.service.menuservice;

import java.util.Set;

public interface MenuService {
	
	Set<String> selectPermsByUserId(int userId);

}
