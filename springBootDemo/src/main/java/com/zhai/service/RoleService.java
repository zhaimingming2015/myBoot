package com.zhai.service;

import java.util.List;

import com.zhai.pojo.UserRoles;

public interface RoleService {
	
	public List<UserRoles> getRolesByUsername(String username);

}
