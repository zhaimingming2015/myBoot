package com.zhai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhai.dao.UserRolesDao;
import com.zhai.pojo.UserRoles;
import com.zhai.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRolesDao userRolesDao;
	
	
	public List<UserRoles> getRolesByUsername(String username) {
		return  userRolesDao.queryRolesByUsername(username);
	
	}

}
